package com.psi.controller.basedata.goodstype;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import org.springframework.web.servlet.ModelAndView;

import com.psi.controller.base.BaseController;
import com.psi.entity.Page;
import com.psi.service.basedata.goodstype.GoodsTypeManager;
import com.psi.util.AppUtil;
import com.psi.util.Const;
import com.psi.util.Jurisdiction;
import com.psi.util.PageData;

/**
 * 说明：商品类型管理
 */
@Controller
@RequestMapping(value = "/goodstype")
public class GoodsTypeController extends BaseController {
	
	String menuUrl = "goodstype/listAllDict.do?GOODTYPE_ID=0"; //菜单地址(权限用)
	@Resource(name="goodsTypeService")
	private GoodsTypeManager goodsTypeService;
	
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
		pd.put("GOODTYPE_ID", this.get32UUID());	//主键
		if(pd.get("PARENTS") == null || pd.get("PARENTS").equals(""))
			pd.put("PARENTS", "0");
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
			map.put("PARENTS", "0");
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
}
