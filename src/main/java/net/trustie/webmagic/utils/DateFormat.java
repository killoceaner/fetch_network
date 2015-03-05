package net.trustie.webmagic.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Administrator on 2014/10/2.
 */
public class DateFormat {

	public static String formatFrom(String formatString, String format) {

		SimpleDateFormat bartDateFormat = new SimpleDateFormat(format,
				Locale.ENGLISH);
		SimpleDateFormat bartDateFormat2 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

		String date = "2000-01-01 00:00:00";
		try {
			date = bartDateFormat2.format(bartDateFormat.parse(formatString))
					.toString();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String formatFrom2(String strTime) {

		if (strTime.contains("昨天"))
			strTime = "1天前";
		String time = "2000-01-01 00:00:00";
		int vanishTime = Integer.parseInt(strTime.replaceAll("[^\\d]", ""));
		String[] ways = strTime.split("\\d+");

		String unit = ways[1];
		if (unit.contains("秒前")) {
			time = (new Timestamp(System.currentTimeMillis() - vanishTime
					* VanishTime.SECOND)).toString();
		} else if (unit.contains("分钟前")) {
			time = (new Timestamp(System.currentTimeMillis() - vanishTime
					* VanishTime.MINUTE)).toString();
		} else if (unit.contains("小时前")) {
			time = (new Timestamp(System.currentTimeMillis() - vanishTime
					* VanishTime.HOUR)).toString();
		} else if (unit.contains("天前")) {
			time = (new Timestamp(System.currentTimeMillis() - vanishTime
					* VanishTime.DAY)).toString();
		} else if (unit.contains("个月前")) {
			time = (new Timestamp(System.currentTimeMillis() - vanishTime
					* VanishTime.MONTH)).toString();
		} else if (unit.contains("年前")) {
			time = (new Timestamp(System.currentTimeMillis() - vanishTime
					* VanishTime.YEAR)).toString();
		} else {
			return null;
		}
		return time;
	}

}
