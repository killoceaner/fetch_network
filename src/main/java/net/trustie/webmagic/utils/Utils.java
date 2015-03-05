package net.trustie.webmagic.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.trustie.webmagic.one.Configure;

public class Utils {
	public static List<String[]> getProxies(Configure conf) {
		List<String[]> rt = new ArrayList<String[]>();
		String[] proxy = new String[2];
		proxy[0] = conf.getProxyIp();
		proxy[1] = conf.getProxyPort();
		rt.add(proxy);
		return rt;
	}

	public static String getNow() {
		SimpleDateFormat crawledTimeFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		// String timestamp = timestampFormat.format(date);
		return crawledTimeFormat.format(date);
	}
}
