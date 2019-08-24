package com.psi.controller.inventorymanagement.resalebill;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
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
import com.psi.util.PageData;
import com.psi.util.Tools;

@Controller
@RequestMapping("/resalebill")
public class ReSalebillController extends BaseController{
	String menuUrl = "resalebill/list.do"; //菜单地址(权限用)

	
	@Resource(name="salebillService")
	private SalebillManager salebillService;
	@Resource(name="goodsService")
	private GoodsManager goodsService;
	@Resource(name="customerService")
	private CustomerManager customerService;
	@Resource(name="warehouseService")
	private WarehouseManager warehouseService;
	
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表resalebill");
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
		pd.put("BILLTYPE", 8);
		page.setPd(pd);
		List<PageData> customerList = customerService.listAll(pd);	//列出customer列表;
		List<PageData>	varList = salebillService.list(page);	//列出salebill列表
		mv.addObject("customerList", customerList);
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		mv.setViewName("inventorymanagement/resalebill/resalebill_list");
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
		mv.setViewName("inventorymanagement/resalebill/resalebill_add");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		mv.addObject("customerList", customerList);
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
		logBefore(logger, Jurisdiction.getUsername()+"新增resalebill");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
//		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("SALEBILL_ID", this.get32UUID());		//主键
		pd.put("LDATE",DateUtil.getTime().toString());	//录入日期
		pd.put("BILLSTATUS", 1);
		pd.put("BILLTYPE", 8);
		pd.put("UNPAIDAMOUNT", pd.get("ALLAMOUNT"));
		pd.put("PAIDAMOUNT", 0);
		pd.put("THISPAY", 0);
		pd.put("ISSETTLEMENTED", 0);
		pd = salebillService.save(pd);
		List<PageData> customerList = customerService.listAll(pd);	//列出customer列表;
		List<PageData> warehouseList = warehouseService.listAll(pd);	//列出仓库列表;
	/*	mv.setViewName("inventorymanagement/salebill/salebill_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", newpd);
		mv.addObject("customerList", customerList);
		mv.addObject("warehouseList", warehouseList);
		return mv;*/
		return "redirect:/resalebill/goEdit.do?SALEBILL_ID="+pd.getString("SALEBILL_ID");
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

			String CUSTOMER_ID = pd.getString("CUSTOMER_ID");
			PageData newpd = salebillService.customerunpaidandgreen(pd);//检查信誉额度
			Double unpaid = 0.0 ;
			if( newpd!=null &&newpd.get("unpaidallam")!=null){
				unpaid = (Double) newpd.get("unpaidallam");
			}
			Integer CREDITDEGREE=0;
			pd.put("unpaidallam", 0);
			pd.put("CREDITDEGREE", 100);
			if(newpd!=null){
				CREDITDEGREE = (Integer) newpd.get("CREDITDEGREE");
				pd.put("unpaidallam", unpaid);
				pd.put("CREDITDEGREE", CREDITDEGREE);
			}
			pd.put("ischaopi", "0");
			
			mv.setViewName("inventorymanagement/resalebill/resalebill_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
			mv.addObject("customerList", customerList);
			mv.addObject("warehouseList", warehouseList);
//			mv.addObject("varList", varList);
//			mv.addObject("varListL", varListL);
			return mv;
		}	
		
		/**审批
		 * @param out
		 * @throws Exception
		 */
		@RequestMapping(value="/shenpi")
		@ResponseBody
		public Object shenpi() throws Exception{
			logBefore(logger, Jurisdiction.getUsername()+"审批resalebill");
			//if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
			Map<String,Object> map = new HashMap<String,Object>();
			PageData pd = new PageData();
			pd = this.getPageData();
			//pd.put("LASTTIME", Tools.date2Str(new Date()));	//最后修改时间
			salebillService.updatefanshen(pd);
			List<PageData> goodslist = goodsService.checkGoodsStockDownNum(pd);
			if(goodslist.isEmpty()) {
				map.put("goodslist",null);
			}else {
				map.put("goodslist",goodslist);
			}
			return AppUtil.returnObject(pd, map);
		}
		
		/**退货单的反审 对应销售单的审批
		 * @param out
		 * @throws Exception
		 */
		@RequestMapping(value="/fanshen")
		public void fanshen(PrintWriter out) throws Exception{
			logBefore(logger, Jurisdiction.getUsername()+"反审resalebill");
			//if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
			PageData pd = new PageData();
			pd = this.getPageData();
			//pd.put("LASTTIME", Tools.date2Str(new Date()));	//最后修改时间
			salebillService.updateshenpi(pd);
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
			pd = salebillService.findById(pd);	//根据ID读取
//			List<PageData>	varList = remarksService.listAll(pd);
//			List<PageData>	varListL = levelService.listAll(pd);
			mv.setViewName("inventorymanagement/resalebill/resalebill_view");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
//			mv.addObject("varList", varList);
//			mv.addObject("varListL", varListL);
			return mv;
		}
		
		/**删除
		 * @param out
		 * @throws Exception
		 */
		@RequestMapping(value="/delete")
		public void delete(PrintWriter out) throws Exception{
			logBefore(logger, Jurisdiction.getUsername()+"删除resalebill");
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
			PageData pd = new PageData();
			pd = this.getPageData();
			pd.put("LASTTIME", Tools.date2Str(new Date()));	//最后修改时间
			salebillService.delete(pd);
			out.write("success");
			out.close();
		}
		
		/**修改
		 * @param
		 * @throws Exception
		 */
		@RequestMapping(value="/edit")
		public String edit() throws Exception{
			logBefore(logger, Jurisdiction.getUsername()+"修改resalebill");
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
			PageData pd = new PageData();
			pd = this.getPageData();
			salebillService.edit(pd);
			return "redirect:/resalebill/list.do";
		}
}
