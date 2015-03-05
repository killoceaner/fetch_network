package net.trustie.webmagic.utils;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import net.trustie.webmagic.one.dao.CreateTableDAO;

@Component("tableHelper")
public class TableHelper {
	@Resource
	private CreateTableDAO dao;
	
	public boolean isTableExist(String tableName){
		try{
			dao.isTableExist(tableName);
			//System.out.println(i);
			return true;
		}catch(Exception e){
			//e.printStackTrace();
			return false;
		}
		
	}
	
	public void createTable(String table, String fields){
		dao.createTable(table, fields);
	}
}
