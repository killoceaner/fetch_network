package net.trustie.webmagic.oneX;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.handler.PatternProcessor;
import us.codecraft.webmagic.handler.PatternRequestMatcher;
import us.codecraft.webmagic.handler.RequestMatcher;
import us.codecraft.webmagic.handler.SubPageProcessor;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by Administrator on 2014/11/15.
 */
class HtmlSubProcessorPatter2 extends PatternProcessor {

	/**
	 * @param pattern
	 *            url pattern to handle
	 */
	public HtmlSubProcessorPatter2(String pattern) {
		super(pattern);
	}

	@Override
	public MatchOther processPage(Page page) {
		return null;
	}

	@Override
	public MatchOther processResult(ResultItems resultItems, Task task) {
		return null;
	}
}
