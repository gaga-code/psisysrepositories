package com.psi.controller.basedata.customer;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.psi.controller.base.BaseController;
import com.psi.entity.Page;
import com.psi.entity.system.User;
import com.psi.service.basedata.customer.CustomerManager;
import com.psi.service.erp.level.LevelManager;
import com.psi.service.erp.remarks.RemarksManager;
import com.psi.util.AppUtil;
import com.psi.util.Const;
import com.psi.util.Jurisdiction;
import com.psi.util.ObjectExcelView;
import com.psi.util.PageData;

/**
 * 说明：客户管理
 */
@Controller
@RequestMapping(value="/customer")
public class CustomerController extends BaseController {
	
	String menuUrl = "customer/list.do"; //菜单地址(权限用)
	@Resource(name="customerService")
	private CustomerManager customerService;
	@Resource(name="remarksService")
	private RemarksManager remarksService;
	@Resource(name="levelService")
	private LevelManager levelService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Customer");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("CUSTOMER_ID", this.get32UUID());		//主键
		customerService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除Customer");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		customerService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Customer");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		customerService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Customer");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String lastLoginStart = pd.getString("lastStart");	//开始时间
		String lastLoginEnd = pd.getString("lastEnd");		//结束时间
		if(lastLoginStart != null && !"".equals(lastLoginStart)){
			pd.put("lastStart", lastLoginStart+" 00:00:00");
		}
		if(lastLoginEnd != null && !"".equals(lastLoginEnd)){
			pd.put("lastEnd", lastLoginEnd+" 00:00:00");
		} 
		page.setPd(pd);
		List<PageData>	varList = customerService.list(page);	//列出Customer列表
		mv.setViewName("basedata/customer/customer_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**列表(弹窗选择用)
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/windowsList")
	public ModelAndView windowsList(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Customer");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String lastLoginStart = pd.getString("lastStart");	//开始时间
		String lastLoginEnd = pd.getString("lastEnd");		//结束时间
		if(lastLoginStart != null && !"".equals(lastLoginStart)){
			pd.put("lastStart", lastLoginStart+" 00:00:00");
		}
		if(lastLoginEnd != null && !"".equals(lastLoginEnd)){
			pd.put("lastEnd", lastLoginEnd+" 00:00:00");
		} 
		page.setPd(pd);
		List<PageData>	varList = customerService.list(page);	//列出Customer列表
		mv.setViewName("basedata/customer/windows_customer_list");
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
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		pd.put("PSI_NAME", user.getNAME());	//用户主键
		mv.setViewName("basedata/customer/customer_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		List<HashMap> varListL = new ArrayList<HashMap>();
		HashMap xianjin = new HashMap();
		xianjin.put("LEVEL_ID", 1);
		xianjin.put("TITLE","现金");
		HashMap yuejie = new HashMap();
		yuejie.put("LEVEL_ID", 2);
		yuejie.put("TITLE","月结");
		varListL.add(xianjin);
		varListL.add(yuejie);
		mv.addObject("varListL", varListL);
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
		pd = customerService.findById(pd);	//根据ID读取
		mv.setViewName("basedata/customer/customer_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		List<HashMap> varListL = new ArrayList<HashMap>();
		HashMap xianjin = new HashMap();
		xianjin.put("LEVEL_ID", 1);
		xianjin.put("TITLE","现金");
		HashMap yuejie = new HashMap();
		yuejie.put("LEVEL_ID", 2);
		yuejie.put("TITLE","月结");
		varListL.add(xianjin);
		varListL.add(yuejie);
		mv.addObject("varListL", varListL);
		return mv;
	}	
	
	 /**查看页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goView")
	public ModelAndView goView()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = customerService.findById(pd);	//根据ID读取
		mv.setViewName("basedata/customer/customer_view");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		List<HashMap> varListL = new ArrayList<HashMap>();
		HashMap xianjin = new HashMap();
		xianjin.put("LEVEL_ID", 1);
		xianjin.put("TITLE","现金");
		HashMap yuejie = new HashMap();
		yuejie.put("LEVEL_ID", 2);
		yuejie.put("TITLE","月结");
		varListL.add(xianjin);
		varListL.add(yuejie);
		mv.addObject("varListL", varListL);
		return mv;
	}
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Customer");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			customerService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出Customer到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("姓名");	//1
		titles.add("客户编号");	//2
		titles.add("手机");	//3
		titles.add("地址");	//4
		titles.add("简称");	//5
		titles.add("拼音编码");	//6
		titles.add("建档时间");	//7
		titles.add("信誉程度");	//8
		titles.add("电话");	//9
		titles.add("传真");	//10
		titles.add("传呼");	//11
		titles.add("联系人");	//12
		titles.add("经销方式");	//13
		titles.add("经手人");	//14
		titles.add("备注");	//15
		titles.add("备注2");	//16
		titles.add("备注3");	//17
		dataMap.put("titles", titles);
		List<PageData> varOList = customerService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("CUATOMERNAME"));	    //1
			vpd.put("var2", varOList.get(i).getString("CUSTOMERCODE"));	    //2
			vpd.put("var3", varOList.get(i).get("PHONE").toString());	//3
			vpd.put("var4", varOList.get(i).getString("ADDRESS"));	    //4
			vpd.put("var5", varOList.get(i).get("SIMPLENAME").toString());	//5
			vpd.put("var6", varOList.get(i).getString("YICODE"));	    //6
			String createtime = ((Timestamp)varOList.get(i).get("CREATETIME")).toString();
			vpd.put("var7", createtime.substring(0, createtime.length()-2));	    //7
			vpd.put("var8", varOList.get(i).get("CREDITDEGREE").toString());	//8
			vpd.put("var9", varOList.get(i).getString("TELEPHONE"));	    //9
			vpd.put("var10", varOList.get(i).getString("FAX"));	    //10
			vpd.put("var11", varOList.get(i).getString("PAGING"));	    //11
			vpd.put("var12", varOList.get(i).getString("LINKMAN"));	    //12
			vpd.put("var13","1".equals( varOList.get(i).getString("DISTRIBUTIONMODE") )?"现金":"月结");	    //13
			vpd.put("var14", varOList.get(i).getString("PSI_NAME"));	    //14
			vpd.put("var14", varOList.get(i).getString("NOTE"));	    //15
			vpd.put("var15", varOList.get(i).getString("NOTE2"));	    //16
			vpd.put("var16", varOList.get(i).getString("NOTE3"));	    //17
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