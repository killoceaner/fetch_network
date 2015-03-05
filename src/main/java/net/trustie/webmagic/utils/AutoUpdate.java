package net.trustie.webmagic.utils;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

/**
 * Created by gyiang on 2014/8/19.
 */
public class AutoUpdate {

	// private static Spider s;
	public static void go(Spider spider, int startIndex, int endIndex, int f,
			String purl, String furl, int sleepTime, int threadNum,
			PageModelPipeline pageModelPipeline, Class... pageModels) {
		// StoreConf.StoreXML(spider,threadNum,startIndex,endIndex,f,sleepTime);
		OrderSpider.startSpider(spider, startIndex, endIndex, f, purl, furl,0);

		while (true) {
			LoadConf prop = new LoadConf(spider.getSite().getDomain());
			Spider s = OOSpider
					.create(Site.me().setRetryTimes(prop.getRetryTimes())
							.setCycleRetryTimes(prop.getCycleRetryTimes())
							.setSleepTime(prop.getSleepTimeThread())
							.setUserAgent(prop.getUserAgent())
							.setDomain(prop.getDomain()), pageModelPipeline,
							pageModels).setSpawnUrl(prop.isSpawnUrl())
					// .setScheduler(spider.getScheduler())
					.thread(threadNum);
			OrderSpider.startSpider(s, prop.getStartPageIndex(),
					prop.getEndInPageIndex(), prop.getPageF(), purl, furl,0);
			// s= spider;
			try {
				Thread.sleep(prop.getSleepTimeThread());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
