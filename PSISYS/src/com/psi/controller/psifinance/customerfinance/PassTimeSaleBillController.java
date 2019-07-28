package com.psi.controller.psifinance.customerfinance;

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
import com.psi.service.basedata.warehouse.WarehouseManager;
import com.psi.service.inventorymanagement.salebill.SalebillManager;
import com.psi.util.AppUtil;
import com.psi.util.Const;
import com.psi.util.DateUtil;
import com.psi.util.Jurisdiction;
import com.psi.util.ObjectExcelView;
import com.psi.util.PageData;
import com.psi.util.Tools;

import net.sf.json.JSONArray;

/**
 * 说明：销售单管理
 */
@Controller
@RequestMapping(value="/ptsalebill")
public class PassTimeSaleBillController extends BaseController {
	String menuUrl = "ptsalebill/list.do"; //菜单地址(权限用)
	
	@Resource(name="salebillService")
	private SalebillManager salebillService;
	@Resource(name="goodsService")
	private GoodsManager goodsService;
	@Resource(name="customerService")
	private CustomerManager customerService;
	@Resource(name="warehouseService")
	private WarehouseManager warehouseService;
	
	/**
	 * 检查指定库存是否存在指定商品checkstock
	 * 通过商品编号和仓库ID来查，商品编号在JSP以GOOD_ID名字来传到后台
	 */
	@RequestMapping(value="/checkstock")
	@ResponseBody
	public Object checkStock() {
		logBefore(logger, Jurisdiction.getUsername()+"检查库存");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			Integer num = salebillService.getStock(pd);
			if(num == null || num <= 0) {
				map.put("msg","error");
			}else {
				map.put("msg","success");
				map.put("stockNum",num);
			}
		}catch(Exception e) {
			map.put("msg","error");
		}
		return AppUtil.returnObject(new PageData(), map);
	}
	/**
	 * 打开添加商品
	 * @throws Exception 
	 */
	@RequestMapping(value="/goaddgoods")
	public ModelAndView addgoods(Model model,String GOODTYPE_ID) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,String> map = new HashMap<String, String>();
			map.put("PK_SOBOOKS", pd.getString("PK_SOBOOKS"));
			map.put("PARENTS", "0");
			map.put("WAREHOUSE_ID", pd.getString("WAREHOUSE_ID"));
			JSONArray arr = JSONArray.fromObject(goodsService.salebillListAllDict(map));
			String json = arr.toString();
			json = json.replaceAll("GOODTYPE_ID", "id").replaceAll("PARENTS", "pId").replaceAll("TYPENAME", "name").replaceAll("subDict", "nodes").replaceAll("hasDict", "checked").replaceAll("treeurl", "url");
			model.addAttribute("zTreeNodes", json);
			mv.addObject("GOODTYPE_ID",GOODTYPE_ID);
			mv.addObject("pd", pd);	
			mv.setViewName("inventorymanagement/salebill/salebill_goods_ztree");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	/**
	 * 商品列表（添加商品用）
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/goodslist")
	public ModelAndView goodslist(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Goods");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		pd.put("GOODTYPE_ID", pd.get("id"));
		pd.put("USERNAME", "admin".equals(Jurisdiction.getUsername())?"":Jurisdiction.getUsername());
		page.setPd(pd);
		List<PageData>	varList = goodsService.list(page);	//列出Goods列表
		mv.setViewName("inventorymanagement/salebill/salebill_goods_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public String save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增salebill");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
//		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("SALEBILL_ID", this.get32UUID());		//主键
		pd.put("LDATE",DateUtil.getTime().toString());	//录入日期
		pd.put("BILLSTATUS", 1);
		pd.put("BILLTYPE", 1);
		pd.put("UNPAIDAMOUNT", pd.get("ALLAMOUNT"));
		pd.put("PAIDAMOUNT", 0);
		pd.put("THISPAY", 0);
		pd.put("ISSETTLEMENTED", 0);
		salebillService.save(pd);
//		mv.addObject("msg","success");
//		mv.setViewName("save_result");
//		mv.setViewName("inventorymanagement/salebill/salebill_list");
		return "redirect:/salebill/list.do";
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除salebill");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("LASTTIME", Tools.date2Str(new Date()));	//最后修改时间
		salebillService.delete(pd);
		out.write("success");
		out.close();
	}
	/**审批
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/shenpi")
	public void shenpi(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"审批salebill");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		//pd.put("LASTTIME", Tools.date2Str(new Date()));	//最后修改时间
		salebillService.updateshenpi(pd);
		out.write("success");
		out.close();
	}
	/**反审
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/fanshen")
	public void fanshen(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"反审salebill");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		//pd.put("LASTTIME", Tools.date2Str(new Date()));	//最后修改时间
		salebillService.updatefanshen(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public String edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改salebill");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		salebillService.edit(pd);
		return "redirect:/salebill/list.do";
	}
	/**结算单反审销售单功能
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/retrialInorder")
	@ResponseBody
	public Object retrialInorder() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改salebill");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		salebillService.retrialInorder(pd);
		map.put("msg", "success");
		return AppUtil.returnObject(new PageData(), map);
	}

	
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表salebill");
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
		List<PageData> customerList = customerService.listAll(pd);	//列出customer列表;
		List<PageData>	varList = salebillService.listForPassTimeSaleBill(page);	//列出salebill列表
		mv.addObject("customerList", customerList);
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限/PSISYS/WebRoot/WEB-INF/jsp/psifinance/customerfinance/ptsalebill_list.jsp
		mv.setViewName("psifinance/customerfinance/ptsalebill_list");
		return mv;
	}
	
	/**列表点击事件用到
	 * 为供应商结算单提供未结算且对应供应商的json数据
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/salebilllistForCustomer")
	@ResponseBody
	public Object salebilllistForCustomer() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表salebill");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData>	varList = salebillService.listForCustomer(pd);	//列出salebill列表
		map.put("varList", varList);
		map.put("QX", Jurisdiction.getHC()); //按钮权限
		return  AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * 新增时调用到
	 * 为供应商结算单提供未结算且对应供应商的json数据
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/salebilllistForCustomerAdd")
	@ResponseBody
	public Object salebilllistForCustomerAdd(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表salebill");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = salebillService.listForCustomerAdd(page);	//列出salebill列表
		map.put("varList", varList);
		map.put("QX", Jurisdiction.getHC()); //按钮权限
		return  AppUtil.returnObject(new PageData(), map);
	}
	
	/**列表(弹窗选择用)
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/windowsList")
	public ModelAndView windowsList(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表salebill");
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
		List<PageData>	varList = salebillService.list(page);	//列出Customer列表
		mv.setViewName("inventorymanagement/salebill/windows_salebill_list");
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
		List<PageData> customerList = customerService.listAll(pd);	//列出customer列表;
		List<PageData> warehouseList = warehouseService.listAll(pd);	//列出仓库列表;
		mv.setViewName("inventorymanagement/salebill/salebill_add");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		mv.addObject("customerList", customerList);
		mv.addObject("warehouseList", warehouseList);
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
		pd = salebillService.findById(pd);	//根据ID读取
		List<PageData> warehouseList = warehouseService.listAll(pd);	//列出仓库列表;
		List<PageData> customerList = customerService.listAll(pd);	//列出customer列表;
		mv.setViewName("inventorymanagement/salebill/salebill_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		mv.addObject("customerList", customerList);
		mv.addObject("warehouseList", warehouseList);
//		mv.addObject("varList", varList);
//		mv.addObject("varListL", varListL);
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
		pd = salebillService.findById(pd);	//根据ID读取
//		List<PageData>	varList = remarksService.listAll(pd);
//		List<PageData>	varListL = levelService.listAll(pd);
		mv.setViewName("inventorymanagement/salebill/salebill_view");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
//		mv.addObject("varList", varList);
//		mv.addObject("varListL", varListL);
		return mv;
	}
	
	 /**批量审批
		 * @param
		 * @throws Exception
		 */
		@RequestMapping(value="/shenpiAll")
		@ResponseBody
		public Object shenpiAll() throws Exception{
			logBefore(logger, Jurisdiction.getUsername()+"批量审批salebill");
			//if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
			PageData pd = new PageData();		
			Map<String,Object> map = new HashMap<String,Object>();
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
//				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				salebillService.updateshenpiAll(pd);
				pd.put("msg", "ok");
			}else{
				pd.put("msg", "no");
			}
			pdList.add(pd);
			map.put("list", pdList);
			return AppUtil.returnObject(pd, map);
		}
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除salebill");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			salebillService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出salebill到excel");
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
		List<PageData> varOList = salebillService.listAll(pd);
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
