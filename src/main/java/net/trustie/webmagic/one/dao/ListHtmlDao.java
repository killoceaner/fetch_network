package net.trustie.webmagic.one.dao;

import net.trustie.webmagic.one.model.DetailHtml;
import net.trustie.webmagic.one.model.ListHtml;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by ganyiang on 2014/12/19.
 */
public interface ListHtmlDao {
	@Insert("insert into "
			+ "${table}"
			+ " (`html`,`url`,`crawledTime`,`history`,`pageMd5`,`urlMd5`) values (#{listHtml.html},#{listHtml.url},#{listHtml.crawledTime},#{listHtml.history},#{listHtml.pageMd5},#{listHtml.urlMd5})")
	public int insertListHtml(@Param("listHtml")ListHtml listHtml, @Param("table") String table);

	@Select("select html from ${listTableName} where extracted is null limit #{startLine},#{endLine}")
	public List<String> getListHtml(@Param("listTableName") String tableName,
			@Param("startLine") int startLine, @Param("endLine") int endLine);

	@Insert("insert ${postUrlsTableName} (`url`,`extractedTime`) values (#{urlPost},#{extractedTime})")
	public int addPostUrls(@Param("postUrlsTableName") String tableName,
			@Param("urlPost") String urlPost,
			@Param("extractedTime") String extractedTime);

	@Update("update ${listTableName} set extracted=#{extracted} where pageMd5=#{pageMd5}")
	public int timestamp(@Param("listTableName") String listTableName,
			@Param("pageMd5") String pageMd5,
			@Param("extracted") String extracted);

	@Select("select * from ${table} where id > #{startId} LIMIT #{batchSize}")
	public List<ListHtml> getBatch(@Param("table") String tableName,
			@Param("startId") int startId, @Param("batchSize") int batchSize);
	
	@Update("create table ${table} ${fields}")
	public int createTable(@Param("table") String table, @Param("fields") String fields);

}
