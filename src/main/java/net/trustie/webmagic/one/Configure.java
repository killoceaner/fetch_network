package net.trustie.webmagic.one;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class Configure {
	private int sleepTimeThread = 5000;
	private int retryTimes = 0;
	private int cycleRetryTimes = 0;
	private int timeOut = 5000;
	private String userAgent = "UserAgent.Browser";
	private String charset;
	private int threadNum = 5;
	private boolean isSpawnUrl = true;
	private int startPageIndex = 1;
	private int endInPageIndex = 6;
	private int pageF = 1;
	private int sleepTimeSpider = 36000;
	private String domain;
	private String startUrl;
	private String urlList;
	private String urlPost;
	private int offset;
	private int limitLine;
	private int noUrlWaitTime;
	private String fixAllRelativeHrefs;
	private Properties prop = new Properties();
	private String prefix;
	private String postfix;
	private String extractMethod;
	private String proxyIp;
	private String proxyPort;
	private int mode;
	private int incrementalPages;
	private int incrementSleepTime;

	Logger log4j = Logger.getLogger(Configure.class);

	public Configure() {
		try {
			FileInputStream fis = null;
			fis = new FileInputStream("spider.conf.xml");
			prop.loadFromXML(fis);
			prop.list(System.out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		config();
	}

	public Configure(String confPath) {
		confPath = confPath + ".xml";
		try {
			URL url = ClassLoader.getSystemResource(confPath);
			InputStream is = url.openStream();
			prop.loadFromXML(is);
			// prop.list(System.out);
		} catch (FileNotFoundException e) {
			log4j.error("FileNotFoundException & loading default config xml! & no default config file");
			e.printStackTrace();
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		config();
	}

	private void config() {
		this.sleepTimeThread = Integer.parseInt(prop
				.getProperty("sleepTimeThread"));
		this.timeOut = Integer.parseInt(prop.getProperty("timeOut"));
		this.retryTimes = Integer.parseInt(prop.getProperty("retryTimes"));
		this.cycleRetryTimes = Integer.parseInt(prop
				.getProperty("cycleRetryTimes"));
		this.threadNum = Integer.parseInt(prop.getProperty("threadNum"));
		this.startPageIndex = Integer.parseInt(prop
				.getProperty("startPageIndex"));
		this.endInPageIndex = Integer.parseInt(prop
				.getProperty("endInPageIndex"));
		this.pageF = Integer.parseInt(prop.getProperty("pageF"));
		this.sleepTimeSpider = Integer.parseInt(prop
				.getProperty("sleepTimeSpider"));
		this.isSpawnUrl = Boolean.parseBoolean(prop.getProperty("isSpawnUrl"));
		this.userAgent = prop.getProperty("userAgent");
		this.domain = prop.getProperty("domain");
		this.startUrl = prop.getProperty("startUrl");
		this.urlList = prop.getProperty("urlList");
		this.urlPost = prop.getProperty("urlPost");
		this.offset = Integer.parseInt(prop.getProperty("offset"));
		this.limitLine = Integer.parseInt(prop.getProperty("limitLine"));
		this.noUrlWaitTime = Integer
				.parseInt(prop.getProperty("noUrlWaitTime"));

		this.fixAllRelativeHrefs = prop.getProperty("fixAllRelativeHrefs");
		this.prefix = prop.getProperty("prefixUrl");
		this.postfix = prop.getProperty("postfixUrl");
		this.extractMethod = prop.getProperty("extractMethod");
		this.proxyIp = prop.getProperty("proxyIp");
		this.proxyPort =prop.getProperty("proxyPort");
		this.mode = Integer.parseInt(prop.getProperty("mode"));
		this.incrementalPages = Integer.parseInt(prop.getProperty("incrementalPages"));
		this.incrementSleepTime = Integer.parseInt(prop.getProperty("incrementSleepTime"));
	}
	
	

	/**
	 * @return the incrementSleepTime
	 */
	public int getIncrementSleepTime() {
		return incrementSleepTime;
	}

	/**
	 * @param incrementSleepTime the incrementSleepTime to set
	 */
	public void setIncrementSleepTime(int incrementSleepTime) {
		this.incrementSleepTime = incrementSleepTime;
	}

	public int getSleepTimeThread() {
		return sleepTimeThread;
	}

	public void setSleepTimeThread(int sleepTimeThread) {
		this.sleepTimeThread = sleepTimeThread;
	}

	public int getRetryTimes() {
		return retryTimes;
	}

	public void setRetryTimes(int retryTimes) {
		this.retryTimes = retryTimes;
	}

	public int getCycleRetryTimes() {
		return cycleRetryTimes;
	}

	public void setCycleRetryTimes(int cycleRetryTimes) {
		this.cycleRetryTimes = cycleRetryTimes;
	}

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public int getThreadNum() {
		return threadNum;
	}

	public void setThreadNum(int threadNum) {
		this.threadNum = threadNum;
	}

	public boolean isSpawnUrl() {
		return isSpawnUrl;
	}

	public void setSpawnUrl(boolean isSpawnUrl) {
		this.isSpawnUrl = isSpawnUrl;
	}

	public int getStartPageIndex() {
		return startPageIndex;
	}

	public void setStartPageIndex(int startPageIndex) {
		this.startPageIndex = startPageIndex;
	}

	public int getEndInPageIndex() {
		return endInPageIndex;
	}

	public void setEndInPageIndex(int endInPageIndex) {
		this.endInPageIndex = endInPageIndex;
	}

	public int getPageF() {
		return pageF;
	}

	public void setPageF(int pageF) {
		this.pageF = pageF;
	}

	public int getSleepTimeSpider() {
		return sleepTimeSpider;
	}

	public void setSleepTimeSpider(int sleepTimeSpider) {
		this.sleepTimeSpider = sleepTimeSpider;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getStartUrl() {
		return startUrl;
	}

	public void setStartUrl(String startUrl) {
		this.startUrl = startUrl;
	}

	public String getUrlList() {
		return urlList;
	}

	public void setUrlList(String urlList) {
		this.urlList = urlList;
	}

	public String getUrlPost() {
		return urlPost;
	}

	public void setUrlPost(String urlPost) {
		this.urlPost = urlPost;
	}

	public Properties getProp() {
		return prop;
	}

	public void setProp(Properties prop) {
		this.prop = prop;
	}

	public Logger getLog4j() {
		return log4j;
	}

	public void setLog4j(Logger log4j) {
		this.log4j = log4j;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimitLine() {
		return limitLine;
	}

	public void setLimitLine(int limitLine) {
		this.limitLine = limitLine;
	}

	public int getNoUrlWaitTime() {
		return noUrlWaitTime;
	}

	public void setNoUrlWaitTime(int noUrlWaitTime) {
		this.noUrlWaitTime = noUrlWaitTime;
	}

	


	public String getFixAllRelativeHrefs() {
		return fixAllRelativeHrefs;
	}

	public void setFixAllRelativeHrefs(String fixAllRelativeHrefs) {
		this.fixAllRelativeHrefs = fixAllRelativeHrefs;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getPostfix() {
		return postfix;
	}

	public void setPostfix(String postfix) {
		this.postfix = postfix;
	}

	public String getExtractMethod() {
		return extractMethod;
	}

	public void setExtractMethod(String extraMethod) {
		this.extractMethod = extraMethod;
	}


	/**
	 * @return the proxyIp
	 */
	public String getProxyIp() {
		return proxyIp;
	}

	/**
	 * @return the proxyPort
	 */
	public String getProxyPort() {
		return proxyPort;
	}

	/**
	 * @param proxyIp the proxyIp to set
	 */
	public void setProxyIp(String proxyIp) {
		this.proxyIp = proxyIp;
	}

	/**
	 * @param proxyPort the proxyPort to set
	 */
	public void setProxyPort(String proxyPort) {
		this.proxyPort = proxyPort;
	}

	/**
	 * @return the mode
	 */
	public int getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(int mode) {
		this.mode = mode;
	}

	/**
	 * @return the incrementalPages
	 */
	public int getIncrementalPages() {
		return incrementalPages;
	}

	/**
	 * @param incrementalPages the incrementalPages to set
	 */
	public void setIncrementalPages(int incrementalPages) {
		this.incrementalPages = incrementalPages;
	}
	
	
	
}
