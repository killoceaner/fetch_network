package net.trustie.webmagic.one;

import net.trustie.webmagic.one.HtmlSubProcessorList;
import net.trustie.webmagic.one.DetailHtmlSubProcessor;
import net.trustie.webmagic.utils.LoadConf;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.handler.CompositePageProcessor;

/**
 * Created by Administrator on 2014/11/15.
 */
public class HtmlProcessor extends CompositePageProcessor {

	private Site site = null;
	private String regexUrl;
	private String sourceRegion;

	public HtmlProcessor(Site site, String regexUrlList, String regexUrlPost,
			String listTableName) {

		super(site);
		this.site = site;
		// super.addSubPageProcessor(new HtmlSubProcessorPost());
		super.addSubPageProcessor(new HtmlSubProcessorList(regexUrlList,
				regexUrlPost, listTableName));
	}

	/*
	 * @Override public void process(Page page) {
	 * page.addTargetRequests(page.getHtml
	 * ().xpath(sourceRegion).links().regex(regexUrl).all());
	 * 
	 * }
	 */

}
