package net.trustie.webmagic.others;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.trustie.webmagic.one.Configure;
import net.trustie.webmagic.one.DetailHtmlProcessor;
import net.trustie.webmagic.one.DetailHtmlSubProcessor;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.handler.CompositePageProcessor;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.scheduler.SimpleScheduler;

public class StaticTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Configure conf = new Configure("ossean");
		Set<Integer> acceptStatCode = new HashSet<Integer>();
		acceptStatCode.add(200);
		acceptStatCode.add(404);
		acceptStatCode.add(500);
		acceptStatCode.add(503);
		Site site = Site.me().setSleepTime(conf.getSleepTimeThread())
				.setTimeOut(conf.getTimeOut())
				.setRetryTimes(conf.getRetryTimes())
				.setCycleRetryTimes(conf.getCycleRetryTimes())
				.setDomain(conf.getDomain()).setUserAgent(conf.getUserAgent())
				.setAcceptStatCode(acceptStatCode);
		List<String> urlList = new ArrayList<String>();
		urlList.add("http://localhost/open_source_projects?page=1");
		while(true){
			Spider
			.create(new DetailHtmlProcessor(site))
			.addPipeline(new ConsolePipeline()).setScheduler(new SimpleScheduler()).thread(conf.getThreadNum()).startUrls(urlList).run();
		}
	}

}
