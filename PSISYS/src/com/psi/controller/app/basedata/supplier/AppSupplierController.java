package com.psi.controller.app.basedata.supplier;

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
	public List<PageData> getSupplierList() throws Exception{
		PageData pd = new PageData();
		pd=this.getPageData();
		List<PageData> list= appSupplierService.listSuppliers(pd);
		return list;
	}

}
