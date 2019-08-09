package com.psi.controller.app.inventorymanagement.salebill;

import java.text.ParseException;
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
	public List<PageData> getSaleInfoByToday() throws Exception{
		PageData pd =new PageData();
		pd=this.getPageData();
		List<PageData> lpd=appSalebillService.listSaleInfoByToday(pd); 
		List<PageData> list= new ArrayList();
		if(lpd!=null && lpd.size()!=0){ //算出结果类似小米  销售量有20台，销售额有60000元，单数有5单（一单购买了5台），毛利10000元；
			lpd.get(0).put("NUM", 1);
			list.add(lpd.get(0));  
			for(int i=1;i<lpd.size();i++){
				PageData ipd=lpd.get(i);
				int flag=1;
				for(int j=0;j<list.size();j++){
					PageData jpd=list.get(j);
					if(ipd.get("GOODNAME").equals(jpd.get("GOODNAME"))){
						int PNUMBER=(Integer) jpd.get("PNUMBER")+(Integer) ipd.get("PNUMBER");
						String a1=jpd.get("AMOUNT").toString();
						String a2=ipd.get("AMOUNT").toString();
						double AMOUNT= Double.parseDouble(a1)+Double.parseDouble(a2);
						
						String m1=jpd.get("maoLi").toString();
						String m2=ipd.get("maoLi").toString();
						double maoLi= Double.parseDouble(m1)+Double.parseDouble(m2);
						
						jpd.put("PNUMBER", PNUMBER);
						jpd.put("AMOUNT", AMOUNT);
						jpd.put("maoLi", maoLi);
						if(jpd.get("NUM")==null){ //单数为零
							jpd.put("NUM", 1);
						}else{
							int NUM=(Integer) jpd.get("NUM");
							jpd.put("NUM", NUM+1);
						}
						list.set(j, jpd);
						flag=0;
					}
					break;
				}
				if(flag==1){
					ipd.put("NUM", 1);
					list.add(ipd);
				}
			}
		}
		return list;
	}
	
	//获取销售汇总（按月）
	@RequestMapping("/getSaleInfoByMouth")
	@ResponseBody
	public List<HashMap<String,Object>> getSaleInfoByMouth() throws ParseException{
		PageData pd = new PageData();
		pd=this.getPageData();
		
		String PK_SOBOOKS=pd.getString("PK_SOBOOKS");
		String yyyy=pd.getString("yyyy");//得到要查询的年份
		
		Calendar cal = Calendar.getInstance(); //定义日期实例
		String year = String.valueOf(cal.get(Calendar.YEAR)); //查询当前年份
		
		Date start = new SimpleDateFormat("yyyy-MM").parse(yyyy+"-1");//定义起始日期  年份的一月份开始
		Date end;//定义结束日期
		
		if(yyyy.equals(year)){//如果yyyy和当前年份相等，则查询今年
			end = new SimpleDateFormat("yyyy-MM").parse(year+"-"+(new Date().getMonth()+1));
		}else{
			end = new SimpleDateFormat("yyyy-MM").parse(yyyy+"-12");
		}
		
		cal.setTime(start);//设置日期起始时间
		
	    List<HashMap<String, Object>> list= new ArrayList<HashMap<String,Object>>();

		while(cal.getTime().getTime() <=end.getTime()){//判断是否到结束日期
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
	
			String str =sdf.format(cal.getTime());
	
			System.out.println(str);//输出日期结果
			pd.put("date", str);
			
		/*	PageData fpd= appInOderService.listMountAndNum(pd); 
			HashMap<String, Object> map= new HashMap<String,Object>();
			if(fpd.get("ALLAMOUNT")!=null){
				map.put("ALLAMOUNT", fpd.get("ALLAMOUNT"));
				map.put("NUM", fpd.get("NUM"));
			}
			pd.put("date", str);
			pd.put("PK_SOBOOKS", PK_SOBOOKS);
			
		/*	List<PageData> lpd=appInOderService.listInOderGoods(pd);//查询 商品在这个月入库的数量
			if(lpd!=null&&lpd.size()!=0){
				map.put("listNum", lpd);
				map.put("yearMouth", str);
				list.add(map);
				
			}*/
			cal.add(Calendar.MONTH, 1);//进行当前日期月份加1
			}
		return null;
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
