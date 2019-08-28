package com.psi.controller.app.basedata.customer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.psi.controller.base.BaseController;
import com.psi.service.app.basedata.customer.AppCustomerManager;
import com.psi.util.PageData;

@Controller
@RequestMapping("/appCustomer")
public class AppCustomerController extends BaseController{
		
	
	@Resource(name="appCustomerService")
	private AppCustomerManager appCustomerService;
	
	//.获取某个客户的信息  getCustomerDetails
	@RequestMapping("/getCustomerDetails")
	@ResponseBody
	public PageData  getCustomerDetails() throws Exception{
		PageData pd=new PageData();
		pd=this.getPageData();
		pd=appCustomerService.findCustomerByCode(pd);	
		return pd;
	}
	
	@RequestMapping("/getCustomerList")
	@ResponseBody
	public HashMap<String,Object> getCustomerList() throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("pageNum", Integer.valueOf(pd.getString("pageNum"))*10);
		int TOTALNUM=appCustomerService.listCutomerNum(pd);
		List<PageData> list=appCustomerService.listCutomer(pd);
		HashMap<String,Object> map= new HashMap();
		map.put("list", list);
		map.put("TOTALNUM", TOTALNUM);
		return map;
	}
	
}
