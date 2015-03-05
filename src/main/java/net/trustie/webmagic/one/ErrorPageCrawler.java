package net.trustie.webmagic.one;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.trustie.webmagic.one.dao.ErrorPageDao;
import net.trustie.webmagic.one.dao.PointerDAO;
import net.trustie.webmagic.one.dao.URLDao;
import net.trustie.webmagic.one.model.URL;
import net.trustie.webmagic.utils.DBPipeline;
import net.trustie.webmagic.utils.FileReader;
import net.trustie.webmagic.utils.TableHelper;

import org.apache.ibatis.binding.BindingException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.handler.CompositePageProcessor;
import us.codecraft.webmagic.scheduler.SimpleScheduler;

/**
 * Created by gyiang on 2014/11/15.
 */
@Component
public class ErrorPageCrawler {
	Logger logger = Logger.getLogger(this.getClass());
	private int batchSize = 10;
	@Resource
	private ErrorPageDao errorPageDao;
	@Resource
	private PointerDAO pointerDao;

	@Qualifier("errorPagePipeline")
	@Autowired
	private DBPipeline pipeline;

	@Qualifier("tableHelper")
	@Autowired
	private TableHelper tableHelper;

	private String pointersTableName = "pointers";
	private String pointersSql = "";

	private String domain = "";
	private String urlTableName = "";
	private String urlTableSql = "";
	private String errorPageTableName = "";
	private String errorPageTableSql = "";

	private String listHtmlTableName = "";
	private String listHtmlTableSql = "";

	private String detailHtmlTableName = "";
	private String detailHtmlTableSql = "";
	private Configure conf = null;

	private void init(String configureName) {
		conf = new Configure(configureName);
		domain = conf.getDomain();
		listHtmlTableName = domain + "_html_list";
		detailHtmlTableName = domain + "_html_detail";
		urlTableName = domain + "_url";
		errorPageTableName = domain + "_error_page";
		batchSize = conf.getThreadNum();
		pipeline.setListHtmlTableName(this.listHtmlTableName);
		pipeline.setDetailHtmlTableName(this.detailHtmlTableName);
		pipeline.setErrorPageTableName(this.errorPageTableName);
		initSql();
		initTable();
	}

	private void initSql() {
		this.pointersSql = FileReader.readFile("./sql/pointers.sql");
		this.urlTableSql = FileReader.readFile("./sql/url.sql");
		this.listHtmlTableSql = FileReader.readFile("./sql/list_html.sql");
		this.detailHtmlTableSql = FileReader.readFile("./sql/detail_html.sql");
		this.errorPageTableSql = FileReader.readFile("./sql/error_page.sql");
	}

	private void initTable() {
		initOneTable(this.pointersTableName, this.pointersSql);
		initOneTable(this.listHtmlTableName, this.listHtmlTableSql);
		initOneTable(this.detailHtmlTableName, this.detailHtmlTableSql);
		initOneTable(this.urlTableName, this.urlTableSql);
		initOneTable(this.errorPageTableName, this.errorPageTableSql);
	}

	private void initOneTable(String table, String fields) {
		if (!tableHelper.isTableExist(table)) { // 表不存在即初始化

			tableHelper.createTable(table, fields);
			logger.info("create table " + table + " success!!!");
		}
	}

	public void crawl(String configureName) throws InterruptedException {
		// Configure conf = new Configure(configureName);
		this.init(configureName);
		// 每一批取线程数个url
		Set<Integer> acceptStatCode = new HashSet<Integer>();
		acceptStatCode.add(200);
		acceptStatCode.add(404);
		acceptStatCode.add(500);
		acceptStatCode.add(503);

		// List<String[]> proxies = Utils.getProxies(conf);
		Site site = Site.me().setSleepTime(conf.getSleepTimeThread())
				.setTimeOut(conf.getTimeOut())
				.setRetryTimes(conf.getRetryTimes())
				.setCycleRetryTimes(conf.getCycleRetryTimes())
				.setDomain(conf.getDomain()).setUserAgent(conf.getUserAgent())
				.setAcceptStatCode(acceptStatCode);
		// .setHttpProxyPool(proxies);
		// int offset = 0;// conf.getOffset();
		// int limitLine = conf.getLimitLine();
		Spider spider = Spider.create(new ErrorPageProcessor(site))
				.addPipeline(pipeline).setScheduler(new SimpleScheduler())
				.thread(conf.getThreadNum());

		int startId = this.getPointer(this.errorPageTableName);
		List<URL> urls = new ArrayList<URL>();
		Request[] urlArray = null;
		Request request = null;
		Map<String, Object> maps = new HashMap<String, Object>();

		while (true) {
			// int endId = startId + this.batchSize;
			urls = errorPageDao.getBatch(this.errorPageTableName, startId,
					this.batchSize);
			urlArray = new Request[urls.size()];
			int i = 0;
			for (URL url : urls) {
				request = new Request(url.getUrl());
				maps.put("type", url.getType());
				request.setExtras(maps);
				urlArray[i] = request;
				startId = url.getId();
				i++;
			}
			i = 0;
			if (urlArray.length == 0) {
				System.out.println("no error url in queue--->Thread.sleep()");
				Thread.sleep(conf.getNoUrlWaitTime());
				continue;
			}

			spider.addRequest(urlArray);
			spider.run();

			pointerDao.updatePointer(this.pointersTableName,
					this.errorPageTableName, startId);
			urls.clear();
			// sleep
			logger.info("sleep " + conf.getSleepTimeSpider() / 1000 + "s");
			Thread.sleep(conf.getSleepTimeSpider());
		}
	}

	private int getPointer(String tableName) {
		int pointer = 0;
		try {
			pointer = pointerDao.readPointer(this.pointersTableName, tableName);
		} catch (BindingException e) {
			pointerDao.insertPointer(this.pointersTableName, tableName, 0);
		}
		return pointer;

	}

	public static void main(String[] args) throws InterruptedException {
		String configureName = "";
		if (args.length != 0)
			configureName = args[0].toString();
		if (configureName.equals("")) {
			configureName = "ossean";
		} else {
		}
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:/spring/applicationContext*.xml");
		final ErrorPageCrawler errorPageCrawler = applicationContext
				.getBean(ErrorPageCrawler.class);
		errorPageCrawler.crawl(configureName);
	}
}
