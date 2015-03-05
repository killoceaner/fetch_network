package net.trustie.webmagic.one;

import net.trustie.webmagic.one.dao.CreateTableDAO;
import net.trustie.webmagic.one.dao.PointerDAO;
import net.trustie.webmagic.one.dao.URLDao;
import net.trustie.webmagic.utils.DBPipeline;
import net.trustie.webmagic.utils.FileReader;
import net.trustie.webmagic.utils.OrderSpider;
import net.trustie.webmagic.utils.TableHelper;
import net.trustie.webmagic.utils.Utils;

import org.apache.http.HttpHost;
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
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.scheduler.SimpleScheduler;

import javax.annotation.Resource;
import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2014/12/19.
 */
@Component
public class ListHtmlCrawler {
	Logger logger = Logger.getLogger(this.getClass());
	@Qualifier("listHtmlPipeline")
	@Autowired
	private DBPipeline pipeline;
	@Resource
	private PointerDAO pointerDao;

	@Qualifier("tableHelper")
	@Autowired
	private TableHelper tableHelper;

	private String pointersTableName = "pointers";
	private String pointersSql = "";

	private String domain = "";
	private String listHtmlTableName = "";
	private String listHtmlTableSql = "";
	private String errorPageTableName = "";
	private String errorPageTableSql = "";
	private String pageIndexName = "";
	private String modeColName = "";
	// 记录列表页爬取位置的字段名

	private int startIndex = 0;
	private int endIndex = 0;
	private int mode;

	private Configure conf = null;
	private Set<Integer> acceptStatCode = null;
	private Site site = null;
	private Spider spider = null;

	private void init(String configureName) {
		this.conf = new Configure(configureName);
		this.acceptStatCode = this.initAcceptStatCode();
		this.domain = conf.getDomain();
		this.listHtmlTableName = this.domain + "_html_list";
		this.errorPageTableName = this.domain + "_error_page";
		this.pageIndexName = this.domain + "_page_index";
		this.modeColName = this.domain + "_mode";

		initSql();
		// 初始化表
		initTable();

		startIndex = this.getPointer(pageIndexName,
				this.conf.getStartPageIndex());
		mode = this.getMode(this.modeColName, this.conf.getMode());

		pipeline.setListHtmlTableName(this.listHtmlTableName);
		pipeline.setErrorPageTableName(this.errorPageTableName);

		// List<String[]> proxies = Utils.getProxies(conf);
		this.site = Site.me().setSleepTime(conf.getSleepTimeThread())
				.setTimeOut(conf.getTimeOut())
				.setRetryTimes(conf.getRetryTimes())
				.setCycleRetryTimes(conf.getCycleRetryTimes())
				.setDomain(conf.getDomain()).setUserAgent(conf.getUserAgent())
				.setAcceptStatCode(acceptStatCode);
		// .setHttpProxyPool(proxies);
		// .setCharset("UTF-8");
		this.spider = Spider
				.create(new ListHtmlProcessor(site))
				.addPipeline(pipeline)
				// .addUrl(conf.getStartUrl())
				// .setScheduler(new QueueScheduler().setDuplicateRemover(new
				// BloomFilterDuplicateRemover(10000))
				.thread(conf.getThreadNum())
				.setScheduler(new SimpleScheduler());
	}

	private void initSql() {
		this.pointersSql = FileReader.readFile("./sql/pointers.sql");
		this.listHtmlTableSql = FileReader.readFile("./sql/list_html.sql");
		this.errorPageTableSql = FileReader.readFile("./sql/error_page.sql");
	}

	private void initTable() {
		initOneTable(this.pointersTableName, this.pointersSql);
		initOneTable(this.listHtmlTableName, this.listHtmlTableSql);
		initOneTable(this.errorPageTableName, this.errorPageTableSql);
	}

	private void initOneTable(String table, String fields) {
		if (!tableHelper.isTableExist(table)) { // 表不存在即初始化

			tableHelper.createTable(table, fields);
			logger.info("create table " + table + " success!!!");
		}
	}

	// 取得指针
	private int getPointer(String tableName, int defaultValue) {
		int pointer = 0;
		try {
			pointer = pointerDao.readPointer(this.pointersTableName, tableName);
			return pointer + 1;
		} catch (BindingException e) {
			pointerDao.insertPointer(this.pointersTableName, tableName,
					defaultValue - 1);
		}
		return defaultValue;

	}

	private int getMode(String tableName, int defaultValue) {
		int pointer = 0;
		try {
			pointer = pointerDao.readPointer(this.pointersTableName, tableName);
			return pointer;
		} catch (BindingException e) {
			pointerDao.insertPointer(this.pointersTableName, tableName,
					defaultValue);
		}
		return defaultValue;
	}

	public void crawl(String configureName) {
		this.init(configureName);

		this.acceptStatCode = this.initAcceptStatCode();
//		pipeline.setTableName(this.listHtmlTableName);
//		pipeline.setErrorPageTableName(this.errorPageTableName);

		this.startSpider();
	}

	private List<String> getOrderedUrl(String purl, String surl, int per,
			int incIndex, int startIndex) {
		List<String> urls = new ArrayList<String>();
		for (int g = startIndex; g < startIndex + per; g++) {
			int pageindex = g + incIndex;
			String url = purl + pageindex + surl;
			urls.add(url);
			// System.out.println(url);
		}
		return urls;
	}

	private void startSpider() {
		// List<String> urls = new ArrayList<String>();
		if (this.mode == 0) { // 镜像模式
			this.mirrorModeCrawl();
			logger.info("change to increment mode!!!");
			pointerDao.updatePointer(this.pointersTableName, this.modeColName,
					1);

			this.reInit();
			this.incrementModeCrawl();
		} else if (this.mode == 1) { // 增量模式
			this.incrementModeCrawl();
		} else {
			logger.info("mode error!!!");
		}
	}

	private void reInit() {
		this.startIndex = this.conf.getStartPageIndex();
	}

	// 镜像模式爬取
	private void mirrorModeCrawl() {
		List<String> urls = new ArrayList<String>();
		this.endIndex = this.conf.getEndInPageIndex();
		for (int i = this.startIndex; i <= this.endIndex; i += conf.getPageF()) {
			// 采用startUrls ，配合0.5.1支持sourceRegion
			// 获得链接
			urls = getOrderedUrl(conf.getPrefix(), conf.getPostfix(),
					conf.getPageF(), 0, i);
			spider.startUrls(urls);
			spider.run();
			// 更新指针
			pointerDao.updatePointer(this.pointersTableName,
					this.pageIndexName, i + conf.getPageF() - 1);
			// sleep
			try {
				logger.info("sleep " + conf.getSleepTimeSpider() / 1000 + "s");
				Thread.sleep(conf.getSleepTimeSpider());
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
	}

	// 镜像模式爬取
	private void incrementModeCrawl() {
		List<String> urls = new ArrayList<String>();
		this.endIndex = this.conf.getIncrementalPages();
		// System.out.println(startIndex);
		// System.out.println(endIndex);
		while (true) {
			for (int i = this.startIndex; i <= this.endIndex; i += conf.getPageF()) {
				// 采用startUrls ，配合0.5.1支持sourceRegion
				// 获得链接
				urls = getOrderedUrl(conf.getPrefix(), conf.getPostfix(),
						conf.getPageF(), 0, i);
				spider.startUrls(urls);
				spider.run();
				// 更新指针
				pointerDao.updatePointer(this.pointersTableName,
						this.pageIndexName, i + conf.getPageF() - 1);
				// sleep
				try {
					logger.info("sleep " + conf.getSleepTimeSpider() / 1000
							+ "s");
					Thread.sleep(conf.getSleepTimeSpider());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			logger.info("increment finish!!!");
			logger.info("sleep "+conf.getIncrementSleepTime()+"s");
			try {
				Thread.sleep(conf.getIncrementSleepTime()*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.reInit();
		}
	}

	private Set<Integer> initAcceptStatCode() {
		Set<Integer> acceptStatCode = new HashSet<Integer>();
		acceptStatCode.add(200);
		acceptStatCode.add(404);
		acceptStatCode.add(500);
		acceptStatCode.add(503);
		return acceptStatCode;
	}

	public static void main(String[] args) throws InterruptedException {
		// DOMConfigurator.configure("log4j.xml");
		// System.err.println(new File("log4j.xml").getAbsolutePath());
		String configureName = "";
		if (args.length != 0)
			configureName = args[0].toString();
		if (configureName.equals("")) {
			configureName = "ossean";
		} else {
		}
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:/spring/applicationContext*.xml");
		final ListHtmlCrawler htmlCrawler = applicationContext
				.getBean(ListHtmlCrawler.class);
		htmlCrawler.crawl(configureName);
	}
}
