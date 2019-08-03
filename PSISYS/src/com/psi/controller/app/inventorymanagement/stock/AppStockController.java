package com.psi.controller.app.inventorymanagement.stock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.psi.controller.base.BaseController;
import com.psi.service.app.inventorymanagement.tock.AppStockManager;
import com.psi.util.PageData;

@Controller
@RequestMapping("appStock")
public class AppStockController extends BaseController{

	@Resource
	private AppStockManager appStockService;
	
	@RequestMapping("/getStockById")//根据商品编号查询每个库的库存
	public List<PageData> listStockById() throws Exception{
		PageData pd=new PageData();
		pd=this.getPageData();
		List<PageData> list=appStockService.listStockById(pd);
		return list;
	}

	// 获取今日商品库存预警信息
	@RequestMapping("/getGoosWarning")
	@ResponseBody
	public Map<String,Object> getGoosWarning() throws Exception{
		PageData pd = new PageData();
		pd=this.getPageData();
		List<PageData> list1=appStockService.listGoodsUpDate(pd);//查询积压过久的商品
		List<PageData> list2=appStockService.listGoodsDownNum(pd);//查询商品库存不足
		HashMap<String,Object> map=new HashMap();
		map.put("list1", list1);
		map.put("list2", list2);
		return map;
	}
	
}
