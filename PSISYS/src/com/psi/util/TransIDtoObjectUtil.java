package com.psi.util;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 主键转换为对象、名称、编码的方法类
 * @author cx
 *
 */
public class TransIDtoObjectUtil {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public static Object transIDtoObject() {
		String sql = "";
		return null;
	}
	
}
