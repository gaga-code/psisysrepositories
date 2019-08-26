package com.psi.controller.app.inventorymanagement.salebill;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.psi.controller.base.BaseController;
import com.psi.service.app.inventorymanagement.customerbill.AppCustomerbillManager;
import com.psi.service.app.inventorymanagement.salebill.AppSalebillManager;
import com.psi.util.DateUtil;
import com.psi.util.Jurisdiction;
import com.psi.util.PageData;

@Controller
@RequestMapping("/appSalebill")
public class AppSalebillController extends BaseController {

	@Resource(name = "appSalebillService")
	private AppSalebillManager appSalebillService;
	@Resource(name = "appCustomerbillService")
	private AppCustomerbillManager appCustomerbillService;

	public List<PageData> listDataAndNumAndPrice() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		return appSalebillService.listDataAndNumAndPrice(pd);

	}

	// 获取今日销售信息
	@RequestMapping("/getSaleInfoByToday")
	@ResponseBody
	public List<PageData> getSaleInfoByToday() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("BILLTYPE", 2);
		List<PageData> lpd = appSalebillService.listSaleInfoByToday(pd); // 查询进货单
		pd.put("BILLTYPE", 8);
		List<PageData> ipd = appSalebillService.listSaleInfoByToday(pd); // 查询退货单
		if (lpd != null && lpd.size() != 0) { // 算出结果类似小米
												// 销售量有20台，销售额有60000元，单数有5单（一单购买了5台），毛利10000元；
			if (ipd != null && ipd.size() != 0) {

				for (int i = 0; i < lpd.size(); i++) {
					for (int j = 0; j < ipd.size(); j++) {
						if (lpd.get(i).get("GOODCODE").equals(ipd.get(j).get("GOODCODE"))) {
							double amount = (Double) lpd.get(i).get("AMOUNT") - (Double) ipd.get(j).get("AMOUNT");
							int pnumber = Integer.valueOf(lpd.get(i).get("PNUMBER").toString())
									- Integer.valueOf(ipd.get(j).get("PNUMBER").toString());
							double maoLi = (Double) lpd.get(i).get("maoLi") - (Double) ipd.get(j).get("maoLi");
							lpd.get(i).put("AMOUNT", amount);
							lpd.get(i).put("PNUMBER", pnumber);
							lpd.get(i).put("maoLi", maoLi);
							break;
						}
					}
				}
			}
		}
		return lpd;
	}

	// 获取销售汇总（按月）
	@RequestMapping("/getSaleInfoByMouth")
	@ResponseBody
	public List<PageData> getSaleInfoByMouth() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();

		String PK_SOBOOKS = pd.getString("PK_SOBOOKS");
		String yyyy = pd.getString("yyyy");// 得到要查询的年份

		Calendar cal = Calendar.getInstance(); // 定义日期实例
		String year = String.valueOf(cal.get(Calendar.YEAR)); // 查询当前年份
		if (pd.getString("yyyy") == null) {
			yyyy = year;
		}
		Date start = new SimpleDateFormat("yyyy-MM").parse(yyyy + "-1");// 定义起始日期
																		// 年份的一月份开始
		Date end;// 定义结束日期

		if (yyyy.equals(year)) {// 如果yyyy和当前年份相等，则查询今年
			end = new SimpleDateFormat("yyyy-MM").parse(year + "-" + (new Date().getMonth() + 1));
		} else {
			end = new SimpleDateFormat("yyyy-MM").parse(yyyy + "-12");
		}

		cal.setTime(start);// 设置日期起始时间

		List<PageData> list = new ArrayList();

		while (cal.getTime().getTime() <= end.getTime()) {// 判断是否到结束日期
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

			String str = sdf.format(cal.getTime());

			System.out.println(str);// 输出日期结果
			pd.put("date", str);

			List<PageData> lpd = appSalebillService.listSalebillByMouth(pd);
			if (lpd != null && lpd.size() != 0) {
				PageData p = new PageData();
				String yearmouth = str; // 月份
				double uppaid = 0; // 未付金额
				double allamount = 0; // 总金额
				int danshu = lpd.size(); // 单数
				double pureamount = 0; // 月利润
				double pnumber = 0; // 月销售量
				for (int i = 0; i < lpd.size(); i++) {
					if (lpd.get(i).get("BILLTYPE").equals("2"))// 如果为销售单
					{
						allamount += (Double) lpd.get(i).get("ALLAMOUNT");
						uppaid += (Double) lpd.get(i).get("UNPAIDAMOUNT");
						String SALEBILL_ID = lpd.get(i).getString("SALEBILL_ID");
						PageData pd1 = appSalebillService.findPureAmountById(SALEBILL_ID); /// 查询SALEBILL_ID的利润
						pnumber += Double.parseDouble(pd1.get("pnumber").toString());
						pureamount += (Double) pd1.get("pureamount");
					} else { // 为退货单
						allamount -= (Double) lpd.get(i).get("ALLAMOUNT");
						uppaid -= (Double) lpd.get(i).get("UNPAIDAMOUNT");
						String SALEBILL_ID = lpd.get(i).getString("SALEBILL_ID");
						PageData pd1 = appSalebillService.findPureAmountById(SALEBILL_ID); /// 查询SALEBILL_ID的利润
						pnumber -= Double.parseDouble(pd1.get("pnumber").toString());
						pureamount -= (Double) pd1.get("pureamount");
					}
				}
				List<PageData> pd2 = appCustomerbillService.listPayAndAmountByMouth(pd);
				p.put("yearmouth", yearmouth);
				p.put("uppaid", uppaid);
				p.put("allamount", allamount);
				p.put("danshu", danshu);
				p.put("pnumber", pnumber);
				p.put("pureamount", pureamount);
				p.put("paymethod", pd2);
				list.add(p);
			}

			cal.add(Calendar.MONTH, 1);// 进行当前日期月份加1
		}
		return list;
	}

	// 获取某一个月每天的销售汇总（按天数）
	@RequestMapping("/getSaleInfoDayByMouth")
	@ResponseBody
	public List<PageData> getSaleInfoDayByMouth() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();

		String PK_SOBOOKS = pd.getString("PK_SOBOOKS");
		String yearmouth = pd.getString("yearmouth");// 得到要查询的年月

		Calendar cal = Calendar.getInstance(); // 定义日期实例

		String today = "" + cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1); // 得到今天的年月

		if (pd.getString("yearmouth") == null) {
			yearmouth = today;
		}

		Date start = new SimpleDateFormat("yyyy-MM-dd").parse(yearmouth + "-1");// 定义起始日期
																				// 月份的第一天开始
		Date end;// 定义结束日期
		String[] strarray1 = yearmouth.split("-");
		int y1 = Integer.parseInt(strarray1[0]);
		int m1 = Integer.parseInt(strarray1[1]);

		String[] strarray2 = today.split("-");
		int y2 = Integer.parseInt(strarray2[0]);
		int m2 = Integer.parseInt(strarray2[1]);

		if (y1 == y2 && m1 == m2) {// 如果yearmouth和当前月份相等，则查询当今这个月
			Calendar c = Calendar.getInstance();
			end = new SimpleDateFormat("yyyy-MM-dd").parse(today + "-" + c.get(Calendar.DAY_OF_MONTH));
		} else {

			Calendar c = Calendar.getInstance();
			c.set(y1, m1, 0); // 输入类型为int类型
			int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);

			end = new SimpleDateFormat("yyyy-MM-dd").parse(yearmouth + "-" + dayOfMonth);
		}
		cal.setTime(start);// 设置日期起始时间

		List<PageData> list = new ArrayList();
		while (cal.getTime().getTime() <= end.getTime()) {// 判断是否到结束日期
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			String str = sdf.format(cal.getTime());

			System.out.println(str);// 输出日期结果
			pd.put("date", str);

			List<PageData> lpd = appSalebillService.listSaleInfoDayByMouth(pd);
			if (lpd != null && lpd.size() != 0) {
				PageData p = new PageData();
				String yearmouthday = str; // 年月日
				double uppaid = 0; // 未付金额
				double allamount = 0; // 总金额
				int danshu = lpd.size(); // 单数
				double pureamount = 0; // 日利润
				double pnumber = 0; // 日销售量
				for (int i = 0; i < lpd.size(); i++) {
					if (lpd.get(i).get("BILLTYPE").equals("2"))// 如果为销售单
					{
						allamount += (Double) lpd.get(i).get("ALLAMOUNT");
						uppaid += (Double) lpd.get(i).get("UNPAIDAMOUNT");
						String SALEBILL_ID = lpd.get(i).getString("SALEBILL_ID");
						PageData pd1 = appSalebillService.findPureAmountById(SALEBILL_ID); /// 查询SALEBILL_ID的利润
						pnumber += Double.parseDouble(pd1.get("pnumber").toString());
						pureamount += (Double) pd1.get("pureamount");
					} else { // 为退货单
						allamount -= (Double) lpd.get(i).get("ALLAMOUNT");
						uppaid -= (Double) lpd.get(i).get("UNPAIDAMOUNT");
						String SALEBILL_ID = lpd.get(i).getString("SALEBILL_ID");
						PageData pd1 = appSalebillService.findPureAmountById(SALEBILL_ID); /// 查询SALEBILL_ID的利润
						pnumber -= Double.parseDouble(pd1.get("pnumber").toString());
						pureamount -= (Double) pd1.get("pureamount");
					}
				}
				List<PageData> pd2 = appCustomerbillService.listPayAndAmountByMouthDay(pd);
				p.put("yearmouthday", yearmouthday);
				p.put("uppaid", uppaid);
				p.put("allamount", allamount);
				p.put("danshu", danshu);
				p.put("pnumber", pnumber);
				p.put("pureamount", pureamount);
				p.put("paymethod", pd2);
				list.add(p);
			}

			cal.add(Calendar.DAY_OF_MONTH, 1);// 进行当前日期月份加1
		}
		return list;
	}

	// 根据排序类型（销售额/销量/单数/毛利）sorttype=(销售额=1，销量=2，单数=3，毛利=4)
	// 时间段（默认是当前月） yyyy-MM 2019-8
	// 商品分类名称：`TYPENAME`（默认是全部）
	@RequestMapping("/getSaledGoodsList")
	@ResponseBody
	public List<PageData> getSaledGoodsBySTT(HttpServletRequest request) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		

		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/uploadFiles/uploadImgs/";
		
		
		if (pd.getString("sortType") == null) { // 默认是销售额 sorttype=1
			pd.put("sortType", 1);
		}
		if (pd.get("TYPENAME") != null && pd.get("TYPENAME").equals("")) {
			pd.put("TYPENAME", null);
		}

		String startTime = pd.getString("startTime");
		String endTime = pd.getString("endTime");
		if (startTime == null && endTime == null) {
			Calendar cal = Calendar.getInstance(); // 定义日期实例
			int mouth = new Date().getMonth() + 1;
			String yearMouth;
			if (mouth < 10) {
				yearMouth = String.valueOf(cal.get(Calendar.YEAR)) + "-0" + mouth;
			} else {
				yearMouth = String.valueOf(cal.get(Calendar.YEAR)) + "-" + mouth;
			}
			pd.put("yearMouth", yearMouth);
		}
		pd.put("BILLTYPE", 2);
		List<PageData> list1 = appSalebillService.listSaledGoodsBySTT(pd);
		pd.put("BILLTYPE", 8);
		List<PageData> list2 = appSalebillService.listSaledGoodsBySTT(pd);
		for (int i = 0; i < list1.size(); i++) {
			list1.get(i).put("Path", basePath+list1.get(i).getString("GOODPIC"));
			for (int j = 0; j < list2.size(); j++) {
				if (list1.get(i).get("GOODCODE_ID").equals(list2.get(j).get("GOODCODE_ID"))) {
					double ALLAMOUNT = (Double) list1.get(i).get("ALLAMOUNT") - (Double) list2.get(j).get("ALLAMOUNT");
					int PNUMBER = Integer.valueOf(list1.get(i).get("PNUMBER").toString())
							- Integer.valueOf(list2.get(j).get("PNUMBER").toString());
					double maoLi = (Double) list1.get(i).get("maoLi") - (Double) list2.get(j).get("maoLi");
					list1.get(i).put("ALLAMOUNT", ALLAMOUNT);
					list1.get(i).put("PNUMBER", PNUMBER);
					list1.get(i).put("maoLi", maoLi);
					break;
				}
			}
		}
		return list1;
	}

	/// 出库单据（销售单）
	@RequestMapping("/getSailbill")
	@ResponseBody
	public List<PageData> getSailbill() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();

		int flag = 1;
		String lastLoginStart = pd.getString("lastStart"); // 开始时间
		String lastLoginEnd = pd.getString("lastEnd"); // 结束时间
		if (lastLoginStart != null && !"".equals(lastLoginStart)) {
			flag = 0;
			pd.put("lastStart", lastLoginStart + " 00:00:00");
		}
		if (lastLoginEnd != null && !"".equals(lastLoginEnd)) {
			flag = 0;
			pd.put("lastEnd", lastLoginEnd + " 00:00:00");
		}
		if (flag == 1) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			String date = sdf.format(new Date());
			pd.put("date", date);
		}
		List<PageData> lpd = appSalebillService.listsalebill(pd);
		if (lpd != null && lpd.size() != 0) {
			for (int i = 0; i < lpd.size(); i++) {
				pd.put("SALEBILL_ID", lpd.get(i).get("SALEBILL_ID"));
				List<PageData> ipd = appSalebillService.listsalebillBody(pd);
				lpd.get(i).put("listgood", ipd);
			}
		}
		return lpd;
	}

	// 根据排序类型（销售额/销量/单数/毛利）sorttype=(销售额=1，销量=2，单数=3，毛利=4)
	// 时间段（默认是当前月） yyyy-MM 2019-8
	// 商品分类名称：`TYPENAME`（默认是全部）
	@RequestMapping("/getSaledByCustomer")
	@ResponseBody
	public List<HashMap<String,Object>> getSaledByCustomer() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		if (pd.getString("sortType") == null) { // 默认是销售额 sorttype=1
			pd.put("sortType", 1);
		}

		String startTime = pd.getString("startTime");
		String endTime = pd.getString("endTime");
		if (startTime == null && endTime == null) {
			Calendar cal = Calendar.getInstance(); // 定义日期实例
			int mouth = new Date().getMonth() + 1;
			String yearMouth;
			if (mouth < 10) {
				yearMouth = String.valueOf(cal.get(Calendar.YEAR)) + "-0" + mouth;
			} else {
				yearMouth = String.valueOf(cal.get(Calendar.YEAR)) + "-" + mouth;
			}
			pd.put("yearMouth", yearMouth);
		}
		pd.put("BILLTYPE", 2);
		List<PageData> list1 = appSalebillService.listSaledByCustomer(pd);
		pd.put("BILLTYPE", 8);
		List<PageData> list2 = appSalebillService.listSaledByCustomer(pd);
		for (int i = 0; i < list1.size(); i++) {
			for (int j = 0; j < list2.size(); j++) {
				if (list1.get(i).get("CUSTOMERCODE").equals(list2.get(j).get("CUSTOMERCODE"))) {
					if (list1.get(i).get("GOODCODE_ID").equals(list2.get(j).get("GOODCODE_ID"))) {
						double AMOUNT = (Double) list1.get(i).get("AMOUNT") - (Double) list2.get(j).get("AMOUNT");
						int PNUMBER = Integer.valueOf(list1.get(i).get("PNUMBER").toString())
								- Integer.valueOf(list2.get(j).get("PNUMBER").toString());
						double MONEY = (Double) list1.get(i).get("MONEY") - (Double) list2.get(j).get("MONEY");
						list1.get(i).put("AMOUNT", AMOUNT);
						list1.get(i).put("PNUMBER", PNUMBER);
						list1.get(i).put("MONEY", MONEY);
						break;
					}
				}
			}
		}
		List<HashMap<String,Object>> list = new ArrayList();  //把商品编号相同的商品数量加起来
		for (int i = 0; i < list1.size(); i++) {
			int flag=1;
			for(int j=0;j<list.size();j++){
				if(list.get(j).get("CUSTOMERCODE").equals(list1.get(i).getString("CUSTOMERCODE"))){ 
					double AMOUNT = (Double)list1.get(i).get("AMOUNT")+(Double)list.get(j).get("AMOUNT");
					double MONEY = (Double)list1.get(i).get("MONEY")+(Double)list.get(j).get("MONEY");
					list.get(j).put("AMOUNT", AMOUNT);
					list.get(j).put("MONEY", MONEY);
					List<PageData> l = (List<PageData>) list.get(j).get("list");
					int flag1=1;
					for(int k=0;k<l.size();k++){
						String gc1 = l.get(k).getString("GOODCODE");
						String gc2 = list1.get(i).getString("GOODCODE_ID");
						if(gc1.equals(gc2)){
							int PNUMBER = (Integer) l.get(k).get("PNUMBER") + (Integer)list1.get(i).get("PNUMBER");
							((List<PageData>)list.get(j).get("list")).get(k).put("PNUMBER", PNUMBER );
							flag1=0;
							break;
						}
					}
					if(flag1==1){
						PageData p = new PageData();
						p.put("GOODCODE", list1.get(i).getString("GOODCODE_ID"));
						p.put("GOODNAME", list1.get(i).getString("GOODNAME"));
						p.put("PNUMBER", list1.get(i).get("PNUMBER"));
						((List<PageData>)list.get(j).get("list")).add(p);
					}
					
					flag=0;
					break;
				}
			}
			if(flag==1){
				HashMap map = new HashMap();
				map.put("CUSTOMERCODE", list1.get(i).getString("CUSTOMERCODE"));
				map.put("CUATOMERNAME", list1.get(i).getString("CUATOMERNAME"));
				map.put("TELEPHONE", list1.get(i).getString("TELEPHONE"));
				map.put("AMOUNT",  list1.get(i).get("AMOUNT"));
				map.put("MONEY",  list1.get(i).get("MONEY"));
				List<PageData> ls = new ArrayList();
				PageData p = new PageData();
				p.put("GOODCODE", list1.get(i).getString("GOODCODE_ID"));
				p.put("GOODNAME", list1.get(i).getString("GOODNAME"));
				p.put("PNUMBER", list1.get(i).get("PNUMBER"));
				ls.add(p);
				map.put("list", ls);
				list.add(map);
			}
		}
		
		return list;
	}
	
	// 根据排序类型（销售额/销量/单数/毛利）sorttype=(销售额=1，销量=2，单数=3，毛利=4)
	// 时间段（默认是当前月） yyyy-MM 2019-8
	// 商品分类名称：`TYPENAME`（默认是全部）
	@RequestMapping("/getSaledByUser")
	@ResponseBody
	public	List<HashMap<String,Object>>  getSaledByUser() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		if (pd.getString("sortType") == null) { // 默认是销售额 sorttype=1
			pd.put("sortType", 1);
		}

		String startTime = pd.getString("startTime");
		String endTime = pd.getString("endTime");
		if (startTime == null && endTime == null) {
			Calendar cal = Calendar.getInstance(); // 定义日期实例
			int mouth = new Date().getMonth() + 1;
			String yearMouth;
			if (mouth < 10) {
				yearMouth = String.valueOf(cal.get(Calendar.YEAR)) + "-0" + mouth;
			} else {
				yearMouth = String.valueOf(cal.get(Calendar.YEAR)) + "-" + mouth;
			}
			pd.put("yearMouth", yearMouth);
		}
		pd.put("BILLTYPE", 2);
		List<PageData> list1 = appSalebillService.listSaledByUser(pd);
		pd.put("BILLTYPE", 8);
		List<PageData> list2 = appSalebillService.listSaledByUser(pd);
		for (int i = 0; i < list1.size(); i++) {
			for (int j = 0; j < list2.size(); j++) {
				if (list1.get(i).get("USER_ID").equals(list2.get(j).get("USER_ID"))) {
					if (list1.get(i).get("GOODCODE_ID").equals(list2.get(j).get("GOODCODE_ID"))) {
						double AMOUNT = (Double) list1.get(i).get("AMOUNT") - (Double) list2.get(j).get("AMOUNT");
						int PNUMBER = Integer.valueOf(list1.get(i).get("PNUMBER").toString())
								- Integer.valueOf(list2.get(j).get("PNUMBER").toString());
						double MONEY = (Double) list1.get(i).get("MONEY") - (Double) list2.get(j).get("MONEY");
						list1.get(i).put("AMOUNT", AMOUNT);
						list1.get(i).put("PNUMBER", PNUMBER);
						list1.get(i).put("MONEY", MONEY);
						break;
					}
				}
			}
		}
		
		List<HashMap<String,Object>> list = new ArrayList();  //把商品编号相同的商品数量加起来
		for (int i = 0; i < list1.size(); i++) {
			int flag=1;
			for(int j=0;j<list.size();j++){
				if(list.get(j).get("USER_ID").equals(list1.get(i).getString("USER_ID"))){ 
					double AMOUNT = (Double)list1.get(i).get("AMOUNT")+(Double)list.get(j).get("AMOUNT");
					double MONEY = (Double)list1.get(i).get("MONEY")+(Double)list.get(j).get("MONEY");
					list.get(j).put("AMOUNT", AMOUNT);
					list.get(j).put("MONEY", MONEY);
					List<PageData> l = (List<PageData>) list.get(j).get("list");
					int flag1=1;
					for(int k=0;k<l.size();k++){
						String gc1 = l.get(k).getString("GOODCODE");
						String gc2 = list1.get(i).getString("GOODCODE_ID");
						if(gc1.equals(gc2)){
							int PNUMBER = (Integer) l.get(k).get("PNUMBER") + (Integer)list1.get(i).get("PNUMBER");
							((List<PageData>)list.get(j).get("list")).get(k).put("PNUMBER", PNUMBER );
							flag1=0;
							break;
						}
					}
					if(flag1==1){
						PageData p = new PageData();
						p.put("GOODCODE", list1.get(i).getString("GOODCODE_ID"));
						p.put("GOODNAME", list1.get(i).getString("GOODNAME"));
						p.put("PNUMBER", list1.get(i).get("PNUMBER"));
						((List<PageData>)list.get(j).get("list")).add(p);
					}
					
					flag=0;
					break;
				}
			}
			if(flag==1){
				HashMap map = new HashMap();
				map.put("USER_ID", list1.get(i).getString("USER_ID"));
				map.put("NAME", list1.get(i).getString("NAME"));
				map.put("ENTERPRISENAME", list1.get(i).getString("ENTERPRISENAME"));
				map.put("AMOUNT",  list1.get(i).get("AMOUNT"));
				map.put("MONEY",  list1.get(i).get("MONEY"));
				List<PageData> ls = new ArrayList();
				PageData p = new PageData();
				p.put("GOODCODE", list1.get(i).getString("GOODCODE_ID"));
				p.put("GOODNAME", list1.get(i).getString("GOODNAME"));
				p.put("PNUMBER", list1.get(i).get("PNUMBER"));
				ls.add(p);
				map.put("list", ls);
				list.add(map);
			}
		}
		
		return list;
	}

	//出库商品的数据更新到数据库
	@RequestMapping("/insertSailbill")
	@ResponseBody
	public String  saveSailbill() throws Exception{
		String menuUrl = "salebill/list.do"; //菜单地址(权限用)
		logBefore(logger, Jurisdiction.getUsername()+"新增salebill");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		
		PageData pd = new PageData();
		pd =  this.getPageData();
		
		String str = pd.getString("salebill");
		String[] sale = str.split(",");
		
		pd.put("CUSBILLNO", sale[0]);
		pd.put("PAYDATE",sale[1]);
		pd.put("NOTE",sale[2]);
		pd.put("CUSTOMER_ID",sale[3]);
		pd.put("USER_ID", sale[4]);
		pd.put("ALLAMOUNT", sale[5]);
		if(sale.length==7){
			pd.put("TOADDRESS", sale[6]);	
		}else{
			pd.put("TOADDRESS", "");
		}
		pd.put("WAREHOUSE_ID","");
		
		pd.put("SALEBILL_ID", this.get32UUID());		//主键
		pd.put("LDATE",DateUtil.getTime().toString());	//录入日期
		pd.put("BILLSTATUS", 1);
		pd.put("BILLTYPE", 2);
		pd.put("UNPAIDAMOUNT", pd.get("ALLAMOUNT"));
		pd.put("PAIDAMOUNT", 0);
		pd.put("THISPAY", 0);
		pd.put("ISSETTLEMENTED", 0);
		pd = appSalebillService.save(pd);
		
		return "OK";
	}
	
	
}
