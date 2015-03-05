package net.trustie.webmagic.one;

import net.trustie.webmagic.extrator.CSSPathExtractor;
import net.trustie.webmagic.extrator.Extractor;
import net.trustie.webmagic.extrator.URLPatternExtractor;
import net.trustie.webmagic.one.dao.ErrorPageDao;
import net.trustie.webmagic.one.dao.URLDao;
import net.trustie.webmagic.one.dao.PointerDAO;
import net.trustie.webmagic.one.dao.DetailHtmlDao;
import net.trustie.webmagic.one.dao.ListHtmlDao;
import net.trustie.webmagic.one.model.ListHtml;
import net.trustie.webmagic.utils.FileReader;
import net.trustie.webmagic.utils.TableHelper;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.ibatis.binding.BindingException;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.model.annotation.ExtractByUrl;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.utils.UrlUtils;

import javax.annotation.Resource;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2014/12/21.
 */
@Component
public class UrlExtractor {
	Logger logger = Logger.getLogger(this.getClass());
	private static int batchSize = 10;
	@Resource
	private ListHtmlDao listHtmlDao;
	@Resource
	private PointerDAO pointerDao;
	@Resource
	private URLDao urlDao;
	@Resource
	private ErrorPageDao errorPageDao;

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
	private Configure conf = null;

	private void init(String configureName) {
		conf = new Configure(configureName);
		domain = conf.getDomain();
		listHtmlTableName = domain + "_html_list";
		urlTableName = domain + "_url";
		errorPageTableName = domain + "_error_page";
		initSql();
		initTable();
	}

	private void initSql() {
		this.pointersSql = FileReader.readFile("./sql/pointers.sql");
		this.urlTableSql = FileReader.readFile("./sql/url.sql");
		this.listHtmlTableSql = FileReader.readFile("./sql/list_html.sql");
		this.errorPageTableSql = FileReader.readFile("./sql/error_page.sql");
	}

	private void initTable() {
		initOneTable(this.pointersTableName, this.pointersSql);
		initOneTable(this.listHtmlTableName, this.listHtmlTableSql);
		initOneTable(this.urlTableName, this.urlTableSql);
		initOneTable(this.errorPageTableName, this.errorPageTableSql);
	}

	private void initOneTable(String table, String fields) {
		if (!tableHelper.isTableExist(table)) { // 表不存在即初始化

			tableHelper.createTable(table, fields);
			logger.info("create table " + table + " success!!!");
		}
	}

	public void exec(String configureName) throws InterruptedException {
		this.init(configureName);
		int startId = this.getPointer(listHtmlTableName);
		List<ListHtml> list = new ArrayList<ListHtml>();
		Set<String> urlSet = new HashSet<String>();

		Extractor extractor = null;
		if ("URLPattern".equals(conf.getExtractMethod())) {
			extractor = new URLPatternExtractor(conf.getUrlPost());
		} else if ("CSSPath".equals(conf.getExtractMethod())) {
			extractor = new CSSPathExtractor(conf.getUrlPost());
		} else {
			logger.error("extract method error!!!");
		}
		SimpleDateFormat extractedTimeFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date = null;
		String extractedTime = "";
		Html html = null;
		String fixRelativeHref = conf.getFixAllRelativeHrefs();
		while(true) {
			//Thread.sleep(100);
			// int startId = start+UrlExtractor.batchSize;
			// int endId = startId + UrlExtractor.batchSize;
			list = listHtmlDao.getBatch(listHtmlTableName, startId,
					UrlExtractor.batchSize);
			if (list.size() == 0) {
				System.out.println("no listHtml in queue--->Thread.sleep()");
				Thread.sleep(conf.getNoUrlWaitTime());
				continue;
			}
			for (ListHtml listHtml : list) {
				if (listHtml == null || listHtml.getHtml() == "") {
					continue;// 跳过
				}
				// SimpleDateFormat timestampFormat = new SimpleDateFormat(
				// "yyyyMMddHHmmss");
				date = new Date();
				// String timestamp = timestampFormat.format(date);
				extractedTime = extractedTimeFormat.format(date);

				html = new Html(UrlUtils.fixAllRelativeHrefs(
						listHtml.getHtml(), fixRelativeHref));

				urlSet = extractor.extract(html);

				String pageMd5 = DigestUtils.md5Hex(listHtml.getHtml());// html.get()
																		// same
				// rawText
				String error = null;

				try {
					for (String url : urlSet) {
						try {
							urlDao.addURL(urlTableName, url, extractedTime);
						} catch (Exception e) {
							logger.error(e.getMessage());
							error = "500";
						}
					}
					// 存完一次后标记
					if (error != null) {
						// pointerDao.updatePointer(conf.getListHtmlTableName(),
						// listHtml.getId());
						// listHtmlDao.timestamp(listHtmlTableName, pageMd5,
						// timestamp);
					} else {// 成功
						pointerDao.updatePointer(this.pointersTableName,
								listHtmlTableName, listHtml.getId());
						// listHtmlDao
						// .timestamp(listHtmlTableName, pageMd5, error);
					}
				} catch (Exception e) {
					logger.error(e.getMessage());

				}

				logger.info("extracted id = " + listHtml.getId() + " "
						+ listHtml.getUrl());

				startId = listHtml.getId();
				urlSet.clear();

			}
			list.clear();
			// startId=
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
		final UrlExtractor urlExtractor = applicationContext
				.getBean(UrlExtractor.class);
		urlExtractor.exec(configureName);
		// urlExtrator.crawl(configureName);
	}
}
