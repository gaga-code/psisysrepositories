
package com.psi.controller.inventorymanagement.inorder;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.psi.controller.base.BaseController;
import com.psi.entity.Page;
import com.psi.entity.system.User;
import com.psi.service.basedata.goods.GoodsManager;
import com.psi.service.basedata.supplier.SupplierManager;
import com.psi.service.basedata.warehouse.WarehouseManager;
import com.psi.service.inventorymanagement.inorder.InOrderManager;
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
 * 说明：进货单管理
 */
@Controller
@RequestMapping(value="/inorder")
public class InOrderController extends BaseController {
	String menuUrl = "inorder/list.do"; //菜单地址(权限用)
	
	@Resource(name="inOrderService")
	private InOrderManager inOrderService;
	@Resource(name="goodsService")
	private GoodsManager goodsService;
	@Resource(name="supplierService")
	private SupplierManager supplierService;
	@Resource(name="warehouseService")
	private WarehouseManager warehouseService;
	@Resource(name="salebillService")
	private SalebillManager salebillService;
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
			map.put("PARENTS", "-2");
			JSONArray arr = JSONArray.fromObject(goodsService.inOrderListAllDict(map));
			String json = arr.toString();
			json = json.replaceAll("GOODTYPE_ID", "id").replaceAll("PARENTS", "pId").replaceAll("TYPENAME", "name").replaceAll("subDict", "nodes").replaceAll("hasDict", "checked").replaceAll("treeurl", "url");
			model.addAttribute("zTreeNodes", json);
			mv.addObject("GOODTYPE_ID",GOODTYPE_ID);
			mv.addObject("pd", pd);	
			mv.setViewName("inventorymanagement/inorder/inorder_goods_ztree");
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
		mv.setViewName("inventorymanagement/inorder/inorder_goods_list");
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
		logBefore(logger, Jurisdiction.getUsername()+"新增inorder");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
//		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("INORDER_ID", this.get32UUID());		//主键
		pd.put("LDATE",DateUtil.getTime().toString());	//录入日期
		pd.put("BILLSTATUS", 1);
		pd.put("BILLTYPE", 1);
		pd.put("UNPAIDAMOUNT", pd.get("ALLAMOUNT"));
		pd.put("PAIDAMOUNT", 0);
		pd.put("THISPAY", 0);
		pd.put("ISSETTLEMENTED", 0);
		inOrderService.save(pd);
//		mv.addObject("msg","success");
//		mv.setViewName("save_result");
//		mv.setViewName("inventorymanagement/inorder/inorder_list");
		return "redirect:/inorder/list.do";
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除inorder");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("LASTTIME", Tools.date2Str(new Date()));	//最后修改时间
		inOrderService.delete(pd);
		out.write("success");
		out.close();
	}
	/**审批
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/shenpi")
	public void shenpi(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"审批inorder");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("BILLSTATUS", 2);
		//pd.put("LASTTIME", Tools.date2Str(new Date()));	//最后修改时间
		inOrderService.updateshenpi(pd);
		out.write("success");
		out.close();
	}
	/**反审
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/fanshen")
	public void fanshen(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"反审inorder");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("BILLSTATUS", 1);
		//pd.put("LASTTIME", Tools.date2Str(new Date()));	//最后修改时间
		inOrderService.updatefanshen(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public String edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改inorder");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		inOrderService.edit(pd);
		return "redirect:/inorder/list.do";
	}
	/**结算单反审进货单功能
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/retrialInorder")
	@ResponseBody
	public Object retrialInorder() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改inorder");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		inOrderService.retrialInorder(pd);
		map.put("msg", "success");
		return AppUtil.returnObject(new PageData(), map);
	}

	
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表进货单");
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
		
		pd.put("BILLTYPE", 1);
		page.setPd(pd);
		List<PageData> supplierList = supplierService.listAll(pd);	//列出supplier列表;
		List<PageData>	varList = inOrderService.list(page);	//列出inorder列表
		mv.addObject("supplierList", supplierList);
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		mv.setViewName("inventorymanagement/inorder/inorder_list");
		return mv;
	}
	
	
	
	/**详情列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/listdetail")
	public ModelAndView listdetail(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表进货详情单");
		String menuUrl = "inorder/listdetail.do"; //菜单地址(权限用)
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
			pd.put("lastStart", lastLoginStart);
		}
		if(lastLoginEnd != null && !"".equals(lastLoginEnd)){
			pd.put("lastEnd", lastLoginEnd);
		} 
		
		pd.put("BILLTYPE", 1);
		page.setPd(pd);
		
		List<PageData>	varList = inOrderService.listdetail(page);	//列出inorder列表
		
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		mv.setViewName("inventorymanagement/inorder/inorder_listdetail");
		return mv;
	}
	
	
	/**进货单列表点击事件用到
	 * 提供指定进货单的详情
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/inOrderlistBody")
	@ResponseBody
	public Object inOrderlistBody() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表inorder");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("BILLTYPE", 1);
		List<PageData>	varList = inOrderService.inOrderlistBody(pd);	//列出inorder列表
		map.put("varList", varList);
		map.put("QX", Jurisdiction.getHC()); //按钮权限
		return  AppUtil.returnObject(new PageData(), map);
	}
	/**列表点击事件用到
	 * 为供应商结算单提供未结算且对应供应商的json数据
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/inOrderlistForSupp")
	@ResponseBody
	public Object inOrderlistForSupp() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表inorder");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData>	varList = inOrderService.listForSuppset(pd);	//列出inorder列表
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
	@RequestMapping(value="/inOrderlistForSuppAdd")
	@ResponseBody
	public Object inOrderlistForSuppAdd(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表inorder");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = inOrderService.listForSuppAdd(page);	//列出inorder列表
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
		logBefore(logger, Jurisdiction.getUsername()+"列表inorder");
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
		pd.put("BILLTYPE", 1);
		page.setPd(pd);
		List<PageData>	varList = inOrderService.list(page);	//列出Customer列表
		mv.setViewName("inventorymanagement/inorder/windows_inorder_list");
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
		List<PageData> supplierList = supplierService.listAll(pd);	//列出supplier列表;
		List<PageData> warehouseList = warehouseService.listAll(pd);	//列出仓库列表;
		mv.setViewName("inventorymanagement/inorder/inorder_add");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		mv.addObject("supplierList", supplierList);
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
		pd = inOrderService.findById(pd);	//根据ID读取
		List<PageData> warehouseList = warehouseService.listAll(pd);	//列出仓库列表;
		List<PageData> supplierList = supplierService.listAll(pd);	//列出supplier列表;
		mv.setViewName("inventorymanagement/inorder/inorder_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		mv.addObject("supplierList", supplierList);
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
		pd = inOrderService.findById(pd);	//根据ID读取
//		List<PageData>	varList = remarksService.listAll(pd);
//		List<PageData>	varListL = levelService.listAll(pd);
		mv.setViewName("inventorymanagement/inorder/inorder_view");
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
			logBefore(logger, Jurisdiction.getUsername()+"批量审批inorder");
			//if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
			PageData pd = new PageData();		
			Map<String,Object> map = new HashMap<String,Object>();
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
//				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				inOrderService.updateshenpiAll(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除inorder");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			inOrderService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出inorder到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("单据编号");	//1
		titles.add("供应商票号");	//2
		titles.add("供应商");	//3
		titles.add("总金额");	//4
		titles.add("已付金额");	//5
		titles.add("未付金额");	//6
		titles.add("单据状态");	//7
		titles.add("结算状态	");	//8
		titles.add("日期");	//9
		titles.add("经手人");	//10
		dataMap.put("titles", titles);
		
		String lastLoginStart = pd.getString("lastStart");	//开始时间
		String lastLoginEnd = pd.getString("lastEnd");		//结束时间
		int flag1=1;
		if(lastLoginStart != null && !"".equals(lastLoginStart)){
			pd.put("lastStart", lastLoginStart+" 00:00:00");
			flag1=0;
		}
		int flag2=1;
		if(lastLoginEnd != null && !"".equals(lastLoginEnd)){
			pd.put("lastEnd", lastLoginEnd+" 23:59:59");
			flag2=0;
		} 
		if(flag1==0&&flag2==0){
			pd.put("flag", 1);
		}
		
		pd.put("BILLTYPE", 1);
		List<PageData> varOList = inOrderService.listAllToExcel(pd);
		
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("BILLCODE"));	    //1
			vpd.put("var2", varOList.get(i).getString("SUPPLIERNO"));	    //2
			vpd.put("var3", varOList.get(i).getString("SUPPLIERNAME"));	//3
			vpd.put("var4", varOList.get(i).get("ALLAMOUNT").toString());	    //4
			vpd.put("var5", varOList.get(i).get("PAIDAMOUNT").toString());	//5
			vpd.put("var6", varOList.get(i).get("UNPAIDAMOUNT").toString());	    //6
			String BILLSTATUS=varOList.get(i).getString("BILLSTATUS");
			if(BILLSTATUS.equals("1")){
				vpd.put("var7", "未审核");	    //7
			}else if(BILLSTATUS.equals("2")){
				vpd.put("var7", "已审核");	    //7
			}else{
				vpd.put("var7", "作废");	    //7
			}
			String ISSETTLEMENTED = varOList.get(i).getString("ISSETTLEMENTED");
			if(ISSETTLEMENTED.equals("0")){
				vpd.put("var8", "未结算" );	//8
			}else if(ISSETTLEMENTED.equals("1")){
				vpd.put("var8", "已结算");	//8
			}
			
			vpd.put("var9", varOList.get(i).getString("LDATE"));	    //9
			vpd.put("var10", varOList.get(i).getString("NAME"));	    //10
			varList.add(vpd);
		}
		dataMap.put("title","进货单");
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
	
	@RequestMapping(value="/listInOderSale")
	public ModelAndView listInOderSale(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"库存买卖记录");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		PageData pd = new PageData();
		pd= this.getPageData();
		

		ModelAndView mv= new ModelAndView();
		
		String keywords = pd.getString("keywords");				//关键词检索条件
		if( keywords !=null && !keywords.equals("")){
			pd.put("keywords", keywords.trim());
		}
		String lastLoginStart = pd.getString("lastStart");	//开始时间
		String lastLoginEnd = pd.getString("lastEnd");		//结束时间
		if(lastLoginStart != null && !"".equals(lastLoginStart)){
			pd.put("lastStart", lastLoginStart+" 00:00:00");
		}
		if(lastLoginEnd != null && !"".equals(lastLoginEnd)){
			pd.put("lastEnd", lastLoginEnd+" 23:59:59");
		} 
		pd.put("USERNAME", Jurisdiction.getUsername());
		
		
		String BILLTYPE=pd.getString("BILLTYPE");
		if(pd.getString("BILLTYPE")==null){
			pd.put("BILLTYPE", 1);
		}
		page.setPd(pd);
		String warehouseId=pd.getString("WAREHOUSE_ID");
		if(warehouseId!=null &&warehouseId !=""){
			mv.addObject("warehouseId",warehouseId);
		}
		List<PageData> list;
		if(BILLTYPE==null ||BILLTYPE.equals("1")){
			list=inOrderService.listInOderSale(page);//列出进货单列表
			
		}else{
			
			list=salebillService.listInOderSale(page);//列出销售单列表
		}
		
		List<PageData> warehouselist=warehouseService.listAll(pd);
		mv.setViewName("inventorymanagement/odersale/odersale_list");
		mv.addObject("pd", pd);
		mv.addObject("varlist",list);
		mv.addObject("warehouselist",warehouselist);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	 /**导出到excel
		 * @param
		 * @throws Exception
		 */
		@RequestMapping(value="/oderSaleExcel")
		public ModelAndView oderSaleExcel() throws Exception{
			logBefore(logger, Jurisdiction.getUsername()+"导出inorder到excel");
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
			ModelAndView mv = new ModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("单据编号");	//1
			titles.add("单据类型");	//2
			titles.add("商品名称	");	//3
			titles.add("单价");	//4
			titles.add("数量");	//5
			titles.add("金额");	//6
			titles.add("起点");	//9
			titles.add("终点");	//10
			titles.add("日期");	//9
			titles.add("经手人");	//10
			dataMap.put("titles", titles);
			
			String lastLoginStart = pd.getString("lastStart");	//开始时间
			String lastLoginEnd = pd.getString("lastEnd");		//结束时间
			int flag1=1;
			if(lastLoginStart != null && !"".equals(lastLoginStart)){
				pd.put("lastStart", lastLoginStart+" 00:00:00");
				flag1=0;
			}
			int flag2=1;
			if(lastLoginEnd != null && !"".equals(lastLoginEnd)){
				pd.put("lastEnd", lastLoginEnd+" 00:00:00");
				flag2=0;
			} 
			if(flag1==0&&flag2==0){
				pd.put("flag", 1);
			}
			
			pd.put("BILLTYPE", 1);
			List<PageData> varOList = inOrderService.listOrderSaleToExcel(pd);
			
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("BILLCODE"));	    //1
				String BILLTYPE= varOList.get(i).getString("BILLTYPE");
				if(BILLTYPE.equals("1")){
					vpd.put("var2", "进货单");	    //2
				}else if(BILLTYPE.equals("2")){
					vpd.put("var2", "销售单");	    //2
				}
				vpd.put("var3", varOList.get(i).getString("GOODNAME"));	//3
				vpd.put("var4", varOList.get(i).get("UNITPRICE_ID").toString());	    //4
				vpd.put("var5", varOList.get(i).get("PNUMBER").toString());	//5
				vpd.put("var6", String.valueOf(varOList.get(i).get("AMOUNT")));	    //6
				vpd.put("var7", varOList.get(i).getString("startplace"));	    //9
				vpd.put("var8", varOList.get(i).getString("endplace"));	    //10
				vpd.put("var9", varOList.get(i).getString("LASTTIME"));	    //9
				vpd.put("var10", varOList.get(i).getString("NAME"));	    //10
				varList.add(vpd);
			}
			dataMap.put("title","买卖仓库单");
			dataMap.put("varList", varList);
			ObjectExcelView erv = new ObjectExcelView();
			mv = new ModelAndView(erv,dataMap);
			return mv;
		}
		
		
		
		/**导出到excel
		 * @param
		 * @throws Exception
		 */
		@RequestMapping(value="/excelDetail")
		public ModelAndView excelDetail() throws Exception{
			String menuUrl = "inorder/listdetail.do"; //菜单地址(权限用)
			logBefore(logger, Jurisdiction.getUsername()+"导出进货详细单到excel");
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
			ModelAndView mv = new ModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("单据编号");	//1
			titles.add("单据类型");	//2
			titles.add("审核状态");	//3
			titles.add("商品编码");	//4
			titles.add("商品条码");	//5
			titles.add("商品名称");	//5
			titles.add("数量");	//6
			titles.add("单价");	//7
			titles.add("金额");	//8
			titles.add("结算状态");	//9
			titles.add("仓库");	//10
			titles.add("供应商");	//11
			titles.add("经手人");	//12
			titles.add("日期");	//13
			
			dataMap.put("titles", titles);
			
			String lastLoginStart = pd.getString("lastStart");	//开始时间
			String lastLoginEnd = pd.getString("lastEnd");		//结束时间
			int flag1=1;
			if(lastLoginStart != null && !"".equals(lastLoginStart)){
				pd.put("lastStart", lastLoginStart+" 00:00:00");
				flag1=0;
			}
			int flag2=1;
			if(lastLoginEnd != null && !"".equals(lastLoginEnd)){
				pd.put("lastEnd", lastLoginEnd+" 00:00:00");
				flag2=0;
			} 
			if(flag1==0&&flag2==0){
				pd.put("flag", 1);
			}
		
			List<PageData> varOList = inOrderService.excelDetail(pd);
			
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("BILLCODE"));	    //1
				
				vpd.put("var2", "进货单");	    //2
				String BILLSTATUS=varOList.get(i).getString("BILLSTATUS");
				if(BILLSTATUS.equals("1")){
					vpd.put("var3", "未审核");	//3
				}else if(BILLSTATUS.equals("2")){
					vpd.put("var3", "已审核");	//3
				}
				vpd.put("var4", varOList.get(i).getString("GOODCODE"));	//4
				vpd.put("var5", varOList.get(i).getString("BARCODE"));	//5
				vpd.put("var6", varOList.get(i).getString("GOODNAME"));	//6
				vpd.put("var7", varOList.get(i).get("PNUMBER").toString());	//7
				vpd.put("var8", varOList.get(i).get("UNITPRICE_ID").toString());	//8    
				vpd.put("var9",  String.valueOf(varOList.get(i).get("AMOUNT")));	//9
				
				String ISSETTLEMENTED= varOList.get(i).getString("ISSETTLEMENTED");
				if(ISSETTLEMENTED.equals("2")){
					vpd.put("var10","结算中");	    //10
				}else if(ISSETTLEMENTED.equals("1")){
					vpd.put("var10","已结算");	    //10
				}else if(ISSETTLEMENTED.equals("0")){
					vpd.put("var10","未结算");	    //10
				}
				vpd.put("var11", varOList.get(i).getString("WHNAME"));	    //11
				vpd.put("var12", varOList.get(i).getString("SUPPLIERNAME"));	    //12
				vpd.put("var13", varOList.get(i).getString("NAME"));	    //113
				vpd.put("var14", varOList.get(i).getString("CREATETIME"));	    //14
				varList.add(vpd);
			}
			dataMap.put("title","进货详细单");
			dataMap.put("varList", varList);
			ObjectExcelView erv = new ObjectExcelView();
			mv = new ModelAndView(erv,dataMap);
			return mv;
		}
		
		
}
