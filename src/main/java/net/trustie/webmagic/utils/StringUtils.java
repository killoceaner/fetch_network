package net.trustie.webmagic.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2014/7/22.
 */
public class StringUtils {

	public static List<Long> getDigit(String text) {
		List<Long> digitList = new ArrayList<Long>();
		Pattern p = Pattern.compile("(\\d+)");
		Matcher m = p.matcher(text);
		while (m.find()) {
			String find = m.group(1).toString();
			digitList.add(Long.valueOf(find));
		}
		return digitList;
	}

	public static String getNumber(String text) {
		Pattern p = Pattern.compile("(\\d+)");
		Matcher m = p.matcher(text);
		if (m.find())
			return m.group(1).toString();
		else
			return "0";
	}

	public static Set<String> removeDuplicate(List<String> strs) {
		Set<String> rt = new HashSet<String>();
		for (String str : strs) {
			rt.add(str);
		}
		return rt;
	}

}
