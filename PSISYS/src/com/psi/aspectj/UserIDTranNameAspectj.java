package com.psi.aspectj;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.psi.util.PageData;
import com.psi.util.JdbcTempUtil;

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
		if (returnObj == null) {
			return returnObj;
		} else if (returnObj instanceof java.util.List) {
			if (((java.util.List) returnObj).isEmpty()) {
				return returnObj;
			} else {
				boolean isNoneP = false;
				for (Object o : (java.util.List) returnObj) {
					if(o!=null) {
						if (o.getClass() == PageData.class) {
							PageData pd = (PageData) o;
							if(pd.containsKey("GOODTYPE_ID")) {
								PageData pdf = transGoodTypeIdToCode(pd);
								isNoneP = true;
							}
							if(pd.containsKey("SUPPLIER_ID")) {
								PageData pdf = transSupplierIdToName(pd);
								isNoneP = true;
							}
							if(pd.containsKey("INOUTCOMETYPE_ID")) {
								PageData pdf = transIOCTypeToName(pd);
								isNoneP = true;
							}
							if (pd.containsKey("USER_ID")) {
								PageData pdf = transUserIdToUserName(pd);
								isNoneP = true;
							} 
							if (pd.containsKey("PAYMETHOD_ID")) {
								PageData pdf = transPayMethodIdToPayMethodName(pd);
								isNoneP = true;
							} 
							if (pd.containsKey("DISTRIBUTIONMODE") ) {
								if (pd.get("DISTRIBUTIONMODE").equals("1")) {
									pd.put("DISTRIBUTIONMODENAME", "现金");
								} else if (pd.get("DISTRIBUTIONMODE").equals("2")) {
									pd.put("DISTRIBUTIONMODENAME", "月结");
								}
								isNoneP = true;
							} 
							if (pd.containsKey("SEX")) {
								if (pd.get("SEX").equals("1")) {
									pd.put("SEXNAME", "男");
								} else if (pd.get("SEX").equals("2")) {
									pd.put("SEXNAME", "女");
								}
								isNoneP = true;
							} 
							if (pd.containsKey("EDUCATION")) {
								// 平凡、初中毕业、中专毕业、高中毕业、大学专科、大学本科
								if (pd.get("EDUCATION").equals("1")) {
									pd.put("EDUCATIONNAME", "平凡");
								} else if (pd.get("EDUCATION").equals("2")) {
									pd.put("EDUCATIONNAME", "初中毕业");
								} else if (pd.get("EDUCATION").equals("3")) {
									pd.put("EDUCATIONNAME", "中专毕业");
								} else if (pd.get("EDUCATION").equals("4")) {
									pd.put("EDUCATIONNAME", "高中毕业");
								} else if (pd.get("EDUCATION").equals("5")) {
									pd.put("EDUCATIONNAME", "大学专科");
								} else if (pd.get("EDUCATION").equals("6")) {
									pd.put("EDUCATIONNAME", "大学本科");
								} 
								isNoneP = true;
							} 
							if (pd.containsKey("BILLSTATUS")) {
								if (pd.get("BILLSTATUS").equals("0")) {
									pd.put("BILLSTATUSNAME", "自由");
								}else if (pd.get("BILLSTATUS").equals("1")) {
									pd.put("BILLSTATUSNAME", "未审核");
								} else if (pd.get("BILLSTATUS").equals("2")) {
									pd.put("BILLSTATUSNAME", "已审核");
								}
								isNoneP = true;
							}
							if (pd.containsKey("BILLTYPE")) {
								if (pd.get("BILLTYPE").equals("1")) {
									pd.put("BILLTYPENAME", "进货单");
								}else if (pd.get("BILLTYPE").equals("2")) {
									pd.put("BILLTYPENAME", "销售单");
								} else if (pd.get("BILLTYPE").equals("3")) {
									pd.put("BILLTYPENAME", "供应商结算单");
								}else if (pd.get("BILLTYPE").equals("4")) {
									pd.put("BILLTYPENAME", "客户结算单");
								}else if (pd.get("BILLTYPE").equals("5")) {
									pd.put("BILLTYPENAME", "供应商退货单");
								}else if (pd.get("BILLTYPE").equals("6")) {
									pd.put("BILLTYPENAME", "客户退货单");
								}
								isNoneP = true;
							}
							if(!isNoneP) {
								return returnObj;
							}
						}else {
							break;
						}
					}else {
						return returnObj;
					}
					
				}
			}
		} else if (returnObj.getClass() == PageData.class) {
			PageData pd = (PageData) returnObj;
			if(pd.containsKey("GOODTYPE_ID")) {
				PageData pdf = transGoodTypeIdToCode(pd);
			}
			if(pd.containsKey("SUPPLIER_ID")) {
				PageData pdf = transSupplierIdToName(pd);
			}
			if (pd.containsKey("PAYMETHOD_ID")) {
				PageData pdf = transPayMethodIdToPayMethodName(pd);
			}
			if (pd.containsKey("USER_ID")) {
				PageData pdf = transUserIdToUserName(pd);
			} 
			if (pd.containsKey("DISTRIBUTIONMODE") ) {
				if (pd.get("DISTRIBUTIONMODE").equals("1")) {
					pd.put("DISTRIBUTIONMODENAME", "现金");
				} else if (pd.get("DISTRIBUTIONMODE").equals("2")) {
					pd.put("DISTRIBUTIONMODENAME", "月结");
				}
			} 
			if (pd.containsKey("SEX")) {
				if (pd.get("SEX").equals("1")) {
					pd.put("SEXNAME", "男");
				} else if (pd.get("SEX").equals("2")) {
					pd.put("SEXNAME", "女");
				}
			} 
			if (pd.containsKey("EDUCATION")) {
				// 平凡、初中毕业、中专毕业、高中毕业、大学专科、大学本科
				if (pd.get("EDUCATION").equals("1")) {
					pd.put("EDUCATIONNAME", "平凡");
				} else if (pd.get("EDUCATION").equals("2")) {
					pd.put("EDUCATIONNAME", "初中毕业");
				} else if (pd.get("EDUCATION").equals("3")) {
					pd.put("EDUCATIONNAME", "中专毕业");
				} else if (pd.get("EDUCATION").equals("4")) {
					pd.put("EDUCATIONNAME", "高中毕业");
				} else if (pd.get("EDUCATION").equals("5")) {
					pd.put("EDUCATIONNAME", "大学专科");
				} else if (pd.get("EDUCATION").equals("6")) {
					pd.put("EDUCATIONNAME", "大学本科");
				} 
			} 
			if (pd.containsKey("BILLSTATUS")) {
				if (pd.get("BILLSTATUS").equals("0")) {
					pd.put("BILLSTATUSNAME", "自由");
				}else if (pd.get("BILLSTATUS").equals("1")) {
					pd.put("BILLSTATUSNAME", "未审核");
				} else if (pd.get("BILLSTATUS").equals("2")) {
					pd.put("BILLSTATUSNAME", "已审核");
				}
			}
			if (pd.containsKey("BILLTYPE")) {
				if (pd.get("BILLTYPE").equals("1")) {
					pd.put("BILLTYPENAME", "进货单");
				}else if (pd.get("BILLTYPE").equals("2")) {
					pd.put("BILLTYPENAME", "销售单");
				} else if (pd.get("BILLTYPE").equals("3")) {
					pd.put("BILLTYPENAME", "供应商结算单");
				}else if (pd.get("BILLTYPE").equals("4")) {
					pd.put("BILLTYPENAME", "客户结算单");
				}else if (pd.get("BILLTYPE").equals("5")) {
					pd.put("BILLTYPENAME", "供应商退货单");
				}else if (pd.get("BILLTYPE").equals("6")) {
					pd.put("BILLTYPENAME", "客户退货单");
				}
			}
		}
		return returnObj;
	}
	
	
	@Autowired
	private JdbcTempUtil jdbcTempUtil;
	/**
	 * 经手人主键 转 名称
	 * @param pd 
	 * @return
	 */
	private PageData transUserIdToUserName(PageData pd) {
		String NAME = jdbcTempUtil.transIDtoString("sys_user", "USER_ID", (String) pd.get("USER_ID"), "NAME");
		pd.put("PSI_NAME", NAME);
		return pd;
	}
	/**
	 * 支付方式主键 转 名称
	 * @param pd 
	 * @return
	 */
	private PageData transPayMethodIdToPayMethodName(PageData pd) {
		String NAME = jdbcTempUtil.transIDtoString("base_paymethod", "PAYMETHOD_ID", (String) pd.get("PAYMETHOD_ID"), "PAYMETHODNAME");
		pd.put("PAYMETHOD_NAME", NAME);
		return pd;
	}
	/**
	 * 收入支出方式 主键转名称
	 * @param pd
	 * @return
	 */
	private PageData transIOCTypeToName(PageData pd) {
		String TYPENAME = jdbcTempUtil.transIDtoString("base_inoutcometype", "INOUTCOMETYPE_ID", (String) pd.get("INOUTCOMETYPE_ID"), "TYPENAME");
		pd.put("INOUTCOMETYPE_NAME", TYPENAME);
		return pd;
	}
	/**
	 * 商品分类编号 主键转 编号
	 * @param pd
	 * @return
	 */
	private PageData transGoodTypeIdToCode(PageData pd) {
		String TYPECODE = jdbcTempUtil.transIDtoString("base_goodtype", "GOODTYPE_ID", (String) pd.get("GOODTYPE_ID"), "TYPECODE");
		pd.put("GOODTYPE_CODE", TYPECODE);
		return pd;
	}
	/**
	 * 供应商 主键转名称
	 * @param pd
	 * @return
	 */
	private PageData transSupplierIdToName(PageData pd) {
		String SUPPLIERNAME = jdbcTempUtil.transIDtoString("base_supplier", "SUPPLIER_ID", (String) pd.get("SUPPLIER_ID"), "SUPPLIERNAME");
		pd.put("SUPPLIER_NAME", SUPPLIERNAME);
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
