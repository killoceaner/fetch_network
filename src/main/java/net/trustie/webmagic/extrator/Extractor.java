package net.trustie.webmagic.extrator;

import java.util.Set;

import us.codecraft.webmagic.selector.Html;

public interface Extractor {
	public Set<String> extract(Html html);
}
