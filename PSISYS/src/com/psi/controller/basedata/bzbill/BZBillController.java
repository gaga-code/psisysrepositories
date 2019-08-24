package com.psi.controller.basedata.bzbill;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.psi.controller.base.BaseController;
import com.psi.entity.Page;
import com.psi.service.basedata.bzbill.BZBillManager;
import com.psi.util.Const;
import com.psi.util.DateUtil;
import com.psi.util.FileUpload;
import com.psi.util.Jurisdiction;
import com.psi.util.PageData;
import com.psi.util.PathUtil;

@Controller
@RequestMapping("/bzbill")
public class BZBillController extends BaseController{
	
	String menuUrl = "bzbill/list.do"; //菜单地址(权限用)
	
	@Resource(name="bZBillService")
	private BZBillManager bZBillService;
	
	@RequestMapping(value="/list")
	public ModelAndView list() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"查看对账单");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = bZBillService.findByPK(pd);
		mv.addObject("pd", pd);
		mv.setViewName("basedata/bzbill/bzbill_list");
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改账单");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = bZBillService.findByPK(pd);
		mv.addObject("pd", pd);
		mv.addObject("msg", "edit");
		mv.setViewName("basedata/bzbill/bzbill_edit");
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	@RequestMapping(value="/edit")
	public ModelAndView edit(@RequestParam(required=false) MultipartFile file,String TITLE,
			String TELEPHONE,
			String ACCOUNTNAME,
			String ACCOUNTBANK, 
			String ACCOUNT , 
			String NOTE,
			String PICTURE) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改对账单");
	//	if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String  fileName = "";
		pd.put("PICTURE",PICTURE);
		if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
			if (null != file && !file.isEmpty()) {
				String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG ;		//文件上传路径/uploadFiles/uploadImgs/20190823
				fileName = FileUpload.fileUp(file, filePath, this.get32UUID());				//执行上传a89b818383414c77bfe6bfc655b4ffed.jpg
				pd.put("PICTURE", fileName);
			}else{
				System.out.println("上传失败");
			}
			pd.put("TITLE",TITLE);
			pd.put("TELEPHONE",TELEPHONE);
			pd.put("ACCOUNTNAME",ACCOUNTNAME);
			pd.put("ACCOUNTBANK",ACCOUNTBANK);
			pd.put("ACCOUNT",ACCOUNT);
			pd.put("NOTE",NOTE);
			bZBillService.edit(pd);
		}
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	
	
}
