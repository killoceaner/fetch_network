package net.trustie.webmagic.extrator;

import java.util.List;
import java.util.Set;

import net.trustie.webmagic.utils.StringUtils;
import us.codecraft.webmagic.selector.Html;

public class XPathExtractor implements Extractor {
	private String path = "";

	/**
	 * @param path
	 */
	public XPathExtractor(String path) {
		this.path = path;
	}

	@Override
	public Set<String> extract(Html html) {
		// TODO Auto-generated method stub
		List<String> urlList = html.xpath(path).all();
		return StringUtils.removeDuplicate(urlList);
	}
}
