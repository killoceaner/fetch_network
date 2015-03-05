package net.trustie.webmagic.one;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.trustie.webmagic.one.model.DetailHtml;
import net.trustie.webmagic.one.model.ListHtml;
import net.trustie.webmagic.utils.Utils;

import org.apache.commons.codec.digest.DigestUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class DetailHtmlProcessor implements PageProcessor {
	private Site site;

	// private String listHtmlTName;// listHtmlTableName
	// private String reList;

	public DetailHtmlProcessor(Site site) {
		this.site = site;
	}

	@Override
	public void process(Page page) {

		DetailHtml detailHtml = new DetailHtml();
		detailHtml.setStatusCode(page.getStatusCode());
		String html = page.getRawText();
		detailHtml.setHtml(html);
		String url = page.getRequest().getUrl();
		detailHtml.setUrl(url);
		if (url != null) {
			detailHtml.setUrlMd5(DigestUtils.md5Hex(page.getUrl().get()));
		}
		detailHtml.setType("Detail");

		detailHtml.setCrawledTime(Utils.getNow());
		if (html != null) {
			detailHtml.setPageMd5(DigestUtils.md5Hex(html));
		}

		// 存入扩展字段，后面持久化Pipeline调用
		page.putField("model", detailHtml);
		// page.putField("listTableName", listTableName);
	}

	@Override
	public Site getSite() {
		return site;
	}
}
