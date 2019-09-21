package com.psi.controller.inventorymanagement.stock;

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
import com.psi.util.Const;
import com.psi.util.Jurisdiction;
import com.psi.util.ObjectExcelView;
import com.psi.util.PageData;

/**
 * 说明：库存管理
 */
@Controller
@RequestMapping(value="/stock")
public class StockController extends BaseController {
	
	String menuUrl = "stock/list.do"; //菜单地址(权限用)
	@Resource(name="stockService")
	private StockManager stockService;
	@Resource(name="warehouseService")
	private WarehouseManager warehouseService;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表stock");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String STOCKSTATE = (String) pd.get("STOCKSTATE");
		if(null != STOCKSTATE && !"".equals(STOCKSTATE)){
			if("0".equals(STOCKSTATE))
				pd.put("STOCKSTATE0", "0");
			if("1".equals(STOCKSTATE))
				pd.put("STOCKSTATE1", "1");
			if("2".equals(STOCKSTATE))
				pd.put("STOCKSTATE2", "2");
		}
		pd.put("OVERDATE", Const.OVERDATE);
		page.setPd(pd);
		List<PageData> warehouseList = warehouseService.listAll(pd);	//列出仓库列表;
		List<PageData>	temp = stockService.list(page);	//列出stock列表
		List<PageData> varList = new ArrayList<PageData>();
		for (PageData pageData : temp) {
			pageData.put("FZ",  pageData.get("UNITPROP") + " " + pageData.get("CUNITNAME") + " = 1 " + pageData.get("FZUNITNAME"));
			pageData.put("ZJE", (Integer)pageData.get("STOCK") * (Double)pageData.get("CPRICE"));
			varList.add(pageData);
		}
		mv.setViewName("inventorymanagement/stock/stock_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("warehouseList", warehouseList);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	
	 /**导出到excel
		 * @param
		 * @throws Exception
		 */
		@RequestMapping(value="/excel")
		public ModelAndView exportExcel() throws Exception{
			logBefore(logger, Jurisdiction.getUsername()+"导出销售单到excel");
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
			ModelAndView mv = new ModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("仓库");	//1
			titles.add("商品编号");	//2
			titles.add("商品名称");	//3
			titles.add("条码");	//4
			titles.add("型号");	//5
			titles.add("规格");	//6
			titles.add("商品类型");	//7
			titles.add("单位	");	//8
			titles.add("辅助数量");	//9
			titles.add("成本价");	//10
			titles.add("总金额");	//11
			titles.add("所属柜组");	//12
			titles.add("库存数量");	//13
			titles.add("库存上限");	//14
			titles.add("库存下线");	//15
			dataMap.put("titles", titles);
			
		
			List<PageData> varOList = stockService.listAllToExcel(pd);
			
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("WHNAME"));	    //1
				vpd.put("var2", varOList.get(i).getString("GOODCODE"));	    //2
				vpd.put("var3", varOList.get(i).getString("GOODNAME"));	//3
				vpd.put("var4", varOList.get(i).getString("BARCODE"));	    //4
				vpd.put("var5", varOList.get(i).getString("GOODTYPECODE"));	//5
				vpd.put("var6", varOList.get(i).getString("GOODSPECIF"));	    //6
				vpd.put("var7", varOList.get(i).getString("TYPENAME"));	    //7
				vpd.put("var8", varOList.get(i).getString("CUNITNAME"));	    //8
				String FZ= varOList.get(i).get("UNITPROP") + " " + varOList.get(i).get("CUNITNAME") + " = 1 " + varOList.get(i).get("FZUNITNAME");
				vpd.put("var9",FZ);	    //9
				
				vpd.put("var10", varOList.get(i).get("CPRICE").toString());	    //10
				
				double ZJE = (Integer)varOList.get(i).get("STOCK") * (Double)varOList.get(i).get("CPRICE");
				vpd.put("var11", String.valueOf(ZJE));	    //11
				
				vpd.put("var12", varOList.get(i).getString("SUBGZ_ID"));	    //12
				vpd.put("var13", varOList.get(i).get("STOCK").toString());	    //13
				vpd.put("var14", varOList.get(i).get("STOCKUPNUM").toString());	    //14
				vpd.put("var15", varOList.get(i).get("STOCKDOWNNUM").toString());	    //15
				varList.add(vpd);
			}
			dataMap.put("varList", varList);
			dataMap.put("title","库存单");
			ObjectExcelView erv = new ObjectExcelView();
			mv = new ModelAndView(erv,dataMap);
			return mv;
		}
	
}
