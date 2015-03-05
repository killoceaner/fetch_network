package net.trustie.webmagic.one.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Param;

public interface PointerDAO {

	@Select("select pointer from ${table} where table_name = #{table_name}")
	public int readPointer(@Param("table")String table,@Param("table_name") String tableName);


	@Insert("insert into ${table} (`table_name`, `pointer`) values (#{table_name}, #{pointer})")
	public int insertPointer(@Param("table") String table, @Param("table_name") String tableName,
			@Param("pointer") Integer pointer);

	@Update("update ${table} set pointer=#{pointer} where table_name=#{table_name}")
	public int updatePointer(@Param("table") String table, @Param("table_name") String tableName,
			@Param("pointer") int pointer);
	
//	@Select("select pointer from pointers  where table_name = #{table_name}")
//	public String readMode(@Param("table_name") String table);
//	
//	@Insert("insert into pointers (`table_name`, `pointer`) values (#{table_name}, #{pointer})")
//	public int insertMode(@Param("table_name") String table,
//			@Param("pointer") Integer pointer);
//
//	@Update("update pointers set pointer=#{pointer} where table_name=#{table_name}")
//	public int updateMode(@Param("table_name") String table,
//			@Param("pointer") int pointer);

}
