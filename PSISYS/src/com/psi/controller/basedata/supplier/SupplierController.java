package com.psi.controller.basedata.supplier;

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
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.Session;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.psi.controller.base.BaseController;
import com.psi.entity.Page;
import com.psi.entity.system.User;
import com.psi.service.basedata.goods.GoodsManager;
import com.psi.service.basedata.supplier.SupplierManager;
import com.psi.service.system.fhlog.FHlogManager;
import com.psi.service.system.user.UserManager;
import com.psi.util.AppUtil;
import com.psi.util.Const;
import com.psi.util.FileDownload;
import com.psi.util.FileUpload;
import com.psi.util.Jurisdiction;
import com.psi.util.ObjectExcelRead;
import com.psi.util.ObjectExcelView;
import com.psi.util.PageData;
import com.psi.util.PathUtil;
import com.psi.util.enumproduct.EnumProductUtil;

/**
 * 说明：供应商管理
 */
@Controller
@RequestMapping(value="/supplier")
public class SupplierController extends BaseController {
	
	String menuUrl = "supplier/list.do"; //菜单地址(权限用)
	@Resource(name="supplierService")
	private SupplierManager supplierService;
	@Resource(name="fhlogService")
	private FHlogManager FHLOG;
	@Resource(name="goodsService")
	private GoodsManager goodsService;
	@Resource(name="userService")
	private UserManager userService;
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增supplier");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("SUPPLIER_ID", this.get32UUID());		//主键
		supplierService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除supplier");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		supplierService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改supplier");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		supplierService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表supplier");
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
		List<PageData>	varList = supplierService.list(page);	//列出supplier列表
		mv.setViewName("basedata/supplier/supplier_list");
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
		logBefore(logger, Jurisdiction.getUsername()+"列表supplier");
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
		List<PageData>	varList = supplierService.list(page);	//列出supplier列表
		mv.setViewName("basedata/supplier/windows_supplier_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/listNameAndID" ,produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object listNameAndID()throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表supplier");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		List<PageData> varList = supplierService.listAll(pd);	//列出supplier列表
		map.put("varList", varList);
		return AppUtil.returnObject(pd, map);
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
		mv.setViewName("basedata/supplier/supplier_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		mv.addObject("varListL", EnumProductUtil.productDistributionModeList());
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
		pd = supplierService.findById(pd);	//根据ID读取
		mv.setViewName("basedata/supplier/supplier_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		mv.addObject("varListL", EnumProductUtil.productDistributionModeList());
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
		pd = supplierService.findById(pd);	//根据ID读取
		mv.setViewName("basedata/supplier/supplier_view");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		mv.addObject("varListL", EnumProductUtil.productDistributionModeList());
		return mv;
	}
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除supplier");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String[] ids = DATA_IDS.split(",");
			StringBuffer idstr = new StringBuffer("");
			for(int i=0;i<ids.length;i++) {
				idstr.append("'"+ids[i]+"',");
			}
			supplierService.deleteAll(idstr.toString().substring(0,idstr.toString().length()-1),(String)pd.get("PK_SOBOOKS"));
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
		logBefore(logger, Jurisdiction.getUsername()+"导出supplier到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("供应商编号");	//1
		titles.add("供应商名称");	//2
		titles.add("联系人");	//3
		titles.add("简称");	//4
		titles.add("联系电话");	//5
		titles.add("手机");	//6
		titles.add("公司名称");	//7
		titles.add("所在地区");	//8
		titles.add("地址");	//9
		titles.add("传真");	//10
		titles.add("传呼");	//11
		titles.add("拼音编码");//12
		titles.add("经销方式");	//13
		titles.add("信誉程度");	//4
		titles.add("开户银行");//15
		titles.add("银行账号");//16
		titles.add("邮编");//17
		titles.add("邮箱");//18
		titles.add("账套");//19
	/*	titles.add("当前应收款");//20
*/		titles.add("网址");//21
		titles.add("经手人");	//22
		titles.add("备注");	//23
		dataMap.put("titles", titles);
		List<PageData> varOList = supplierService.listAllSupp(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("SUPPLIERCODE"));	    //1
			vpd.put("var2", varOList.get(i).getString("SUPPLIERNAME"));	    //2
			vpd.put("var3", varOList.get(i).getString("LINKMAN"));	//3
			vpd.put("var4", varOList.get(i).getString("SIMPLENAME"));	    //4
			vpd.put("var5", varOList.get(i).getString("TELEPHONE"));	//5
			vpd.put("var6", varOList.get(i).getString("PHONE"));	    //6
			vpd.put("var6", varOList.get(i).getString("COMPANY"));	    //7
			vpd.put("var8", varOList.get(i).getString("SUBADDR_ID"));	//8
			vpd.put("var9", varOList.get(i).getString("ADDRESS"));	    //9
			vpd.put("var10", varOList.get(i).getString("FAX"));	    //10
			vpd.put("var11", varOList.get(i).getString("PAGING"));	    //11
			vpd.put("var12", varOList.get(i).getString("YICODE"));	    //12
			vpd.put("var13", varOList.get(i).getString("DISTRIBUTIONMODE"));	    //13
			vpd.put("var14", String.valueOf(varOList.get(i).get("CREDITDEGREE")));	    //14
			vpd.put("var15", varOList.get(i).getString("OPENBANK"));	    //15
			vpd.put("var16", varOList.get(i).getString("BANKACCOUNT"));	    //16
			vpd.put("var17", varOList.get(i).getString("MAILCODE"));	    //17
			vpd.put("var18", varOList.get(i).getString("EMAIL"));	    //18
			vpd.put("var19", varOList.get(i).getString("ENTERPRISENAME"));	    //19
			/*vpd.put("var20", varOList.get(i).getString("DEF1"));	    //20
*/			vpd.put("var20", varOList.get(i).getString("NETADDR"));	    //20
			vpd.put("var21", varOList.get(i).getString("NAME"));	    //21
			vpd.put("var22", varOList.get(i).getString("NOTE"));	    //22
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	
	
	@RequestMapping(value="/goUploadExcel")
	public ModelAndView goUploadExcel()throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("basedata/supplier/uploadexcel");
		return mv;
	}
	
	/**从EXCEL导入到数据库
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/readExcel")
	public ModelAndView readExcel(
			@RequestParam(value="excel",required=false) MultipartFile file
			) throws Exception{
		FHLOG.save(Jurisdiction.getUsername(), "从EXCEL导入到数据库");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd=this.getPageData();
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		if (null != file && !file.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;								//文件上传路径
			String fileName =  FileUpload.fileUp(file, filePath, "supplierexcel");							//执行上传
			List<PageData> listPd = (List)ObjectExcelRead.readExcel(filePath, fileName, 1, 0, 0);		//执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
			/*存入数据库操作======================================*/
			pd.put("DR",0);//逻辑删除
			pd.put("DISTRIBUTIONMODE", "1"); //经销方式
			for(int i=0;i<listPd.size();i++){		
				pd.put("SUPPLIER_ID", this.get32UUID());										//ID
				pd.put("SUPPLIERCODE", listPd.get(i).getString("var0"));							//供应商编号
				pd.put("SUPPLIERNAME", listPd.get(i).getString("var1"));	//供应商名称
				PageData isexits=supplierService.findByCode(pd);
				if(isexits!=null){
					continue;
				}
				pd.put("LINKMAN", listPd.get(i).getString("var2"));	//3
				pd.put("LINKMAN", listPd.get(i).getString("var3"));	    //4
				pd.put("TELEPHONE", listPd.get(i).getString("var4"));	//5
				pd.put("PHONE", listPd.get(i).getString("var5"));	    //6
				pd.put("COMPANY", listPd.get(i).getString("var6"));	    //7
				pd.put("SUBADDR_ID", listPd.get(i).getString("var7"));	//8
				pd.put("ADDRESS", listPd.get(i).getString("var8"));	    //9
				pd.put("FAX", listPd.get(i).getString("var9"));	    //10
				pd.put("PAGING", listPd.get(i).getString("var10"));	    //11
				pd.put("YICODE", listPd.get(i).getString("var11"));	    //12
				if(listPd.get(i).getString("var12")!=null){
					pd.put("DISTRIBUTIONMODE", listPd.get(i).getString("var12"));	    //13
				}else{
					pd.put("DISTRIBUTIONMODE", 1);	    //13
				}
				pd.put("CREDITDEGREE", listPd.get(i).getString("var13"));	    //14
				pd.put("OPENBANK", listPd.get(i).getString("var14"));	    //15
				pd.put("BANKACCOUNT", listPd.get(i).getString("var15"));	    //16
				pd.put("MAILCODE", listPd.get(i).getString("var16"));	    //17
				pd.put("EMAIL", listPd.get(i).getString("var17"));	    //18
				
				String PK_NAME=listPd.get(i).getString("var18");//18
				pd.put("PK_NAME", PK_NAME);
				String PK_ID=goodsService.findPKBYName(pd);
				if(PK_ID!=null){
					pd.put("PK_SOBOOKS",PK_ID);//	18
				}
				
			/*	pd.put("DEF1", listPd.get(i).getString("var19"));	    //20
*/				pd.put("NETADDR", listPd.get(i).getString("var19"));	    //21
				
				
				String username=listPd.get(i).getString("var20");//18
				pd.put("username", username);
				String userId = userService.findByname(pd);
				if(userId!=null){
					pd.put("USER_ID",userId );//	
				}
				
				pd.put("NOTE", listPd.get(i).getString("var21"));	    //23
				supplierService.saveSupplier(pd);
			}
			/*存入数据库操作======================================*/
			mv.addObject("msg","success");
		}
		mv.setViewName("save_result");
		return mv;
	}
	
	
	/**下载模版
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/downExcel")
	public void downExcel(HttpServletResponse response)throws Exception{
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "Supplier.xls", "Supplier.xls");
	}
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
