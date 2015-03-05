package net.trustie.webmagic.one;

import net.trustie.webmagic.one.model.DetailHtml;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.handler.SubPageProcessor;
import us.codecraft.webmagic.selector.PlainText;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2014/11/15.
 */
public class DetailHtmlSubProcessor implements SubPageProcessor {

	private Logger logger = Logger.getLogger(DetailHtmlSubProcessor.class);

	// public static final String URL_POST =
	// "http://blog\\.sina\\.com\\.cn/s/blog_\\w+\\.html";

	@Override
	public MatchOther processPage(Page page) {

//		SimpleDateFormat timestampFormat = new SimpleDateFormat(
//				"yyyyMMddHHmmss");
		SimpleDateFormat crawledTimeFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		//String timestamp = timestampFormat.format(date);
		String crawledTime = crawledTimeFormat.format(date);

		DetailHtml detailHtml = new DetailHtml();
		if (page.getStatusCode() == 404 || page.getStatusCode() == 503) {
			detailHtml.setStatusCode(page.getStatusCode());
			//detailHtml.setTimestamp(page.getStatusCode() + "");
			page.setSkip(true);// 跳过这个页面
		} else {
			//detailHtml.setStatusCode(statusCode);
		}
		
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

		detailHtml.setCrawledTime(crawledTime);
		detailHtml.setPageMd5(DigestUtils.md5Hex(page.getRawText().toString()));

		// 存入扩展字段，后面持久化Pipeline调用
		page.putField("model", detailHtml);
		//page.putField("listTableName", listTableName);

		return MatchOther.YES;
	}

//	public DetailHtmlSubProcessor(String postTableName, String listTableName) {
//		this.postTableName = postTableName;
//		this.listTableName = listTableName;
//	}

	@Override
	public boolean match(Request page) {
		/*
		 * if (new PlainText(page.getUrl()).regex(URL_POST).match()){ return
		 * true; }else { return false; }
		 */
		return true;
	}
}
