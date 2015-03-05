package net.trustie.webmagic.utils;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

/**
 * Created by gyiang on 2014/8/19.
 */
public class AutoUpdate_ {

	// private static Spider s;
	public static void go(Spider spider, int startIndex, int endIndex, int f,
			String purl, String furl, int sleepTime, int threadNum,
			PageModelPipeline pageModelPipeline, Class... pageModels) {
		OrderSpider.startSpider(spider, startIndex, endIndex, f, purl, furl,0);
		while (true) {
			Spider s = OOSpider
					.create(Site
							.me()
							.setRetryTimes(spider.getSite().getRetryTimes())
							.setCycleRetryTimes(
									spider.getSite().getCycleRetryTimes())
							.setSleepTime(spider.getSite().getSleepTime())
							.setUserAgent(spider.getSite().getUserAgent()),
							pageModelPipeline, pageModels)
					.setSpawnUrl(spider.isSpawnUrl())
					// .setScheduler(spider.getScheduler())
					.thread(threadNum);
			OrderSpider.startSpider(s, startIndex, endIndex, f, purl, furl,0);
			// s= spider;
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
