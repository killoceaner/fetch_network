package net.trustie.webmagic.one;

import net.trustie.webmagic.one.model.DetailHtml;
import net.trustie.webmagic.one.model.ListHtml;
import net.trustie.webmagic.utils.Utils;

import org.apache.commons.codec.digest.DigestUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gyiang on 2014/12/19.
 */
public class ListHtmlProcessor implements PageProcessor {

	private Site site;

	public ListHtmlProcessor(Site site) {
		this.site = site;
	}

	@Override
	public void process(Page page) {

		ListHtml listHtml = new ListHtml();

		listHtml.setStatusCode(page.getStatusCode());
		String html = page.getRawText();
		listHtml.setHtml(html);

		listHtml.setCrawledTime(Utils.getNow());
		String url;// = page.getUrl().get();
		url = page.getRequest().getUrl();
		listHtml.setUrl(url);
		if (url != null) {
			listHtml.setUrlMd5(DigestUtils.md5Hex(url));
		}
		if (html != null) {
			listHtml.setPageMd5(DigestUtils.md5Hex(html));// 用于确定是已经抽过
		}
		listHtml.setType("List");

		// 存入扩展字段，后面持久化Pipeline调用
		page.putField("model", listHtml);
	}

	@Override
	public Site getSite() {
		return site;
	}
}
