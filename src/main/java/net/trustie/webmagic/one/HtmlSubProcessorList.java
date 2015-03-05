package net.trustie.webmagic.one;

import org.apache.log4j.Logger;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.handler.SubPageProcessor;

import java.util.List;

/**
 * Created by gan on 2014/11/15. Notified by gan on 2014/11/
 */
public class HtmlSubProcessorList implements SubPageProcessor {
	/*
	 * 最初使用static final方法 public static final String URL_LIST; public static
	 * final String URL_POST ;
	 */
	private String urlList;
	private String urlPost;
	private String listTableName;

	public HtmlSubProcessorList(String urlList, String urlPost,
			String listTableName) {
		this.urlList = urlList;
		this.urlPost = urlPost;
		this.listTableName = listTableName;
	}

	@Override
	public MatchOther processPage(Page page) {

		// 加入爬取队列，策略改变后，变成存入数据库
		/*
		 * page.addTargetRequests(page.getHtml().xpath(
		 * "//div[@class=\"articleList\"]").links().regex(urlPost).all());
		 * page.addTargetRequests(page.getHtml().links().regex(urlList).all());
		 * page.getTargetRequests()
		 */
		// 列表页链接加入，发现新的列表页

		// System.out.println(page.getRawText());
		page.addTargetRequests(page.getHtml().links().regex(urlList).all());
		// 获取post链接
		List<String> targetUrl = page.getHtml().links().regex(urlPost).all();
		// 列表页的url也存入，去爬
		targetUrl.add(page.getUrl().get());

		// 交给pipeline
		page.putField("findUrl", targetUrl);
		page.putField("listTableName", listTableName);

		// 只取列表页，跳过持久化过程
		// page.setSkip(false);
		return MatchOther.YES;
	}

	@Override
	public boolean match(Request page) {
		return PatternUrl.match(page, urlList);
	}
}
