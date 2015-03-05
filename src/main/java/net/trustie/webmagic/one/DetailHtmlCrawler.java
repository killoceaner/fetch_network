package net.trustie.webmagic.one;

import net.trustie.webmagic.one.dao.PointerDAO;
import net.trustie.webmagic.one.dao.URLDao;
import net.trustie.webmagic.one.model.URL;
import net.trustie.webmagic.utils.DBPipeline;
import net.trustie.webmagic.utils.FileReader;
import net.trustie.webmagic.utils.TableHelper;
import net.trustie.webmagic.utils.Utils;

import org.apache.ibatis.binding.BindingException;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.handler.CompositePageProcessor;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.SimpleScheduler;
import us.codecraft.webmagic.scheduler.component.BloomFilterDuplicateRemover;

import javax.annotation.Resource;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by gyiang on 2014/11/15.
 */
@Component
public class DetailHtmlCrawler {
	Logger logger = Logger.getLogger(this.getClass());
	private int batchSize = 10;
	@Resource
	private URLDao urlDao;
	@Resource
	private PointerDAO pointerDao;

	@Qualifier("detailHtmlPipeline")
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

	private String detailHtmlTableName = "";
	private String detailHtmlTableSql = "";
	private Configure conf = null;

	private void init(String configureName) {
		conf = new Configure(configureName);
		domain = conf.getDomain();
		detailHtmlTableName = domain + "_html_detail";
		urlTableName = domain + "_url";
		errorPageTableName = domain + "_error_page";
		initSql();
		initTable();
	}

	private void initSql() {
		this.pointersSql = FileReader.readFile("./sql/pointers.sql");
		this.urlTableSql = FileReader.readFile("./sql/url.sql");
		this.detailHtmlTableSql = FileReader.readFile("./sql/detail_html.sql");
		this.errorPageTableSql = FileReader.readFile("./sql/error_page.sql");
	}

	private void initTable() {
		initOneTable(this.pointersTableName, this.pointersSql);
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
		batchSize = conf.getThreadNum();
		Set<Integer> acceptStatCode = new HashSet<Integer>();
		//pipeline.setTableName(this.detailHtmlTableName);
		pipeline.setDetailHtmlTableName(this.detailHtmlTableName);
		pipeline.setErrorPageTableName(this.errorPageTableName);
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
		Spider spider = Spider
				.create(new DetailHtmlProcessor(site))
				.addPipeline(pipeline).setScheduler(new SimpleScheduler()).thread(conf.getThreadNum());

		int startId = this.getPointer(this.urlTableName);
		List<URL> urls = new ArrayList<URL>();
		List<String> urlList = new ArrayList<String>();
		while (true) {
			// int endId = startId + this.batchSize;
			urls = urlDao.getBatch(this.urlTableName, startId, this.batchSize);
			urlList = new ArrayList<String>();

			for (URL url : urls) {
				urlList.add(url.getUrl());
				startId = url.getId();
			}
			if (urlList.size() == 0) {
				System.out.println("no url in queue--->Thread.sleep()");
				Thread.sleep(conf.getNoUrlWaitTime());
				continue;
			}
			
			spider.startUrls(urlList);
			spider.run();

			pointerDao.updatePointer(this.pointersTableName, this.urlTableName,
					startId);
			urls.clear();
			urlList.clear();
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
		final DetailHtmlCrawler htmlCrawler = applicationContext
				.getBean(DetailHtmlCrawler.class);
		htmlCrawler.crawl(configureName);
	}
}
