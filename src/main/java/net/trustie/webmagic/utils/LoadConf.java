package net.trustie.webmagic.utils;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class LoadConf {
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
	private Properties prop = new Properties();
	Logger log4j = Logger.getLogger(LoadConf.class);

	public LoadConf() {
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

	public LoadConf(String confPath) {
		System.out.println(new File(".").getAbsolutePath());
		confPath = "conf/" + "conf." + confPath + ".xml";
		System.err.println(new File(confPath).getAbsolutePath());
		try {
			FileInputStream fis = null;
			fis = new FileInputStream(confPath);
			prop.loadFromXML(fis);
			prop.list(System.out);
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
}
