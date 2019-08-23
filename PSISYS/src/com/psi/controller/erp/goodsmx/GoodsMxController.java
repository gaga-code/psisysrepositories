package com.psi.controller.erp.goodsmx;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.psi.controller.base.BaseController;
import com.psi.entity.Page;
import com.psi.service.basedata.goods.GoodsManager;
import com.psi.service.information.pictures.PicturesManager;
import com.psi.util.AppUtil;
import com.psi.util.Base64Image;
import com.psi.util.Const;
import com.psi.util.DateUtil;
import com.psi.util.DelAllFile;
import com.psi.util.FileUpload;
import com.psi.util.Jurisdiction;
import com.psi.util.PageData;
import com.psi.util.PathUtil;
import com.psi.util.Tools;
import com.psi.util.Watermark;

import sun.misc.BASE64Decoder;

/**
 * 说明：商品管理(明细)
 */
@Controller
@RequestMapping(value="/goodsmx")
public class GoodsMxController extends BaseController {
	
	String menuUrl = "goodsmx/list.do"; //菜单地址(权限用)
	@Resource(name="picturesService")
	private PicturesManager picturesService;
	@Resource(name="goodsService")
	private GoodsManager goodsService;
	/**新增
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	@ResponseBody
	public Object save(
			@RequestParam(required=false) MultipartFile file,
			@RequestParam(value="MASTER_ID",required=false) String MASTER_ID
			) throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"新增图片");
		Map<String,String> map = new HashMap<String,String>();
		String  ffile = DateUtil.getDays(), fileName = "";
		PageData pd = new PageData();
		if(Jurisdiction.buttonJurisdiction(menuUrl, "add")){
			if (null != file && !file.isEmpty()) {
				String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile;		//文件上传路径/uploadFiles/uploadImgs/20190823
				fileName = FileUpload.fileUp(file, filePath, this.get32UUID());				//执行上传a89b818383414c77bfe6bfc655b4ffed.jpg
			}else{
				System.out.println("上传失败");
			}
			pd.put("PICTURES_ID", this.get32UUID());			//主键
			pd.put("TITLE", "商品图片");								//标题
			pd.put("NAME", fileName);							//文件名
			pd.put("PATH", ffile + "/" + fileName);				//路径
			pd.put("CREATETIME", Tools.date2Str(new Date()));	//创建时间
			pd.put("MASTER_ID", MASTER_ID);						//附属与
			pd.put("BZ", "商品图片");							//备注
			pd.put("ORDER_BY", 1);								//排序
			Watermark.setWatemark(PathUtil.getClasspath() + Const.FILEPATHIMG + ffile + "/" + fileName);//加水印
			picturesService.save(pd);
			goodsService.editPic(pd);
		}
		map.put("result", "ok");
		return AppUtil.returnObject(pd, map);
	}


	@RequestMapping(value = "/uploadData", method = {RequestMethod.POST}, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String uploadData(HttpServletRequest request) throws Exception {
	
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"新增图片");
		PageData pd = new PageData();
		
		String base64Image  = request.getParameter("base64Image"); //图base64编码字符串
		String pictureName  = request.getParameter("pictureName");
		String base64img = base64Image.substring(22, base64Image.length());//去掉base64前面22个字符 data:image/png;base64,是固定值 
		
		logger.info("图片的名称："+pictureName);
		logger.info(base64img);
		
		String  ffile = DateUtil.getDays(), fileName = "";
		String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile+"/";		//文件上传路径
		fileName=this.get32UUID()+".jpg";
		//保存图片路径
	  //  uploadFiles/uploadImgs/PATH
		
		logger.info(" 图片保存路径："+ filePath);
		
		//保存图片
		boolean bool = Base64Image.GenerateImage(base64img, filePath,fileName);
		if(bool){
			String MASTER_ID= request.getParameter("MASTER_ID");
			pd.put("PICTURES_ID", this.get32UUID());			//主键
			pd.put("TITLE", "商品图片");								//标题
			pd.put("NAME", fileName);							//文件名
			pd.put("PATH", ffile + "/" + fileName);				//路径
			pd.put("CREATETIME", Tools.date2Str(new Date()));	//创建时间
			pd.put("MASTER_ID", MASTER_ID);						//附属与
			pd.put("BZ", "商品图片");							//备注
			pd.put("ORDER_BY", 1);								//排序
			//Watermark.setWatemark(PathUtil.getClasspath() + Const.FILEPATHIMG + ffile + "/" + fileName);//加水印
			picturesService.save(pd);
			goodsService.editPic(pd);
			return "OK";
		}
		
		return "Error";
	

	}


	/**删除
	 * @param out
	 * @throws Exception 
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"删除图片");
		PageData pd = new PageData();
		if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){
			pd = this.getPageData();
			if(Tools.notEmpty(pd.getString("PATH").trim())){
				DelAllFile.delFolder(PathUtil.getClasspath()+ Const.FILEPATHIMG + pd.getString("PATH")); //删除图片
			}
			picturesService.delete(pd);
		}
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param request
	 * @param file
	 * @param tpz
	 * @param PICTURES_ID
	 * @param TITLE
	 * @param MASTER_ID
	 * @param BZ
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit(
			HttpServletRequest request,
			@RequestParam(value="tp",required=false) MultipartFile file,
			@RequestParam(value="tpz",required=false) String tpz,
			@RequestParam(value="PICTURES_ID",required=false) String PICTURES_ID,
			@RequestParam(value="TITLE",required=false) String TITLE,
			@RequestParam(value="MASTER_ID",required=false) String MASTER_ID,
			@RequestParam(value="ORDER_BY",required=false) int ORDER_BY,
			@RequestParam(value="BZ",required=false) String BZ
			) throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"修改图片");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
			pd.put("PICTURES_ID", PICTURES_ID);		//图片ID
			pd.put("TITLE", TITLE);					//标题
			pd.put("ORDER_BY", ORDER_BY);			//序号
			pd.put("MASTER_ID", MASTER_ID);			//属于ID
			pd.put("BZ", BZ);						//备注
			if(null == tpz){tpz = "";}
			String  ffile = DateUtil.getDays(), fileName = "";
			if (null != file && !file.isEmpty()) {
				String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile;	//文件上传路径
				fileName = FileUpload.fileUp(file, filePath, this.get32UUID());			//执行上传
				pd.put("PATH", ffile + "/" + fileName);									//路径
				pd.put("NAME", fileName);
				Watermark.setWatemark(PathUtil.getClasspath() + Const.FILEPATHIMG + ffile + "/" + fileName);//加水印
			}else{
				pd.put("PATH", tpz);
			}
			picturesService.edit(pd);				//执行修改数据库
			goodsService.editPic(pd);
		}
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
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String KEYW = pd.getString("keyword");	//检索条件
		if(null != KEYW && !"".equals(KEYW)){
			pd.put("KEYW", KEYW.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = picturesService.list(page);	//列出Pictures列表
		mv.setViewName("erp/goodsmx/goodsmx_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**列表(查询商品详情页)
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/viewList")
	public ModelAndView viewList(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		List<PageData>	varList = picturesService.list(page);	//列出Pictures列表
		mv.setViewName("erp/goodsmx/goodsmx_view_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		return mv;
	}
	

	/**去新增页面
	 * @return
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("erp/goodsmx/goodsmx_add");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**去修改页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = picturesService.findById(pd);	//根据ID读取
		mv.setViewName("erp/goodsmx/goodsmx_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**批量删除
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception {
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){
			List<PageData> pdList = new ArrayList<PageData>();
			List<PageData> pathList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				pathList = picturesService.getAllById(ArrayDATA_IDS);
				for(int i=0;i<pathList.size();i++){
					if(Tools.notEmpty(pathList.get(i).getString("PATH").trim())){
					DelAllFile.delFolder(PathUtil.getClasspath()+ Const.FILEPATHIMG + pathList.get(i).getString("PATH"));//删除图片
					}
				}
				picturesService.deleteAll(ArrayDATA_IDS);
				pd.put("msg", "ok");
			}else{
				pd.put("msg", "no");
			}
			pdList.add(pd);
			map.put("list", pdList);
			}
		return AppUtil.returnObject(pd, map);
	}
	
	/**删除图片
	 * @param out
	 * @throws Exception 
	 */
	@RequestMapping(value="/deltp")
	public void deltp(PrintWriter out) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		String PATH = pd.getString("PATH");													 		//图片路径
		if(Tools.notEmpty(pd.getString("PATH").trim())){
			DelAllFile.delFolder(PathUtil.getClasspath()+ Const.FILEPATHIMG + pd.getString("PATH")); 	//删除图片
		}
		if(PATH != null){
			picturesService.delTp(pd);																//删除数据库中图片数据
		}	
		out.write("success");
		out.close();
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
