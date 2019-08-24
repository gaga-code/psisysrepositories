package com.psi.controller.basedata.payment;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.psi.controller.base.BaseController;
import com.psi.entity.Page;
import com.psi.entity.system.User;
import com.psi.service.basedata.payment.PaymentManager;
import com.psi.service.basedata.warehouse.WarehouseManager;
import com.psi.service.inventorymanagement.customersetbill.CustomersetbillManager;
import com.psi.service.inventorymanagement.inorder.InOrderManager;
import com.psi.service.inventorymanagement.salebill.SalebillManager;
import com.psi.service.inventorymanagement.suppsetbill.SuppsetbillManager;
import com.psi.util.AppUtil;
import com.psi.util.Const;
import com.psi.util.Jurisdiction;
import com.psi.util.ObjectExcelView;
import com.psi.util.PageData;

/**
 * 说明：支付方式
 */
@Controller
@RequestMapping(value="/payment")
public class PaymentController extends BaseController {
	
	String menuUrl = "payment/list.do"; //菜单地址(权限用)
	@Resource(name="paymentService")
	private PaymentManager paymentService;
	@Resource(name="suppsetbillService")
	private SuppsetbillManager suppsetbillService;	
	@Resource(name="inOrderService")
	private InOrderManager inOrderService;
	
	@Resource(name="salebillService")
	private SalebillManager salebillService;
	
	@Resource(name="customersetbillService")
	private CustomersetbillManager customersetbillService;
	
	@Resource(name="warehouseService")
	private WarehouseManager warehouseService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增支付方式");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("PAYMETHOD_ID", this.get32UUID());	//主键
		paymentService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除支付方式");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		paymentService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改支付方式");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		paymentService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表支付方式");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = paymentService.list(page);	//列出列表
		mv.setViewName("basedata/payment/payment_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	/**列表返回json数据
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/listjson" ,produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object listjson() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表支付方式");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData>	varList = paymentService.listAll(pd);	//列出列表
		map.put("varList", varList);
		map.put("QX", Jurisdiction.getHC());//按钮权限
		return AppUtil.returnObject(new PageData(), map);
	}
	
	
	
	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		pd.put("USER_ID", user.getUSER_ID());
		pd.put("PSI_NAME",user.getNAME());
		mv.setViewName("basedata/payment/payment_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = paymentService.findById(pd);	//根据ID读取
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		pd.put("USER_ID", user.getUSER_ID());
		pd.put("PSI_NAME",user.getNAME());
		mv.setViewName("basedata/payment/payment_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除支付方式");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			paymentService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出支付方式到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("支付方式");	//1
		titles.add("经手人");	//2
		titles.add("备注");	//3
		dataMap.put("titles", titles);
		List<PageData> varOList = paymentService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("PAYMETHODNAME"));	//1
			vpd.put("var2", varOList.get(i).getString("PSI_NAME"));	//2
			vpd.put("var3", varOList.get(i).getString("NOTE"));	//3
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	
	
	@RequestMapping("/listOrderSaleByPayment")
	public ModelAndView listOrderSaleByPayment(Page page) throws Exception{
		
		logBefore(logger, Jurisdiction.getUsername()+"查看进销支付");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		
		PageData pd = new PageData();
		pd = this.getPageData();
		ModelAndView mv = new ModelAndView();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if( keywords !=null && !keywords.equals("")){
			pd.put("keywords", keywords.trim());
		}
		String lastLoginStart = pd.getString("lastStart");	//开始时间
		String lastLoginEnd = pd.getString("lastEnd");		//结束时间
		int start=0;
		int end=0;
		if(lastLoginStart != null && !"".equals(lastLoginStart)){
			pd.put("lastStart", lastLoginStart+" 00:00:00");
			start=1;
		}
		if(lastLoginEnd != null && !"".equals(lastLoginEnd)){
			pd.put("lastEnd", lastLoginEnd+" 00:00:00");
			end=1;
		} 
		if(start==0&&end==0){//如果没有指定时间，默认是当天
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String str =sdf.format(new Date());
			pd.put("date", str);
		}
		
		
		
		String BILLTYPE=pd.getString("BILLTYPE");//单据类型
		if(pd.getString("BILLTYPE")==null){
			pd.put("BILLTYPE", 1);
		}
		String paymentId=pd.getString("PAYMETHOD_ID");//支付方式id
		if(paymentId!=null &&paymentId !=""){
			mv.addObject("paymentId",paymentId);
		}
		
		page.setPd(pd);
		List<PageData> list;
		List<PageData> list3 = null;
	    List list2 = new ArrayList();  //用来装INORDER_ID 
		if(BILLTYPE==null ||BILLTYPE.equals("1")){
			list3=settleResults("INORDER_IDS","INORDER_ID",pd,page);
		}else{
			list3=settleResults("SALEBILL_IDS","SALEBILL_ID",pd,page);
		}
		
		List<PageData>  paymentlist =paymentService.listAll(pd);
		mv.setViewName("basedata/payment/odersalebypayment");
		mv.addObject("paymentlist",paymentlist);
		mv.addObject("pd", pd);
		mv.addObject("varlist",list3);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	
	public List<PageData> settleResults(String str1,String str2,PageData pd,Page page) throws Exception{
		List<PageData> list;
		List<PageData> list1;
		List<PageData> list3 = null;
	    List list2 = new ArrayList();  //用来装INORDER_ID 
	    if(str2.equals("INORDER_ID")){
	    	list1=suppsetbillService.listByCondition(pd);//列出供应商列表
	    }else{
	    	list1=customersetbillService.listByCondition(pd);//列出客户列表
	    }
		HashMap<String,Object> hashmap = new HashMap();
		if(list1!=null&&list1.size()!=0){
			for (int i = 0; i < list1.size(); i++) {
				String[] strArr = list1.get(i).getString(str1).split(","); // 切割IDS
				for (int j = 0; j < strArr.length; j++) {
					String str = strArr[j];
					list2.add(str);
					String PAYMETHODNAME = list1.get(i).getString("PAYMETHODNAME");
	
					if (hashmap.containsKey(str)) {
						boolean bool = hashmap.get(str).toString().contains(PAYMETHODNAME);
						if (!bool) {
							hashmap.replace(str, hashmap.get(str) + "," + PAYMETHODNAME);
						}
					} else {
						hashmap.put(str, PAYMETHODNAME);
					}
				}
			}
			page.setList(list2); // 放进page，传到mapper进行条件判断
		    if(str2.equals("INORDER_ID")){ 
		    	list3 = inOrderService.listInOderByCondition(page);
		    }else{
		    	list3=salebillService.listSalebillByCondition(page);
		    }
			for (int i = 0; i < list3.size(); i++) {
				String PAYMETHODNAME = list3.get(i).getString("PAYMETHODNAME");
				if (PAYMETHODNAME == null) {
					String INORDER_ID = list3.get(i).getString(str2);
					String key = hashmap.get("'"+INORDER_ID+"'").toString();
					list3.get(i).put("PAYMETHODNAME", key);
				}
			}
		}
		return list3;
	}
}

