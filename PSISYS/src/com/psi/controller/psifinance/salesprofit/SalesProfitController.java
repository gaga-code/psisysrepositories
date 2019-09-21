package com.psi.controller.psifinance.salesprofit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.psi.util.ObjectExcelView;
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
			pd.put("lastEnd", lastLoginEnd+" 23:59:59");
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
		
		double chenben=0;
		double saleAmount=0;
		double lirun=0;
		for (PageData pageData : temp) {
			pageData.put("FZ",  pageData.get("UNITPROP") + " " + pageData.get("CUNITNAME") + " = 1 " + pageData.get("FZUNITNAME"));
			double profit=((Double)pageData.get("UNITPRICE_ID") - Double.valueOf(pageData.getString("DEF1"))) * (Integer)pageData.get("PNUMBER");
			pageData.put("LRJE", profit);
			varList.add(pageData);
			chenben+=(Double)pageData.get("CPRICE")*(Integer)pageData.get("PNUMBER");
			saleAmount += (Double)pageData.get("UNITPRICE_ID")*(Integer)pageData.get("PNUMBER");
			lirun += profit;
		}
		
		mv.addObject("chenben",chenben);
		mv.addObject("saleAmount",saleAmount);
		mv.addObject("lirun",lirun);
		mv.setViewName("psifinance/salesprofit/salesprofit_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
//		mv.addObject("warehouseList", warehouseList);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
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
		titles.add("商品编号");	//1
		titles.add("商品名称");	//2
		titles.add("条码");	//3
		titles.add("型号");	//4
		titles.add("规格");	//5
		titles.add("所属柜组");	//6
		titles.add("商品类别");	//7
		titles.add("数量	");	//8
		titles.add("单价");	//9
		titles.add("辅助数量");	//10
		titles.add("成本金额");	//11
		titles.add("销售金额");	//12
		titles.add("利润金额");	//13
		titles.add("日期");	//14
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
		
		List<PageData> varOList = salesprofitService.listAllToExcel(pd);
		
		for (PageData pageData : varOList) {
			pageData.put("FZ",  pageData.get("UNITPROP") + " " + pageData.get("CUNITNAME") + " = 1 " + pageData.get("FZUNITNAME"));
			double profit=((Double)pageData.get("UNITPRICE_ID") - Double.valueOf(pageData.getString("DEF1"))) * (Integer)pageData.get("PNUMBER");
			pageData.put("LRJE", profit);
		}
		
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("GOODCODE"));	    //1
			vpd.put("var2", varOList.get(i).getString("GOODNAME"));	    //2
			vpd.put("var3", varOList.get(i).getString("BARCODE"));	//3
			vpd.put("var4", varOList.get(i).getString("GOODTYPECODE"));	    //4
			vpd.put("var5", varOList.get(i).getString("GOODSPECIF"));	    //5
			vpd.put("var6", varOList.get(i).getString("SUBGZ_ID"));	    //6
			vpd.put("var7", varOList.get(i).getString("TYPENAME"));	    //7
			vpd.put("var8", varOList.get(i).get("PNUMBER").toString());	    //8
			vpd.put("var9", varOList.get(i).getString("CUNITNAME"));	    //9
			vpd.put("var10", varOList.get(i).getString("FZ"));	    //10
			vpd.put("var11", String.valueOf(varOList.get(i).get("CPRICE")));	    //11
			vpd.put("var12", String.valueOf(varOList.get(i).get("UNITPRICE_ID")));	    //12
			vpd.put("var13", String.valueOf(varOList.get(i).get("LRJE")));	    //13
			vpd.put("var14", varOList.get(i).getString("LASTTIME"));	    //14
			varList.add(vpd);
		}
		dataMap.put("title","商品销售利润单");
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	
}
