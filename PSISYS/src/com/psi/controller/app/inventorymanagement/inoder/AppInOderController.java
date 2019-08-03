package com.psi.controller.app.inventorymanagement.inoder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.psi.controller.base.BaseController;
import com.psi.service.app.inventorymanagement.inoder.AppInOderManager;
import com.psi.service.app.inventorymanagement.inoder.impl.AppInOderService;
import com.psi.util.PageData;

@Controller
@RequestMapping("/appInOder")
public class AppInOderController extends BaseController{
	
	@Resource(name="appInOderService")
	private AppInOderManager appInOderService;

	//获取今日入库信息
	@RequestMapping("/getInOderByToday")
	@ResponseBody
	public List<PageData> getInOderByToday() throws Exception{
		PageData pd = new PageData();
		pd=this.getPageData();
		List<PageData> list= appInOderService.listInOderByToday(pd);
		return list;
	}
	//获取入库汇总（按月份）
	@SuppressWarnings("deprecation")
	@RequestMapping("/getInOderByMouth")
	@ResponseBody
	public List<HashMap<String, Object>> getInOderByMouth() throws Exception{
		PageData pd = new PageData();
		pd=this.getPageData();
		
		String PK_SOBOOKS=pd.getString("PK_SOBOOKS");
		String yyyy=pd.getString("yyyy");//得到要查询的年份
		
		Calendar cal = Calendar.getInstance(); //定义日期实例
		String year = String.valueOf(cal.get(Calendar.YEAR)); //查询当前年份
		
		Date start = new SimpleDateFormat("yyyy-MM").parse(yyyy+"-1");//定义起始日期  年份的一月份开始
		Date end;//定义结束日期
		
		if(yyyy.equals(year)){//如果yyyy和当前年份相等，则查询今
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
		PageData fpd= appInOderService.listMountAndNum(pd); 
		HashMap<String, Object> map= new HashMap<String,Object>();
		if(fpd.get("ALLAMOUNT")!=null){
			map.put("ALLAMOUNT", fpd.get("ALLAMOUNT"));
			map.put("NUM", fpd.get("NUM"));
		}
		pd.put("date", str);
		pd.put("PK_SOBOOKS", PK_SOBOOKS);
		
		List<PageData> lpd=appInOderService.listInOderGoods(pd);//查询 商品在这个月入库的数量
		if(lpd!=null&&lpd.size()!=0){
			map.put("listNum", lpd);
			map.put("yearMouth", str);
			list.add(map);
			
		}
		cal.add(Calendar.MONTH, 1);//进行当前日期月份加1
		}
		return list;
	}
	
	@RequestMapping("/listInOrderByMouthDay")
	@ResponseBody
	public  List<HashMap<String, Object>> listInOrderByMouthDay() throws Exception{
		PageData pd = new PageData();
		pd=this.getPageData();

		String PK_SOBOOKS=pd.getString("PK_SOBOOKS");
		String yearmouth=pd.getString("yearmouth");//得到要查询的年月
		
		Calendar cal = Calendar.getInstance(); //定义日期实例
	
		String today =""+cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1); //得到今天的年月
		
		Date start = new SimpleDateFormat("yyyy-MM-dd").parse(yearmouth+"-1");//定义起始日期  月份的第一天开始
		Date end;//定义结束日期
		String[] strarray1=yearmouth.split("-"); 
		int y1 = Integer.parseInt(strarray1[0]);
		int m1 = Integer.parseInt(strarray1[1]);
		
		String[] strarray2=today.split("-"); 
		int y2 = Integer.parseInt(strarray2[0]);
		int m2 = Integer.parseInt(strarray2[1]);
		
		if(y1==y2&&m1==m2){//如果yearmouth和当前月份相等，则查询当今这个月
			end = new SimpleDateFormat("yyyy-MM-dd").parse(today+"-"+new Date().getDay());
		}else{
	
			Calendar c = Calendar.getInstance();
			c.set(y1, m2, 0); //输入类型为int类型
			int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
			
			end = new SimpleDateFormat("yyyy-MM-dd").parse(yearmouth+"-"+dayOfMonth);
		}
		cal.setTime(start);//设置日期起始时间
		
		List<HashMap<String, Object>> list= new ArrayList<HashMap<String,Object>>();
		
		while(cal.getTime().getTime() <=end.getTime()){//判断是否到结束日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String str =sdf.format(cal.getTime());

		System.out.println(str);//输出日期结果
		pd.put("date", str);
		PageData fpd= appInOderService.listMountAndNumByMD(pd);
		HashMap<String, Object> map= new HashMap<String,Object>();
		if(fpd!=null){
			map.put("ALLAMOUNT", fpd.get("ALLAMOUNT"));
			map.put("NUM", fpd.get("NUM"));
		}
		pd.put("date", str);
		pd.put("PK_SOBOOKS", PK_SOBOOKS);
		
		List<PageData> lpd=appInOderService.listInOderGoodsByMD(pd);//查询 商品在这个月入库的数量
		if(lpd!=null&&lpd.size()!=0){
			map.put("listNum", lpd);
			map.put("yearMouthDay", str);
			list.add(map);
		}
		cal.add(Calendar.DAY_OF_MONTH, 1);//进行当前日期月份加1
		}
		return list;
	}
	
	
	
}
