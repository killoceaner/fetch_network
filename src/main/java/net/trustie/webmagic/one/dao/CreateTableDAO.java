package net.trustie.webmagic.one.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface CreateTableDAO {
	//判断表是否存在
	@Select("select 1 from ${tableName}")
	public List<Integer> isTableExist(@Param("tableName") String tableName);
	
	@Update("create table ${table} ${fields}")
	public int createTable(@Param("table") String table, @Param("fields") String fields);
}
