package net.trustie.webmagic.one.dao;

import net.trustie.webmagic.one.model.URL;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by gan on 2014/11/25.
 */
public interface URLDao {
	@Insert("insert ${listTableName} (`url`,`crawledTime`) values (#{urlPost},#{crawledTime})")
	public int add(@Param("urlPost") String urlPost,
			@Param("crawledTime") String crawledTime,
			@Param("listTableName") String listTableName);

	@Select("select url from ${listTableName}  where `timestamp` is  NULL limit #{startLine},#{endLine}")
	public List<String> getUrl(@Param("listTableName") String listTableName,
			@Param("startLine") int startLine, @Param("endLine") int endLine);

	@Update("update ${listTableName} set timestamp=#{timestamp} where url=#{url}")
	public int timestamp(@Param("timestamp") String timestamp,
			@Param("url") String url,
			@Param("listTableName") String listTableName);

	@Select("select url from target_url")
	public List<String> getAllUrl();

	@Select("select * from ${table} where id > #{startId} LIMIT #{batchSize}")
	public List<URL> getBatch(@Param("table") String tableName,
			@Param("startId") int startId, @Param("batchSize") int batchSize);

	@Insert("insert ${table} (`url`, `extractedTime`) values (#{url}, #{extractedTime})")
	public int addURL(@Param("table") String table,
			@Param("url") String url, @Param("extractedTime") String timestamp);
}
