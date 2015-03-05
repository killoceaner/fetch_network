package net.trustie.webmagic.one;

import net.trustie.webmagic.one.model.DetailHtml;
import net.trustie.webmagic.one.model.ListHtml;
import net.trustie.webmagic.utils.Utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gyiang on 2014/12/19.
 */
public class ErrorPageProcessor implements PageProcessor {
	Logger logger = Logger.getLogger(this.getClass());
	private Site site;

	public ErrorPageProcessor(Site site) {
		this.site = site;
	}

	@Override
	public void process(Page page) {

		if ("List".equals((String) (page.getRequest().getExtra("type")))) {
			constructListHtml(page);
		} else if ("Detail"
				.equals((String) (page.getRequest().getExtra("type")))) {
			constructDetailHtml(page);
		} else {
			logger.info("errorPage type error :" + page.getRequest().getUrl());
		}
	}

	private void constructListHtml(Page page) {
		ListHtml listHtml = new ListHtml();
		listHtml.setStatusCode(page.getStatusCode());
		String html = "";
		html = page.getRawText();
		listHtml.setHtml(html);

		listHtml.setCrawledTime(Utils.getNow());
		String url = page.getUrl().get();
		listHtml.setUrl(url);
		listHtml.setUrlMd5(DigestUtils.md5Hex(url));
		listHtml.setPageMd5(DigestUtils.md5Hex(html));// 用于确定是已经抽过
		listHtml.setType("List");
		page.putField("model", listHtml);
	}

	private void constructDetailHtml(Page page) {
		DetailHtml detailHtml = new DetailHtml();

		detailHtml.setStatusCode(page.getStatusCode());

		detailHtml.setHtml(page.getRawText().toString());
		detailHtml.setUrl(page.getUrl().get());
		detailHtml.setUrlMd5(DigestUtils.md5Hex(page.getUrl().get()));
		detailHtml.setType("Detail");

		/*
		 * if(page.getUrl().regex(reList).match()) { htmlModel.setType("list");
		 * }else if(page.getUrl().regex(rePost).match()) {
		 * htmlModel.setType("post"); }else {
		 * logger.error("unknown RE，current url:"+page.getUrl().get()); }
		 */

		detailHtml.setCrawledTime(Utils.getNow());
		detailHtml.setPageMd5(DigestUtils.md5Hex(page.getRawText().toString()));

		// 存入扩展字段，后面持久化Pipeline调用
		page.putField("model", detailHtml);
	}

	@Override
	public Site getSite() {
		return site;
	}
}
