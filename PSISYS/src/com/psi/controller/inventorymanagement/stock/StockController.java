package com.psi.controller.inventorymanagement.stock;

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
import com.psi.service.basedata.supplier.SupplierManager;
import com.psi.service.erp.spbrand.SpbrandManager;
import com.psi.service.erp.sptype.SptypeManager;
import com.psi.service.erp.spunit.SpunitManager;
import com.psi.service.information.pictures.PicturesManager;
import com.psi.service.inventorymanagement.stock.StockManager;
import com.psi.util.AppUtil;
import com.psi.util.BarcodeUtil;
import com.psi.util.Const;
import com.psi.util.Jurisdiction;
import com.psi.util.PageData;
import com.psi.util.PathUtil;
import com.psi.util.TwoDimensionCode;

import net.sf.json.JSONArray;

/**
 * 说明：库存管理
 */
@Controller
@RequestMapping(value="/stock")
public class StockController extends BaseController {
	
	String menuUrl = "stock/list.do"; //菜单地址(权限用)
	@Resource(name="stockService")
	private StockManager stockService;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表stock");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
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
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	
}
