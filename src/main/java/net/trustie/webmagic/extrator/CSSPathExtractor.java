package net.trustie.webmagic.extrator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

public class CSSPathExtractor implements Extractor{
	private String path = "";

	/**
	 * @param path
	 */
	public CSSPathExtractor(String path) {
		this.path = path;
	}

	@Override
	public Set<String> extract(Html html) {
		// TODO Auto-generated method stub
		Document doc = html.getDocument();
		Elements eles = doc.select(path);
		Set<String> urlSet = new HashSet<String>();
		for(Element e :eles){
			urlSet.add(e.attr("href"));
		}

		return urlSet;
	}
}
