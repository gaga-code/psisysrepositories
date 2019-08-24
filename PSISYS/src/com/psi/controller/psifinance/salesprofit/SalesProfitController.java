package com.psi.controller.psifinance.salesprofit;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.psi.controller.base.BaseController;
import com.psi.entity.Page;
import com.psi.service.basedata.warehouse.WarehouseManager;
import com.psi.service.inventorymanagement.stock.StockManager;
import com.psi.service.psifinance.salesprofit.SalesProfitManager;
import com.psi.util.Const;
import com.psi.util.Jurisdiction;
import com.psi.util.PageData;

/**
 * 说明：库存管理
 */
@Controller
@RequestMapping(value="/salesprofit")
public class SalesProfitController extends BaseController {
	
	String menuUrl = "salesprofit/list.do"; //菜单地址(权限用)
	@Resource(name="salesprofitService")
	private SalesProfitManager salesprofitService;
	@Resource(name="warehouseService")
	private WarehouseManager warehouseService;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表salesprofit");
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
		/*String STOCKSTATE = (String) pd.get("STOCKSTATE");
		if(null != STOCKSTATE && !"".equals(STOCKSTATE)){
			if("0".equals(STOCKSTATE))
				pd.put("STOCKSTATE0", "0");
			if("1".equals(STOCKSTATE))
				pd.put("STOCKSTATE1", "1");
			if("2".equals(STOCKSTATE))
				pd.put("STOCKSTATE2", "2");
		}
		pd.put("OVERDATE", Const.OVERDATE);*/
		page.setPd(pd);
//		List<PageData> warehouseList = warehouseService.listAll(pd);	//列出仓库列表;
		List<PageData>	temp = salesprofitService.list(page);	//列出stock列表
		List<PageData> varList = new ArrayList<PageData>();
		for (PageData pageData : temp) {
			pageData.put("FZ",  pageData.get("UNITPROP") + " " + pageData.get("CUNITNAME") + " = 1 " + pageData.get("FZUNITNAME"));
			pageData.put("LRJE", ((Double)pageData.get("UNITPRICE_ID") - Double.valueOf(pageData.getString("DEF1"))) * (Integer)pageData.get("PNUMBER"));
			varList.add(pageData);
		}
		mv.setViewName("psifinance/salesprofit/salesprofit_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
//		mv.addObject("warehouseList", warehouseList);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	
}
