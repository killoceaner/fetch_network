package net.trustie.webmagic.oneX;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.handler.PatternRequestMatcher;
import us.codecraft.webmagic.handler.SubPageProcessor;

/**
 * Created by Administrator on 2014/11/15.
 */
public class HtmlSubProcessorTest2 extends PatternRequestMatcher implements
		SubPageProcessor {

	/**
	 * @param pattern
	 *            url pattern to handle
	 */
	public HtmlSubProcessorTest2(String pattern) {
		super(pattern);
	}

	@Override
	public MatchOther processPage(Page page) {
		System.out.println("i got it！URL_POST");
		return MatchOther.YES;
	}
}
