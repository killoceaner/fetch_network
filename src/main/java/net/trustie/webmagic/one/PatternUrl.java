package net.trustie.webmagic.one;

import us.codecraft.webmagic.Request;

import java.util.regex.Pattern;

/**
 * Created by gyiang on 2014/11/19.
 */
public class PatternUrl {
	protected String pattern;
	private Pattern patternCompiled;

	public static boolean match(Request request, String pattern) {
		return Pattern.compile(pattern).matcher(request.getUrl()).matches();
	}
}
