package net.trustie.webmagic.utils;

import us.codecraft.webmagic.Spider;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class StoreConf {

	public static void StoreXML(Spider spider, int threadNum, int startIndex,
			int endIndex, int pageF, int sleepTimeSpider) {
		Properties prop = new Properties();
		prop.setProperty("domain", spider.getSite().getDomain() + "");
		prop.setProperty("retryTimes", spider.getSite().getRetryTimes() + "");
		prop.setProperty("cycleRetryTimes", spider.getSite()
				.getCycleRetryTimes() + "");
		prop.setProperty("timeOut", spider.getSite().getTimeOut() + "");
		prop.setProperty("sleepTimeThread", spider.getSite().getSleepTime()
				+ "");
		prop.setProperty("userAgent", spider.getSite().getUserAgent() + "");
		prop.setProperty("threadNum", threadNum + "");
		prop.setProperty("isSpawnUrl", spider.isSpawnUrl() + "");
		prop.setProperty("startPageIndex", startIndex + "");
		prop.setProperty("endInPageIndex", endIndex + "");
		prop.setProperty("pageF", pageF + "");
		prop.setProperty("sleepTimeSpider", sleepTimeSpider + "");
		prop.setProperty("proxy", spider.getSite().getHttpProxyPool() + "");
		FileOutputStream fos;
		try {
			fos = new FileOutputStream("spider.conf.xml");
			prop.storeToXML(fos, "Spider");
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
