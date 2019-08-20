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
import com.psi.util.DateUtil;
import com.psi.util.Jurisdiction;
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
		List<PageData> lpd= appInOderService.listInOderByToday(pd);//得到进货单和退货单
		List<PageData> list=new ArrayList();//进货单
		List<PageData> ipd=new ArrayList();//退货单
		if(lpd!=null&&lpd.size()!=0){
			for(int i=0;i<lpd.size();i++){
				if(lpd.get(i).get("BILLTYPE").equals("1")){//如果为进货单
					int flag=0;
					for(int j=0;j<list.size();j++){
						if(list.get(j).get("GOODCODE").equals(lpd.get(i).get("GOODCODE"))){//商品编号相同
							int PNUMBER=(Integer)list.get(i).get("PNUMBER")+(Integer)lpd.get(i).get("PNUMBER");
							double ALLAMOUNT=(Double)list.get(i).get("ALLAMOUNT")+(Double)lpd.get(i).get("ALLAMOUNT");
							list.get(i).put("PNUMBER", PNUMBER);
							list.get(i).put("ALLAMOUNT", ALLAMOUNT);
							flag=1;
						}
					}
					if(flag==0){
						list.add(lpd.get(i));
					}
					
				}else{
					ipd.add(lpd.get(i));
				}
			}
			for(int i=0;i<list.size();i++){
				for(int j=0;j<ipd.size();j++){
					if(list.get(i).get("GOODCODE").equals(ipd.get(j).get("GOODCODE"))){
						int PNUMBER=(Integer)list.get(i).get("PNUMBER")-(Integer)ipd.get(i).get("PNUMBER");
						double ALLAMOUNT=(Double)list.get(i).get("ALLAMOUNT")-(Double)ipd.get(i).get("ALLAMOUNT");
						list.get(i).put("PNUMBER", PNUMBER);
						list.get(i).put("ALLAMOUNT", ALLAMOUNT);
						ipd.remove(j);
					}
				}
			}
		}
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
			
			HashMap<String, Object> map= new HashMap<String,Object>();
			
			
			pd.put("BILLTYPE", 1);
			PageData fpd= appInOderService.listMountAndNum(pd); 
			pd.put("BILLTYPE", 8);
			PageData hpd= appInOderService.listMountAndNum(pd); 
			
			
			
			double ALLAMOUNT = 0;
			if(fpd!=null&&fpd.size()!=0&&fpd.get("ALLAMOUNT")!=null){
				if((Long)hpd.get("NUM")!=0){
					 ALLAMOUNT= (Double)fpd.get("ALLAMOUNT")-(Double)hpd.get("ALLAMOUNT");
				
				}else{
					 ALLAMOUNT= (Double)fpd.get("ALLAMOUNT");
				}
			}
			map.put("ALLAMOUNT",ALLAMOUNT);
			map.put("NUM",fpd.get("NUM"));
			
			pd.put("date", str);
			pd.put("PK_SOBOOKS", PK_SOBOOKS);
			
			pd.put("BILLTYPE", 1);
			List<PageData> lpd=appInOderService.listInOderGoods(pd);//查询 商品在这个月入库的数量 进货单
			pd.put("BILLTYPE", 8);
			List<PageData> ipd=appInOderService.listInOderGoods(pd);//查询 商品在这个月入库的数量 退货单
			map.put("NUM",lpd.size());
			
			if(lpd!=null&&lpd.size()!=0){
				if(ipd!=null && ipd.size()!=0){
					for(int i=0;i<lpd.size();i++){
						for(int j=0;j<ipd.size();j++){
							if(ipd.get(j).get("GOODCODE_ID").equals(lpd.get(i).get("GOODCODE_ID"))){
								
								int PNUMBER = Integer.parseInt(lpd.get(i).get("PNUMBER").toString())- Integer.parseInt(ipd.get(j).get("PNUMBER").toString());
								lpd.get(i).put("PNUMBER", PNUMBER);
								break;
							}
						}
					}
				}
				map.put("listNum", lpd);
				map.put("yearMouth", str);
				list.add(map);
				
			}
			cal.add(Calendar.MONTH, 1);//进行当前日期月份加1
		}
		return list;
	}
	//获取入库汇总（按月份的每一天）
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
			Calendar c = Calendar.getInstance();
			end = new SimpleDateFormat("yyyy-MM-dd").parse(today+"-"+c.get(Calendar.DAY_OF_MONTH));
		}else{
	
			Calendar c = Calendar.getInstance();
			c.set(y1, m1, 0); //输入类型为int类型
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
			HashMap<String, Object> map= new HashMap<String,Object>();
			
			pd.put("BILLTYPE", 1);
			PageData fpd= appInOderService.listMountAndNumByMD(pd);//进货单
			
			pd.put("BILLTYPE", 8);
			PageData hpd= appInOderService.listMountAndNumByMD(pd);//退货单
			
			double ALLAMOUNT = 0;
			if(fpd!=null&&fpd.size()!=0&&fpd.get("ALLAMOUNT")!=null){
				if((Long)hpd.get("NUM")!=0){
					 ALLAMOUNT= (Double)fpd.get("ALLAMOUNT")-(Double)hpd.get("ALLAMOUNT");
				
				}else{
					 ALLAMOUNT= (Double)fpd.get("ALLAMOUNT");
				}
			}
			map.put("ALLAMOUNT",ALLAMOUNT);
			map.put("NUM",fpd.get("NUM"));
			
			pd.put("date", str);
			pd.put("PK_SOBOOKS", PK_SOBOOKS);
			
			pd.put("BILLTYPE", 1);
			List<PageData> lpd=appInOderService.listInOderGoodsByMD(pd);//查询 商品在这个月入库的数量 进货单
			
			pd.put("BILLTYPE", 8);
			List<PageData> ipd=appInOderService.listInOderGoodsByMD(pd);//查询 商品在这个月入库的数量  退货单
			
			map.put("NUM",lpd.size());
			if(lpd!=null&&lpd.size()!=0){
				if(ipd!=null && ipd.size()!=0){
					for(int i=0;i<lpd.size();i++){
						for(int j=0;j<ipd.size();j++){
							if(ipd.get(j).get("GOODCODE_ID").equals(lpd.get(i).get("GOODCODE_ID"))){
								
								int PNUMBER = Integer.parseInt(lpd.get(i).get("PNUMBER").toString())- Integer.parseInt(ipd.get(j).get("PNUMBER").toString());
								lpd.get(i).put("PNUMBER", PNUMBER);
								break;
							}
						}
					}
				}
				map.put("listNum", lpd);
				map.put("yearMouthDay", str);
				list.add(map);
			}
			cal.add(Calendar.DAY_OF_MONTH, 1);//进行当前日期月份加1
		}
		
		return list;
	}
	
	
	///入库单据（进货单）
	@RequestMapping("/getInOrder")
	@ResponseBody
	public List<PageData> getSailbill() throws Exception{
		PageData pd= new PageData();
		pd = this.getPageData();
		
		int flag=1;
		String lastLoginStart = pd.getString("lastStart");	//开始时间
		String lastLoginEnd = pd.getString("lastEnd");		//结束时间
		if(lastLoginStart != null && !"".equals(lastLoginStart)){
			flag=0;
			pd.put("lastStart", lastLoginStart+" 00:00:00");
		}
		if(lastLoginEnd != null && !"".equals(lastLoginEnd)){
			flag=0;
			pd.put("lastEnd", lastLoginEnd+" 00:00:00");
		} 
		if(flag==1){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			String date =sdf.format(new Date());
			pd.put("date", date);
		}
		List<PageData> lpd=appInOderService.listInOrder(pd);
		if(lpd!=null){
			for(int i=0;i<lpd.size();i++){
				pd.put("INORDER_ID", lpd.get(i).get("INORDER_ID"));
				List<PageData> ipd = appInOderService.listInOrderBody(pd);
				lpd.get(i).put("listgood", ipd);
			/*	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String CREATETIME=sdf.format(lpd.get(0).getString("CREATETIME"));
				pd.put("CREATETIME", CREATETIME);*/
			}
		}
		return lpd;
	}
	
	
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/saveInOrder")
	public String save() throws Exception{
	
//		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("INORDER_ID", this.get32UUID());		//主键
		pd.put("LDATE",DateUtil.getTime().toString());	//录入日期
		pd.put("BILLSTATUS", 1);
		pd.put("BILLTYPE", 1);
		pd.put("UNPAIDAMOUNT", pd.get("ALLAMOUNT"));
		pd.put("PAIDAMOUNT", 0);
		pd.put("THISPAY", 0);
		pd.put("ISSETTLEMENTED", 0);
		appInOderService.save(pd);
//		mv.addObject("msg","success");
//		mv.setViewName("save_result");
//		mv.setViewName("inventorymanagement/inorder/inorder_list");
		return "redirect:/inorder/list.do";
	}
	
	
	
}
