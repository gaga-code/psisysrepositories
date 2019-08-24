package com.psi.controller.basedata.goodstype;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.shiro.session.Session;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.psi.service.basedata.goodstype.GoodsTypeManager;
import com.psi.service.system.fhlog.FHlogManager;
import com.psi.util.AppUtil;
import com.psi.util.Const;
import com.psi.util.FileDownload;
import com.psi.util.FileUpload;
import com.psi.util.Jurisdiction;
import com.psi.util.ObjectExcelRead;
import com.psi.util.ObjectExcelView;
import com.psi.util.PageData;
import com.psi.util.PathUtil;

/**
 * 说明：商品类型管理
 */
@Controller
@RequestMapping(value = "/goodstype")
public class GoodsTypeController extends BaseController {
	
	String menuUrl = "goodstype/listAllDict.do?GOODTYPE_ID=-2"; //菜单地址(权限用)
	@Resource(name="goodsTypeService")
	private GoodsTypeManager goodsTypeService;
	@Resource(name="fhlogService")
	private FHlogManager FHLOG;
	@Resource(name="goodsService")
	private GoodsManager goodsService;
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增GoodsType");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
	//	pd.put("GOODTYPE_ID", this.get32UUID());	//主键
		pd.put("GOODTYPE_ID", pd.get("TYPECODE"));	//主键
		if(pd.get("PARENTS") == null || pd.get("PARENTS").equals(""))
			pd.put("PARENTS", "-2");
		goodsTypeService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 * @param GOODTYPE_ID
	 * @param
	 * @throws Exception 
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object delete(@RequestParam String GOODTYPE_ID) throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"删除GOODTYPE");
		Map<String,String> map = new HashMap<String,String>();
		
		Session session = Jurisdiction.getSession();
		String PK_SOBOOKS = (String)session.getAttribute(Const.SESSION_PK_SOBOOKS);
		Map<String,String> parentIdAndPK_SOBOOKS = new HashMap<String, String>();
		parentIdAndPK_SOBOOKS.put("PK_SOBOOKS", PK_SOBOOKS);
		parentIdAndPK_SOBOOKS.put("PARENTS", GOODTYPE_ID);
		
		PageData pd = new PageData();
		pd.put("GOODTYPE_ID", GOODTYPE_ID);
		String errInfo = "success";
		if(goodsTypeService.listSubDictByParentId(parentIdAndPK_SOBOOKS).size() > 0){//判断是否有子级，是：不允许删除
			errInfo = "false";
		}
		if("success".equals(errInfo)){
			goodsTypeService.delete(pd);	//执行删除
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Dictionaries");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		goodsTypeService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表GOODTYPE");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");					//检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String GOODTYPE_ID = null == pd.get("GOODTYPE_ID")?"":pd.get("GOODTYPE_ID").toString();
		if(null != pd.get("id") && !"".equals(pd.get("id").toString())){
			GOODTYPE_ID = pd.get("id").toString();
		}
		pd.put("GOODTYPE_ID", GOODTYPE_ID);					//上级ID
		page.setPd(pd);
		List<PageData>	varList = goodsTypeService.list(page);	//列出Dictionaries列表
		mv.addObject("pd", goodsTypeService.findById(pd));		//传入上级所有信息
		mv.addObject("GOODTYPE_ID", GOODTYPE_ID);			//上级ID
		mv.setViewName("basedata/goodstype/goodstype_list");
		mv.addObject("varList", varList);
		mv.addObject("QX",Jurisdiction.getHC());					//按钮权限
		return mv;
	}
	
	/**
	 * 显示列表ztree
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/listAllDict")
	public ModelAndView listAllDict(Model model,String GOODTYPE_ID)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,String> map = new HashMap<String, String>();
			map.put("PK_SOBOOKS", pd.getString("PK_SOBOOKS"));
			map.put("PARENTS", "-2");
			JSONArray arr = JSONArray.fromObject(goodsTypeService.listAllDict(map));
			String json = arr.toString();
			json = json.replaceAll("GOODTYPE_ID", "id").replaceAll("PARENTS", "pId").replaceAll("TYPENAME", "name").replaceAll("subDict", "nodes").replaceAll("hasDict", "checked").replaceAll("treeurl", "url");
			model.addAttribute("zTreeNodes", json);
			mv.addObject("GOODTYPE_ID",GOODTYPE_ID);
			mv.addObject("pd", pd);	
			mv.setViewName("basedata/goodstype/goodstype_ztree");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
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
		String GOODTYPE_ID = null == pd.get("GOODTYPE_ID")?"":pd.get("GOODTYPE_ID").toString();
		pd.put("GOODTYPE_ID", GOODTYPE_ID);					//上级ID
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		pd.put("NAME", user.getNAME());	//用户名字作为经手人名字
		mv.addObject("pd",pd);		//传入上级所有信息
		mv.addObject("pds",goodsTypeService.findById(pd));		//传入上级所有信息
		mv.addObject("GOODTYPE_ID", GOODTYPE_ID);			//传入ID，作为子级ID用
		mv.setViewName("basedata/goodstype/goodstype_edit");
		mv.addObject("msg", "save");
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
		String GOODTYPE_ID = pd.getString("GOODTYPE_ID");
		pd = goodsTypeService.findById(pd);	//根据ID读取
		mv.addObject("pd", pd);					//放入视图容器
		pd.put("GOODTYPE_ID",pd.get("PARENTS").toString());			//用作上级信息
		mv.addObject("pds",goodsTypeService.findById(pd));				//传入上级所有信息
		mv.addObject("GOODTYPE_ID", pd.get("PARENTS").toString());	//传入上级ID，作为子ID用
		pd.put("GOODTYPE_ID",GOODTYPE_ID);							//复原本ID
		mv.setViewName("basedata/goodstype/goodstype_edit");
		mv.addObject("msg", "edit");
		return mv;
	}	

	/**判断编码是否存在
	 * @return
	 */
	@RequestMapping(value="/hasBianma")
	@ResponseBody
	public Object hasBianma(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(goodsTypeService.findByBianma(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	
	/**打开上传EXCEL页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goUploadExcel")
	public ModelAndView goUploadExcel()throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("basedata/goodstype/uploadexcel");
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
		pd = this.getPageData();
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		if (null != file && !file.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;								//文件上传路径
			String fileName =  FileUpload.fileUp(file, filePath, "goodsexcel");							//执行上传
			List<PageData> listPd = (List)ObjectExcelRead.readExcel(filePath, fileName, 1, 0, 0);		//执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
			/*存入数据库操作======================================*/
			pd.put("DR", 0);
			for(int i=0;i<listPd.size();i++){
				String TYPECODE= listPd.get(i).getString("var0"); 
				pd.put("GOODTYPE_ID",TYPECODE);		
				pd.put("TYPECODE", TYPECODE);// 0        
				if(goodsTypeService.findById(pd) != null){									
					continue;
				}

				
				pd.put("TYPENAME", listPd.get(i).getString("var1"));		//2
			/*	pd.put("PARENTS", listPd.get(i).getString("var2"));			//3
*/				
				String GOODTYPE_ID=TYPECODE.substring(0, TYPECODE.length()-4);
				pd.put("PARENTS", GOODTYPE_ID);

				goodsTypeService.saveGoodsType(pd);
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
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "GoodsType.xls", "GoodsType.xls");
	}
	
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		FHLOG.save(Jurisdiction.getUsername(), "导出商品分类信息到EXCEL");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			if(Jurisdiction.buttonJurisdiction(menuUrl, "cha")){
				Map<String,Object> dataMap = new HashMap<String,Object>();
				List<String> titles = new ArrayList<String>();
				titles.add("商品分类编号"); 		//1
				titles.add("商品分类名称");  		//2
				titles.add("商品父类编号");			//3
				dataMap.put("titles", titles);
				List<PageData> goodsList = goodsTypeService.listAll(pd);
				List<PageData> varList = new ArrayList<PageData>();
				for(int i=0;i<goodsList.size();i++){
					PageData vpd = new PageData();
					vpd.put("var1", goodsList.get(i).getString("TYPECODE"));		//1
					vpd.put("var2", goodsList.get(i).getString("TYPENAME"));		//2
					vpd.put("var3", goodsList.get(i).getString("PARENTS"));			//3
					varList.add(vpd);
				}
				dataMap.put("varList", varList);
				ObjectExcelView erv = new ObjectExcelView();					//执行excel操作
				mv = new ModelAndView(erv,dataMap);
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	
}
