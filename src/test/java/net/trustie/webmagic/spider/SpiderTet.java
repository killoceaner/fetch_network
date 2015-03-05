package net.trustie.webmagic.spider;

import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class SpiderTet {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> urls = new ArrayList<String>();
		urls.add("http://www.oschina.net/question?catalog=1&show=active&p=1");
		urls.add("http://www.oschina.net/question?catalog=1&show=active&p=2");
		urls.add("http://www.oschina.net/question?catalog=1&show=active&p=3");
		Spider spider = Spider.create(new PageProcessor() {
			
			@Override
			public void process(Page arg0) {
				// TODO Auto-generated method stub
				System.out.println(1);
			}
			
			@Override
			public Site getSite() {
				// TODO Auto-generated method stub
				return null;
			}
		}).addPipeline(new ConsolePipeline()).thread(3).startUrls(urls);
		spider.run();
	}

}
