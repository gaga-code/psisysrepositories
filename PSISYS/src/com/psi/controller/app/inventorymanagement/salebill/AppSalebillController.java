package com.psi.controller.app.inventorymanagement.salebill;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.psi.controller.base.BaseController;
import com.psi.service.app.inventorymanagement.salebill.AppSalebillManager;
import com.psi.util.PageData;

@Controller
@RequestMapping("/appSalebill")
public class AppSalebillController extends BaseController {
	
	@Resource(name="appSalebillService")
	private AppSalebillManager appSalebillService;
	
	
	public List<PageData> listDataAndNumAndPrice() throws Exception{
		PageData pd=new PageData();
		pd=this.getPageData();
		return appSalebillService.listDataAndNumAndPrice(pd);
		
	}
	
	//获取今日销售信息 
	@RequestMapping("/getSaleInfoByToday")
	@ResponseBody
	public PageData getSaleInfoByToday() throws Exception{
		PageData pd =new PageData();
		pd=this.getPageData();
		pd=appSalebillService.listSaleInfoByToday(pd);
		return pd;
	}
	
	//获取销售汇总（按月）
	@RequestMapping("/getSaleInfoByMouth")
	@ResponseBody
	public PageData getSaleInfoByMouth(){
		PageData pd = new PageData();
		pd=this.getPageData();
		
		return pd;
	}
	//获取某一个月每天的销售汇总（按天数）
	@RequestMapping("/getSaleInfoDayByMouth")
	@ResponseBody
	public List<PageData> getSaleInfoDayByMouth(){
		PageData pd = new PageData();
		pd=this.getPageData();
		List<PageData> list= appSalebillService.listSaleInfoDayByMouth(pd);
		return list;
	}
	
	//根据排序类型（销售额/销量/单数/毛利）sorttype=(销售额=1，销量=2，单数=3，毛利=4)
	//时间段（默认是当前月） yyyy-MM  2019-8
	//商品分类名称：`TYPENAME`（默认是全部）
	@RequestMapping("/getSaledGoodsList")
	@ResponseBody
	public List<PageData> getSaledGoodsBySTT() throws Exception{
		PageData pd= new PageData();
		pd=this.getPageData();
		if(pd.getString("sortType")!=null){  //默认是销售额  sorttype=1
			pd.put("sortType", 1); 
		}
		String startTime=pd.getString("startTime");
		String endTime=pd.getString("endTime");
		if(startTime==null&& endTime==null){
			Calendar cal = Calendar.getInstance(); //定义日期实例
			int mouth=cal.get(Calendar.MONTH);
			String yearMouth;
			if(mouth<10){
				 yearMouth = String.valueOf(cal.get(Calendar.YEAR))+"-0"+mouth;
			}else{
				 yearMouth = String.valueOf(cal.get(Calendar.YEAR))+"-"+mouth;
			}
			pd.put("yearMouth", yearMouth);
		}
		List<PageData> list=appSalebillService.listSaledGoodsBySTT(pd);
		return list;
	}
}
