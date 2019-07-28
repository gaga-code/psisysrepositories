package com.psi.controller.psifinance.expense;

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
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.psi.controller.base.BaseController;
import com.psi.entity.Page;
import com.psi.entity.system.User;
import com.psi.service.basedata.customer.CustomerManager;
import com.psi.service.basedata.goods.GoodsManager;
import com.psi.service.basedata.inoutcometype.InOutComeTypeManager;
import com.psi.service.basedata.payment.PaymentManager;
import com.psi.service.basedata.warehouse.WarehouseManager;
import com.psi.service.psifinance.expense.ExpenseManager;
import com.psi.util.AppUtil;
import com.psi.util.Const;
import com.psi.util.DateUtil;
import com.psi.util.Jurisdiction;
import com.psi.util.ObjectExcelView;
import com.psi.util.PageData;
import com.psi.util.Tools;

/**
 * 说明：费用开支单管理
 */
@Controller
@RequestMapping(value="/expense")
public class ExpenseController extends BaseController {
	String menuUrl = "expense/list.do"; //菜单地址(权限用)
	
	@Resource(name="expenseService")
	private ExpenseManager expenseService;
	@Resource(name="goodsService")
	private GoodsManager goodsService;
	@Resource(name="customerService")
	private CustomerManager customerService;
	@Resource(name="warehouseService")
	private WarehouseManager warehouseService;
	@Resource(name="paymentService")
	private PaymentManager paymentService;
	@Resource(name="inOutComeTypeService")
	private InOutComeTypeManager inOutComeTypeService;
	
	
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public String save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增expense");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
//		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("EXPENSE_ID", this.get32UUID());		//主键
		pd.put("LDATE",DateUtil.getTime().toString());	//录入日期
		pd.put("BILLTYPE", 7);
		pd.put("BILLSTATUS", 1);
		expenseService.save(pd);
//		mv.addObject("msg","success");
//		mv.setViewName("save_result");
//		mv.setViewName("psifinance/expense/expense_list");
		return "redirect:/expense/list.do";
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除expense");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		expenseService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public String edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改expense");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		expenseService.edit(pd);
		return "redirect:/expense/list.do";
	}

	
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表expense");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String lastLoginStart = pd.getString("lastStart");	//开始时间
		String lastLoginEnd = pd.getString("lastEnd");		//结束时间
		if(lastLoginStart != null && !"".equals(lastLoginStart)){
			pd.put("lastStart", lastLoginStart+" 00:00:00");
		}
		if(lastLoginEnd != null && !"".equals(lastLoginEnd)){
			pd.put("lastEnd", lastLoginEnd+" 00:00:00");
		} 
		page.setPd(pd);
		List<PageData>	varList = expenseService.list(page);	//列出expense列表
	
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		mv.setViewName("psifinance/expense/expense_list");
		return mv;
	}
	
	
	/**列表(弹窗选择用)
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/windowsList")
	public ModelAndView windowsList(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表expense");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String lastLoginStart = pd.getString("lastStart");	//开始时间
		String lastLoginEnd = pd.getString("lastEnd");		//结束时间
		if(lastLoginStart != null && !"".equals(lastLoginStart)){
			pd.put("lastStart", lastLoginStart+" 00:00:00");
		}
		if(lastLoginEnd != null && !"".equals(lastLoginEnd)){
			pd.put("lastEnd", lastLoginEnd+" 00:00:00");
		} 
		pd.put("USERNAME", Jurisdiction.getUsername());
		page.setPd(pd);
		List<PageData>	varList = expenseService.list(page);	//列出Customer列表
		mv.setViewName("psifinance/expense/windows_expense_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
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
		pd.put("PSI_NAME", user.getNAME());
		List<PageData> paymethodList = paymentService.listAll(pd);	//支付方式列表;
		List<PageData> ioctypeList = inOutComeTypeService.listAll(pd);//收入支出类型
		mv.addObject("ioctypeList", ioctypeList);
		mv.addObject("paymethodList", paymethodList);
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		mv.setViewName("psifinance/expense/expense_edit");
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
//		mv.addObject("varListL", varListL);
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
		pd = expenseService.findById(pd);	//根据ID读取
		List<PageData> paymethodList = paymentService.listAll(pd);	//支付方式列表;
		List<PageData> ioctypeList = inOutComeTypeService.listAll(pd);//收入支出类型
		mv.addObject("ioctypeList", ioctypeList);
		mv.addObject("paymethodList", paymethodList);
		mv.setViewName("psifinance/expense/expense_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}	
	
	 /**查看页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goView")
	public ModelAndView goView()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = expenseService.findById(pd);	//根据ID读取
		mv.setViewName("psifinance/expense/expense_view");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除expense");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			expenseService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出expense到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("姓名");	//1
		titles.add("年龄");	//2
		titles.add("手机");	//3
		titles.add("地址");	//4
		titles.add("QQ");	//5
		titles.add("微信");	//6
		titles.add("建档时间");	//7
		titles.add("消费金额");	//8
		titles.add("级别");	//9
		titles.add("备注1");	//10
		titles.add("备注2");	//11
		dataMap.put("titles", titles);
		pd.put("USERNAME", Jurisdiction.getUsername());
		List<PageData> varOList = expenseService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("NAME"));	    //1
			vpd.put("var2", varOList.get(i).getString("AGE"));	    //2
			vpd.put("var3", varOList.get(i).get("PHONE").toString());	//3
			vpd.put("var4", varOList.get(i).getString("ADDRESS"));	    //4
			vpd.put("var5", varOList.get(i).get("QQ").toString());	//5
			vpd.put("var6", varOList.get(i).getString("WEIXIN"));	    //6
			vpd.put("var7", varOList.get(i).getString("CTIME"));	    //7
			vpd.put("var8", varOList.get(i).get("MONEY").toString());	//8
			vpd.put("var9", varOList.get(i).getString("LEVEL"));	    //9
			vpd.put("var10", varOList.get(i).getString("REMARKS1"));	    //10
			vpd.put("var11", varOList.get(i).getString("REMARKS2"));	    //11
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
}
