package net.trustie.webmagic.one.dao;

import java.util.List;

import net.trustie.webmagic.one.model.AbstractHtml;
import net.trustie.webmagic.one.model.URL;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ErrorPageDao {

	@Insert("insert into "
			+ "${table}"
			+ " (`html`,`url`,`crawledTime`,`pageMd5`,`urlMd5`,`type`) values (#{html.html},#{html.url},#{html.crawledTime},#{html.pageMd5},#{html.urlMd5},#{html.type})")
	public int insertPage(@Param("table") String table, @Param("html")AbstractHtml html);
	
	@Select("select * from ${table} where id > #{startId} LIMIT #{batchSize}")
	public List<URL> getBatch(@Param("table") String tableName,
			@Param("startId") int startId, @Param("batchSize") int batchSize);
}
