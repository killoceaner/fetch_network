package net.trustie.webmagic.oneX;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.handler.PatternRequestMatcher;
import us.codecraft.webmagic.handler.RequestMatcher;
import us.codecraft.webmagic.handler.SubPageProcessor;

/**
 * Created by Administrator on 2014/11/15.
 */
public class HtmlSubProcessorPatter extends PatternRequestMatcher implements
		SubPageProcessor {

	/**
	 * @param pattern
	 *            url pattern to handle
	 */
	public HtmlSubProcessorPatter(String pattern) {
		super(pattern);
	}

	@Override
	public MatchOther processPage(Page page) {
		System.out.println("i konwn it！URL_LIST");
		return MatchOther.YES;
	}
}
