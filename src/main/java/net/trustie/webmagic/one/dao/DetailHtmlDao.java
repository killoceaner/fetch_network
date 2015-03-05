package net.trustie.webmagic.one.dao;

import java.util.List;

import net.trustie.webmagic.one.model.DetailHtml;
import net.trustie.webmagic.one.model.ListHtml;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by Administrator on 2014/11/16.
 */
public interface DetailHtmlDao {
	
	@Insert("insert into "
			+ "${tableName}"
			+ " (`html`,`url`,`crawledTime`,`history`,`pageMd5`,`urlMd5`) values (#{html},#{url},#{crawledTime},#{history},#{pageMd5},#{urlMd5})")
	public int add(DetailHtml detailHtml);

	@Insert("insert into "
			+ "${table2Name}"
			+ " (`html`,`url`,`crawledTime`,`history`,`pageMd5`,`urlMd5`) values (#{html},#{url},#{crawledTime},#{history},#{pageMd5},#{urlMd5})")
	public int addListHtml(DetailHtml detailHtml);

	@Delete("delete from ${tableName} where url=#{url}")
	public int delete404(@Param("url") String url,
			@Param("tableName") String tableName);

	@Select("select * from ${table} where id > #{startId} AND id <= #{endId}")
	public List<DetailHtml> getBatch(@Param("table") String tableName,
			@Param("startId") int startId, @Param("endId") int endId);

	@Insert("insert into "
			+ "${table}"
			+ " (`html`,`url`,`crawledTime`,`history`,`pageMd5`,`urlMd5`) values (#{detailHtml.html},#{detailHtml.url},#{detailHtml.crawledTime},#{detailHtml.history},#{detailHtml.pageMd5},#{detailHtml.urlMd5})")
	public int insertDetailHtml(@Param("detailHtml")DetailHtml detailHtml, @Param("table") String table);
}
