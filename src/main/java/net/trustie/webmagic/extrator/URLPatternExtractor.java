package net.trustie.webmagic.extrator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.trustie.webmagic.utils.StringUtils;
import us.codecraft.webmagic.selector.Html;

public class URLPatternExtractor implements Extractor{
	private String pattern = "";
	
	
	/**
	 * @param pattern
	 */
	public URLPatternExtractor(String pattern) {
		this.pattern = pattern;
	}


	@Override
	public Set<String> extract(Html html) {
		// TODO Auto-generated method stub
		List<String> postUrls = html.links().regex(pattern).all();
		
		return StringUtils.removeDuplicate(postUrls);
	}
}
