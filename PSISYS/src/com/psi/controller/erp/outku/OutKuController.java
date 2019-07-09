package com.psi.controller.erp.outku;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.psi.controller.base.BaseController;
import com.psi.entity.Page;
import com.psi.service.erp.goods.GoodsManager;
import com.psi.service.erp.outku.OutKuManager;
import com.psi.util.AppUtil;
import com.psi.util.DateUtil;
import com.psi.util.Jurisdiction;
import com.psi.util.ObjectExcelView;
import com.psi.util.PageData;
import com.psi.util.Tools;

/**
 * 说明：商品出库（销售）
 */
@Controller
@RequestMapping(value="/outku")
public class OutKuController extends BaseController {
	
	String menuUrl = "outku/list.do"; //菜单地址(权限用)
	@Resource(name="outkuService")
	private OutKuManager outkuService;
	@Resource(name="goodsService")
	private GoodsManager goodsService;
	
	/**保存库存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增OutKu");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData goodpd = new PageData();
		goodpd = goodsService.findById(pd);
		String ORDER_NUMBERS = pd.getString("ORDER_NUMBERS");
		ORDER_NUMBERS = Tools.notEmpty(ORDER_NUMBERS)?ORDER_NUMBERS:"ZD"+DateUtil.getSdfTimes()+Tools.getRandomNum();
		pd.put("OUTKU_ID", this.get32UUID());				//主键
		pd.put("OUTTIME", Tools.date2Str(new Date()));		//出库时间
		pd.put("OUTDATE", DateUtil.getDay());				//出库日期
		pd.put("GOODS_NAME", goodpd.getString("TITLE"));	//商品名称
		pd.put("ORDER_NUMBER", "D"+DateUtil.getSdfTimes()+Tools.getRandomNum());//订单编号(单个)
		pd.put("ORDER_NUMBERS", ORDER_NUMBERS);				//订单编号(批量)
		pd.put("USERNAME", Jurisdiction.getUsername());		//用户名
		outkuService.save(pd);
		//消减库存
		int zs = Integer.parseInt(goodpd.get("ZCOUNT").toString())-Integer.parseInt(pd.get("INCOUNT").toString());
		goodpd.put("ZCOUNT", zs);
		goodsService.editKuCun(goodpd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**把出库记录临时表的数据转入到正式表
	 * @throws Exception
	 */
	@RequestMapping(value="/transfer")
	@ResponseBody
	public Object transfer() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String errInfo = "success";
		List<PageData>	varList = outkuService.listTemp(pd);	//列出临时OutKu列表
		for(int i=0;i<varList.size();i++){
			PageData ddpd = varList.get(i);
			outkuService.save(ddpd);					//保存到正式出库记录表中
			//消减库存
			PageData goodpd = new PageData();
			goodpd = goodsService.findById(ddpd);
			int zs = Integer.parseInt(goodpd.get("ZCOUNT").toString())-Integer.parseInt(ddpd.get("INCOUNT").toString());
			goodpd.put("ZCOUNT", zs);
			goodsService.editKuCun(goodpd);
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	
	/**新增订单中保存一个商品订单信息
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/saveOne")
	public ModelAndView saveOne() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"保存One");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData goodpd = new PageData();
		goodpd = goodsService.findById(pd);
		String ORDER_NUMBERS = pd.getString("ORDER_NUMBERS");
		pd.put("OUTKU_ID", this.get32UUID());				//主键
		pd.put("OUTTIME", Tools.date2Str(new Date()));		//出库时间
		pd.put("OUTDATE", DateUtil.getDay());				//出库日期
		pd.put("GOODS_NAME", goodpd.getString("TITLE"));	//商品名称
		pd.put("ORDER_NUMBER", "D"+DateUtil.getSdfTimes()+Tools.getRandomNum());//订单编号(单个)
		pd.put("ORDER_NUMBERS", ORDER_NUMBERS);				//订单编号(批量)
		pd.put("USERNAME", Jurisdiction.getUsername());		//用户名
		outkuService.saveOne(pd);
		mv.setViewName("redirect:/outku/goAddDingdan.do?MSG=NEXT&ORDER_NUMBERS="+ORDER_NUMBERS); //保存成功跳转到列表页面
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除临时OutKu");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		outkuService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/editTemp")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改OutKuTemp");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		outkuService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**出库列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表OutKu");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");		//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String lastStart = pd.getString("lastStart");	//开始时间
		String lastEnd = pd.getString("lastEnd");		//结束时间
		if(lastStart != null && !"".equals(lastStart)){
			pd.put("lastStart", lastStart+" 00:00:00");
		}
		if(lastEnd != null && !"".equals(lastEnd)){
			pd.put("lastEnd", lastEnd+" 00:00:00");
		} 
		pd.put("USERNAME", Jurisdiction.getUsername());
		PageData jinepd = outkuService.priceSum(pd);		//总金额
		String zprice = "0";
		if(null != jinepd){
			zprice = jinepd.get("ZPRICE").toString();
		}
		page.setPd(pd);
		List<PageData>	varList = outkuService.list(page);	//列出OutKu列表
		pd.put("OUTDATE", DateUtil.getDay());				//出库日期
		outkuService.deleteOld(pd);							//删除非今天数据(从出库记录临时表中)
		mv.setViewName("erp/outku/outku_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("zprice", zprice);
		mv.addObject("QX",Jurisdiction.getHC());			//按钮权限
		return mv;
	}
	
	/**出库列表(临时)
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/listTemp")
	public ModelAndView listTemp() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表OutKu");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData>	varList = outkuService.listTemp(pd);	//列出临时OutKu列表
		PageData jinepd = outkuService.priceSumTemp(pd);		//总金额
		String zprice = "0";
		if(null != jinepd){
			zprice = jinepd.get("ZPRICE").toString();
		}
		mv.setViewName("erp/outku/outku_list_temp");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("zprice", zprice);
		return mv;
	}
	
	/**订单列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/dingdanList")
	public ModelAndView dingdanList(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"订单列表OutKu");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String lastStart = pd.getString("lastStart");	//开始时间
		String lastEnd = pd.getString("lastEnd");		//结束时间
		if(lastStart != null && !"".equals(lastStart)){
			pd.put("lastStart", lastStart);
		}
		if(lastEnd != null && !"".equals(lastEnd)){
			pd.put("lastEnd", lastEnd);
		} 
		pd.put("USERNAME", Jurisdiction.getUsername());
		page.setPd(pd);
		List<PageData>	varList = outkuService.dingdanList(page);	//列出订单列表
		mv.setViewName("erp/outku/outku_dingdanlist");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**某一订单号下的出库记录
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/listAllByDingdan")
	public ModelAndView listAllByDingdan() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"订单出库记录");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String CUSTOMER_NAME = new String(pd.getString("CUSTOMER_NAME").getBytes("iso-8859-1"), "utf-8");		//客户
		pd.put("CUSTOMER_NAME", CUSTOMER_NAME);
		List<PageData>	varList = outkuService.listAllByDingdan(pd);	//列出出库记录
		mv.setViewName("erp/outku/outku_dingdanlist_outku");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**商品销售报表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/salesReport")
	public ModelAndView salesReport(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"销售报表");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");		//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String lastStart = pd.getString("lastStart");	//开始时间
		String lastEnd = pd.getString("lastEnd");		//结束时间
		if(lastStart != null && !"".equals(lastStart)){
			pd.put("lastStart", lastStart+" 00:00:00");
		}
		if(lastEnd != null && !"".equals(lastEnd)){
			pd.put("lastEnd", lastEnd+" 00:00:00");
		} 
		pd.put("USERNAME", Jurisdiction.getUsername());
		page.setPd(pd);
		List<PageData>	varList = outkuService.salesReport(page);
		mv.setViewName("erp/outku/salesReport");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("USERNAME", Jurisdiction.getUsername());
		List<PageData> goodsList = goodsService.listAll(pd);
		mv.setViewName("erp/outku/outku_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		mv.addObject("goodsList", goodsList);
		return mv;
	}
	
	/**去新增订单页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAddDingdan")
	public ModelAndView goAddDingdan()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String ORDER_NUMBERS = pd.getString("ORDER_NUMBERS");
		ORDER_NUMBERS = Tools.notEmpty(ORDER_NUMBERS)?ORDER_NUMBERS:"ZD"+DateUtil.getSdfTimes()+Tools.getRandomNum();
		pd.put("ORDER_NUMBERS", ORDER_NUMBERS);
		pd.put("USERNAME", Jurisdiction.getUsername());
		List<PageData> goodsList = goodsService.listAll(pd);
		mv.setViewName("erp/outku/dingdan_add");
		mv.addObject("pd", pd);
		mv.addObject("goodsList", goodsList);
		return mv;
	}
	
	 /**去打印出库(订)单页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/ddpirnt")
	public ModelAndView ddpirnt()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = outkuService.findById(pd);	//根据ID读取
		mv.setViewName("erp/outku/outku_ddprint");
		mv.addObject("pd", pd);
		return mv;
	}
	
	 /**去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("USERNAME", Jurisdiction.getUsername());
		List<PageData> goodsList = goodsService.listAll(pd);
		pd = outkuService.findByIdFromTemp(pd);	//根据ID读取
		mv.addObject("goodsList", goodsList);
		mv.setViewName("erp/outku/outku_edit");
		mv.addObject("msg", "editTemp");
		mv.addObject("pd", pd);
		return mv;
	}
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出出库到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("商品名称");	//1
		titles.add("数量");		//2
		titles.add("单价");		//3
		titles.add("总价");		//4
		titles.add("出库时间");	//5
		titles.add("客户");		//6
		titles.add("订单号");	//8
		dataMap.put("titles", titles);
		String keywords = pd.getString("keywords");			//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String lastStart = pd.getString("lastStart");		//开始时间
		String lastEnd = pd.getString("lastEnd");			//结束时间
		if(lastStart != null && !"".equals(lastStart)){
			pd.put("lastStart", lastStart+" 00:00:00");
		}
		if(lastEnd != null && !"".equals(lastEnd)){
			pd.put("lastEnd", lastEnd+" 00:00:00");
		} 
		pd.put("USERNAME", Jurisdiction.getUsername());
		page.setShowCount(30000);							//最大条数 3万条
		page.setPd(pd);
		List<PageData>	varOList = outkuService.list(page);	//列出OutKu列表
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("GOODS_NAME"));		//1
			vpd.put("var2", varOList.get(i).get("INCOUNT").toString());		//2
			vpd.put("var3", varOList.get(i).get("PRICE").toString()+"元");	//3
			vpd.put("var4", varOList.get(i).get("ZPRICE").toString()+"元");	//4
			vpd.put("var5", varOList.get(i).getString("OUTTIME"));	    	//5
			vpd.put("var6", varOList.get(i).getString("CUSTOMER_NAME"));	//6
			vpd.put("var7", varOList.get(i).getString("ORDER_NUMBER"));		//7
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	/**销售报表导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel2")
	public ModelAndView exportExcel2(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出销售报表到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("商品名称");	//1
		titles.add("销量");		//2
		titles.add("销售额");	//3
		dataMap.put("titles", titles);
		String keywords = pd.getString("keywords");		//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String lastStart = pd.getString("lastStart");	//开始时间
		String lastEnd = pd.getString("lastEnd");		//结束时间
		if(lastStart != null && !"".equals(lastStart)){
			pd.put("lastStart", lastStart+" 00:00:00");
		}
		if(lastEnd != null && !"".equals(lastEnd)){
			pd.put("lastEnd", lastEnd+" 00:00:00");
		} 
		pd.put("USERNAME", Jurisdiction.getUsername());
		page.setShowCount(30000);							//最大条数 3万条
		page.setPd(pd);
		List<PageData>	varOList = outkuService.salesReport(page);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("GOODS_NAME"));		//1
			vpd.put("var2", varOList.get(i).get("ZCOUNT").toString());		//2
			vpd.put("var3", varOList.get(i).get("ZPRICE").toString()+"元");	//3
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
