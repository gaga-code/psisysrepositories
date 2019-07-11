package com.psi.util;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 主键转换为对象、名称、编码的方法类
 * @author cx
 *
 */

public class TransIDtoObjectUtil {

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
	
}
