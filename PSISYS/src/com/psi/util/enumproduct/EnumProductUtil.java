package com.psi.util.enumproduct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 枚举的生产类
 * ====================================================================================
 *  DISTRIBUTIONMODE  经销方式：现金、月结   1 2
 *
 *	SEX   性别：男女 1 2
 *
 *	EDUCATION  学历：平凡、初中毕业、中专毕业、高中毕业、大学专科、大学本科
 *	          			1	   2       3         4        5         6   		
 *	
 *	BILLSTATUS  单据状态：未审核、已审核、结算未通过
 *		  				  1         2      3
 *	
 *	BILLTYPE  单据类型：进货单、销售单、供应商结算单、客户结算单、供应商退货单、客户退货单
 *	                     1       2         3            4           5            6
 *	 ====================================================================================                    
 * @author cx by 190719
 *
 */
public class EnumProductUtil {
	
	/**
	 * 返回经销方式的List
	 * 现金、月结   1 2
	 * @return
	 */
	public static List<HashMap> productDistributionModeList(){
		List<HashMap> varListL = new ArrayList<HashMap>();
		HashMap xianjin = new HashMap();
		xianjin.put("LEVEL_ID", 1);
		xianjin.put("TITLE","现金");
		HashMap yuejie = new HashMap();
		yuejie.put("LEVEL_ID", 2);
		yuejie.put("TITLE","月结");
		varListL.add(xianjin);
		varListL.add(yuejie);
		return varListL;
	}
	/**
	 * 返回性别的list
	 * 男女 1 2
	 * @return
	 */
	public static List<HashMap> productSexList(){
		//性别
		List<HashMap> varListSex = new ArrayList<HashMap>();
		HashMap man = new HashMap();
		man.put("LEVEL_ID", 1);
		man.put("TITLE","男");
		HashMap women = new HashMap();
		women.put("LEVEL_ID", 2);
		women.put("TITLE","女");
		varListSex.add(man);
		varListSex.add(women);
		return varListSex;
	}
	/**
	 * 返回学历的List
	 * 学历：平凡、初中毕业、中专毕业、高中毕业、大学专科、大学本科
     *     	  1	      2       3         4        5         6
	 * 
	 * @return
	 */
	public static List<HashMap> productEductionList(){
		//学历
		List<HashMap> varListLEduction = new ArrayList<HashMap>();
		HashMap one = new HashMap();
		one.put("LEVEL_ID", 1);
		one.put("TITLE","平凡");
		HashMap two = new HashMap();
		two.put("LEVEL_ID", 2);
		two.put("TITLE","初中毕业");
		HashMap three = new HashMap();
		three.put("LEVEL_ID", 3);
		three.put("TITLE","中专毕业");
		HashMap four = new HashMap();
		four.put("LEVEL_ID", 4);
		four.put("TITLE","初中毕业");
		HashMap five = new HashMap();
		five.put("LEVEL_ID", 5);
		five.put("TITLE","高中毕业");
		HashMap six = new HashMap();
		six.put("LEVEL_ID", 6);
		six.put("TITLE","大学专科");
		HashMap seven = new HashMap();
		seven.put("LEVEL_ID", 7);
		seven.put("TITLE","大学本科");
		varListLEduction.add(one);
		varListLEduction.add(two);
		varListLEduction.add(three);
		varListLEduction.add(four);
		varListLEduction.add(five);
		varListLEduction.add(six);
		varListLEduction.add(seven);
		return varListLEduction;
	}
	
	/**
	 * 返回单据状态的list
	 * 单据状态：未审核、已审核、结算未通过
	 * 			 1         2      3
	 * @return
	 */
	public static List<HashMap> productBillStatusList(){
		//单据状态
		List<HashMap> varList = new ArrayList<HashMap>();
		HashMap nopass = new HashMap();
		nopass.put("LEVEL_ID", 1);
		nopass.put("TITLE","未审核");
		HashMap pass = new HashMap();
		pass.put("LEVEL_ID", 2);
		pass.put("TITLE","已审核");
		HashMap hasproblem = new HashMap();
		hasproblem.put("LEVEL_ID", 3);
		hasproblem.put("TITLE","审核不通过");
		varList.add(nopass);
		varList.add(pass);
		varList.add(hasproblem);
		return varList;
	}
	
	/**
	 * 返回单据类型的list
	 * 单据类型：进货单、销售单、供应商结算单、客户结算单、供应商退货单、客户退货单
     *                1       2       3        4           5            6
	 * @return
	 */
	public static List<HashMap> productBillTypeList(){
		//单据类型
		List<HashMap> varList = new ArrayList<HashMap>();
		HashMap inorder = new HashMap();
		inorder.put("LEVEL_ID", 1);
		inorder.put("TITLE","进货单");
		HashMap salebill = new HashMap();
		salebill.put("LEVEL_ID", 2);
		salebill.put("TITLE","销售单");
		HashMap suppsetbill = new HashMap();
		suppsetbill.put("LEVEL_ID", 3);
		suppsetbill.put("TITLE","供应商结算单");
		HashMap customersetbill = new HashMap();
		customersetbill.put("LEVEL_ID", 4);
		customersetbill.put("TITLE","客户结算单");
		HashMap suppsetreturn = new HashMap();
		suppsetreturn.put("LEVEL_ID", 5);
		suppsetreturn.put("TITLE","供应商退货单");
		HashMap customerreturn = new HashMap();
		customerreturn.put("LEVEL_ID", 6);
		customerreturn.put("TITLE","客户退货单");
		varList.add(inorder);
		varList.add(salebill);
		varList.add(suppsetbill);
		varList.add(customersetbill);
		varList.add(suppsetreturn);
		varList.add(customerreturn);
		return varList;
	}
}
