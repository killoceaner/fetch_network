package net.trustie.webmagic.one.extractor;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

import net.trustie.webmagic.extrator.Extractor;
import net.trustie.webmagic.extrator.XPathExtractor;

import org.jsoup.Jsoup;

import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.utils.UrlUtils;
import us.codecraft.xsoup.Xsoup;

public class XPathExtractorTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String urlStr = "http://www.freecode.com/";
		String doc="";
		try {
			URL url = new URL(urlStr);
			 doc= Jsoup.parse(url, 100000).html();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Html html = new Html(UrlUtils.fixAllRelativeHrefs(doc,
				"http://www.freecode.com/"));
		Extractor extractor = new XPathExtractor("//div[@class='release']/h2[@class='release-head']/a/@href");
		Set<String> urlSet = extractor.extract(html);
		System.out.println(urlSet.size());
		for (String url : urlSet) {
			System.out.println(url);
		}
	}

}
