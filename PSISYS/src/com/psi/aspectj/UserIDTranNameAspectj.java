package com.psi.aspectj;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.psi.util.PageData;
import com.psi.util.TransIDtoObjectUtil;

@Component 
@Aspect 
public class UserIDTranNameAspectj {
	
	
	
	/** 
	 * 切面 
	 */ 
	private final String POINT_CUT_list = "execution(* com.psi.service.*.*.impl.*.list*(..))";
	@Pointcut(POINT_CUT_list) 
	private void pointcutlist(){}
	/** 
	 * 切面 
	 */ 
	private final String POINT_CUT_find = "execution(* com.psi.service.*.*.impl.*.find*(..))";
	@Pointcut(POINT_CUT_find) 
	private void pointcutfind(){}
	
	@AfterReturning(value = "pointcutlist() || pointcutfind()", returning = "returnObj") 
	public Object afterReturn(Object returnObj) { 
		if(returnObj == null) {
			return returnObj;
		}else if(returnObj instanceof java.util.List) {
			if(((java.util.List) returnObj).isEmpty()) {
				return returnObj;
			}else {
				for(Object o:(java.util.List)returnObj) {
					if(o.getClass() == PageData.class) {
						PageData pd = (PageData) o;
						if(pd.containsKey("USER_ID")) {
							PageData pdf = transUserIdToUserName(pd);
							System.out.println(pdf);
						}else {
							return returnObj;
						}
					}else {
						break;
					}
				}
			}
			
		}
		if(returnObj.getClass() == PageData.class) {
			PageData pd = (PageData) returnObj;
			if(pd.containsKey("USER_ID")) {
				return transUserIdToUserName(pd);
			}else {
				return returnObj;
			}
		}
		return returnObj;
	}
	
	@Autowired
	private TransIDtoObjectUtil transIDtoObjectUtil;
	/**
	 * 经手人主键 转 名称
	 * @param pd 
	 * @return
	 */
	private PageData transUserIdToUserName(PageData pd) {
		String NAME = transIDtoObjectUtil.transIDtoString("sys_user", "USER_ID", (String) pd.get("USER_ID"), "NAME");
		pd.put("PSI_NAME", NAME);
		return pd;
	}
	/**
	 * 批量操作  经手人主键 转 名称
	 * @param list
	 * @return
	 */
	private List<PageData> ptransUserIdUserName(List<PageData> list){
		List<PageData> newlist = new ArrayList<PageData>();
		for(int i = 0; i < list.size(); i++) {
			newlist.add(transUserIdToUserName(list.get(i)));
		}
		return newlist;
	}
	
}
