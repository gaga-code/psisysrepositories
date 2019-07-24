package com.psi.util;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * jdbctemplete的应用：
 * 主键转NAME
 * 批量删除
 * @author cx
 *
 */

public class JdbcTempUtil {

	private  JdbcTemplate jdbcTemplate;
	

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	/**
	 * 根据主键获取表的某个字段值
	 * @param tableName  表名
	 * @param IDColumn   主键字段名
	 * @param IDValue    主键值
	 * @param NAMECOLUMN  想要的字段名
	 * @return
	 */
	public  String transIDtoString(String tableName,String IDColumn,String IDValue,String WANTCOLUMN) {
		String sql = "select "+WANTCOLUMN+" from "+tableName+" where "+IDColumn+"='"+IDValue+"' ";
		return jdbcTemplate.queryForObject(sql,String.class);
	}

	/**
	 * 批量删除
	 * @param DATA_IDS 主键
	 * @param PK_SOBOOKS 帐套主键
	 * @param tableName 表名
	 * @param IDColumn  主键字段名
	 */
	public void deleteAll(String DATA_IDS,String PK_SOBOOKS,String tableName,String IDColumn) {
		String sql = "update "+tableName+" set DR = 1 where "+IDColumn+" in ("+DATA_IDS+") and PK_SOBOOKS='"+PK_SOBOOKS+"' and IFNULL(DR,0)=0 ";
		jdbcTemplate.execute(sql);
	}

	public void update(String sql) {
		jdbcTemplate.execute(sql);
	}
	
}
