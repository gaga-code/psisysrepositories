package com.psi.controller.app.basedata.supplier;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.psi.controller.base.BaseController;
import com.psi.service.app.basedata.supplier.AppSupplierManager;
import com.psi.util.PageData;

@Controller
@RequestMapping("/appSupplier")
public class AppSupplierController extends BaseController {
	
	@Resource(name="appSupplierService")
	private AppSupplierManager appSupplierService;
	
	@RequestMapping("/getSupplierList")
	@ResponseBody
	public  HashMap<String,Object> getSupplierList() throws Exception{
		PageData pd = new PageData();
		pd=this.getPageData();
		pd.put("pageNum", Integer.valueOf(pd.getString("pageNum"))*10);
		int TOTALNUM = appSupplierService.listSuppliersNum(pd);
		List<PageData> list= appSupplierService.listSuppliers(pd);
		HashMap<String,Object> map= new HashMap();
		map.put("list", list);
		map.put("TOTALNUM", TOTALNUM);
		return map;
	}

}
