package com.psi.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import com.psi.service.system.BillCodePsi.BillCodeManager;

/**
 * 编码生成器
 * @author cx
 *
 */
public class ProductBillCodeUtil {

	@Resource(name="billCodeService")
	private BillCodeManager billCodeService;
	
	/**
	 * String[0] 返回最大MaxNo的编码 
	 * String[1] 对应主键
	 * 如果不存在该编号类型的数据，则String[1]返回null
	 * 后面根据是否为null来判断新增还是更新
	 * @param BillType  编码类型   可在Const常量类取
	 * @return
	 * @throws Exception
	 */
	public synchronized String[] getBillCode(String BillType) throws Exception {
		PageData pd = new PageData();
		pd.put("CodeType", BillType);
		PageData pe = billCodeService.findMaxNoByCodeType(pd);
		String max_code = "";
		String comment_code = "";
		if(pe!=null) {
			if(pe.containsKey("MaxNo")) {
				max_code = (String) pe.get("MaxNo");
			}
		}
		SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
		String date_pfix = format.format(new Date());
		if(max_code != null && max_code.contains(date_pfix)) {
			String uid_end = max_code.substring(max_code.length()-3, max_code.length());
			int endNum = Integer.parseInt(uid_end);
			int tmpNum = 1000+endNum+1;
			comment_code = date_pfix+ this.subStr(""+tmpNum, 1);
		}else {
			comment_code = date_pfix + "001";
		}
		if(pe == null) {
			return new String[] {BillType+comment_code,null};
		}else {
			return new String[] {comment_code,(String) pe.get("Code_ID")};
		}
	}
	public String subStr(String str,int start) {
		if(str == null || str.equals("") || str.length() == 0) {
			return "";
		}
		if(start < str.length()) {
			return str.substring(start);
		}else {
			return "";
		}
	}
}
