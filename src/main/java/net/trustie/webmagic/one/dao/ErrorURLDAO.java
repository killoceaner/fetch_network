package net.trustie.webmagic.one.dao;

import net.trustie.webmagic.one.model.URL;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface ErrorURLDAO {
	@Insert("insert ${table} (`id`,`url`) values (#{id},#{url})")
	public int insertURL(@Param("table") String table, URL url);
}
