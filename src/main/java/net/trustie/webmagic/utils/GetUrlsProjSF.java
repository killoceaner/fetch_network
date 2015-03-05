package net.trustie.webmagic.utils;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2014/9/30.
 */
public class GetUrlsProjSF {
	public static List<String> get() throws SQLException,
			ClassNotFoundException, IllegalAccessException,
			InstantiationException {
		Logger log4j = Logger.getLogger(GetUrlsProjSF.class);
		List<String> l = new ArrayList<String>();
		String sql2 = "select url from proj_sourceforge_url";
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection conn = DriverManager.getConnection(
				"jdbc:mysql://192.168.80.104:3306/oss_tree_fix", "influx",
				"influx1234");
		// Statement stmt = conn.createStatement();
		PreparedStatement ps = conn.prepareStatement(sql2);
		ResultSet rs = ps.executeQuery();
		int i = 0;
		while (rs.next()) {
			String t = rs.getString("url");
			log4j.info("added!#" + t);
			i++;
			l.add(t);
			/*
			 * if (i==100) break;
			 */
		}
		log4j.info("total urls:" + i);
		log4j.error("well down");
		return l;
	}

}
