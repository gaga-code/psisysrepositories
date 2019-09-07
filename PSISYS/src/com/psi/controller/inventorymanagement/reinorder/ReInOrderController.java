package com.psi.controller.inventorymanagement.reinorder;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.psi.controller.base.BaseController;
import com.psi.entity.Page;
import com.psi.entity.system.User;
import com.psi.service.basedata.goods.GoodsManager;
import com.psi.service.basedata.supplier.SupplierManager;
import com.psi.service.basedata.warehouse.WarehouseManager;
import com.psi.service.inventorymanagement.inorder.InOrderManager;
import com.psi.service.inventorymanagement.reinorder.ReInOrderManager;
import com.psi.service.inventorymanagement.reinorder.impl.ReInOrderService;
import com.psi.service.inventorymanagement.salebill.SalebillManager;
import com.psi.util.AppUtil;
import com.psi.util.Const;
import com.psi.util.DateUtil;
import com.psi.util.Jurisdiction;
import com.psi.util.PageData;
import com.psi.util.Tools;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value="/reinorder")
public class ReInOrderController extends BaseController {
	String menuUrl = "reinorder/list.do"; //菜单地址(权限用)
	
	@Resource(name="reInOrderService")
	private ReInOrderManager reInOrderService;
	
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
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表reinorder");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		pd.put("BILLTYPE",8);
		page.setPd(pd);
		List<PageData> supplierList = supplierService.listAll(pd);	//列出supplier列表;
		List<PageData>	varList = inOrderService.list(page);	//列出inorder列表
		mv.addObject("supplierList", supplierList);
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		mv.setViewName("inventorymanagement/reinorder/reinorder_list");
		return mv;
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
			map.put("PARENTS", "-2");
			map.put("WAREHOUSE_ID", pd.getString("WAREHOUSE_ID"));
			JSONArray arr = JSONArray.fromObject(goodsService.salebillListAllDict(map));
			String json = arr.toString();
			json = json.replaceAll("GOODTYPE_ID", "id").replaceAll("PARENTS", "pId").replaceAll("TYPENAME", "name").replaceAll("subDict", "nodes").replaceAll("hasDict", "checked").replaceAll("treeurl", "url");
			model.addAttribute("zTreeNodes", json);
			mv.addObject("GOODTYPE_ID",GOODTYPE_ID);
			mv.addObject("pd", pd);	
			mv.setViewName("inventorymanagement/reinorder/inorder_goods_ztree");
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
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
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
		List<PageData>	result = new ArrayList<PageData>();	
		List<PageData>	varList = goodsService.list(page);	//列出Goods列表
		for (PageData pageData : varList) {
			if((Integer)pageData.get("STOCKNUM") != 0) {
				result.add(pageData);
			}
			
		}
		mv.setViewName("inventorymanagement/reinorder/inorder_goods_list");
		mv.addObject("varList", result);
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
		mv.setViewName("inventorymanagement/reinorder/reinorder_add");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		mv.addObject("supplierList", supplierList);
		mv.addObject("warehouseList", warehouseList);
//		mv.addObject("varListL", varListL);
		return mv;
	}	
	

	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public String save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增reinorder");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
//		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("INORDER_ID", this.get32UUID());		//主键
		pd.put("LDATE",DateUtil.getTime().toString());	//录入日期
		pd.put("BILLSTATUS", 1);
		pd.put("BILLTYPE", 8);
		pd.put("UNPAIDAMOUNT", pd.get("ALLAMOUNT"));
		pd.put("PAIDAMOUNT", 0);
		pd.put("THISPAY", 0);
		pd.put("ISSETTLEMENTED", 0);
		inOrderService.save(pd);
//		mv.addObject("msg","success");
//		mv.setViewName("save_result");
//		mv.setViewName("inventorymanagement/inorder/inorder_list");
		return "redirect:/reinorder/list.do";
	}
	
	/**退货单的反审 对应 进货单的审批  
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/fanshen")
	public void fanshen(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"反审reinorder");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("BILLSTATUS", 1);
		//pd.put("LASTTIME", Tools.date2Str(new Date()));	//最后修改时间
		inOrderService.updateshenpi(pd);
		out.write("success");
		out.close();
	}
	
	/**退货的审批 对应进货单的反审
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/shenpi")
	public void shenpi(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"审批reinorder");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("BILLSTATUS", 2);
		//pd.put("LASTTIME", Tools.date2Str(new Date()));	//最后修改时间
		reInOrderService.updatefanshen(pd);
		out.write("success");
		out.close();
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
		mv.setViewName("inventorymanagement/reinorder/reinorder_view");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
//		mv.addObject("varList", varList);
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
			mv.setViewName("inventorymanagement/reinorder/reinorder_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
			mv.addObject("supplierList", supplierList);
			mv.addObject("warehouseList", warehouseList);
//			mv.addObject("varList", varList);
//			mv.addObject("varListL", varListL);
			return mv;
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
			return "redirect:/reinorder/list.do";
		}
		
		/**删除
		 * @param out
		 * @throws Exception
		 */
		@RequestMapping(value="/delete")
		public void delete(PrintWriter out) throws Exception{
			logBefore(logger, Jurisdiction.getUsername()+"删除reinorder");
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
			PageData pd = new PageData();
			pd = this.getPageData();
			pd.put("LASTTIME", Tools.date2Str(new Date()));	//最后修改时间
			inOrderService.delete(pd);
			out.write("success");
			out.close();
		}
		
		

		 /**批量审批
			 * @param
			 * @throws Exception
			 */
			@RequestMapping(value="/shenpiAll")
			@ResponseBody
			public Object shenpiAll() throws Exception{
				logBefore(logger, Jurisdiction.getUsername()+"批量审批reinorder");
				//if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
				PageData pd = new PageData();		
				Map<String,Object> map = new HashMap<String,Object>();
				pd = this.getPageData();
				List<PageData> pdList = new ArrayList<PageData>();
				String DATA_IDS = pd.getString("DATA_IDS");
				if(null != DATA_IDS && !"".equals(DATA_IDS)){
//					String ArrayDATA_IDS[] = DATA_IDS.split(",");
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
			logBefore(logger, Jurisdiction.getUsername()+"批量删除reinorder");
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
		
		
}
