package com.psi.controller.app.basedata.goodstype;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.psi.controller.base.BaseController;
import com.psi.service.app.basedata.goodstype.AppGoodsTypeManager;
import com.psi.util.PageData;

@Controller
@RequestMapping("/appGoodsType")
public class AppGoodsTypeController extends BaseController{

	@Resource(name="appGoodsTypeService")
	private AppGoodsTypeManager appGoodsTypeService;
	
	// 获取商品分类名称
	@RequestMapping("/getGoodsClassList")
	@ResponseBody
	public List<String> getGoodsClassList() throws Exception{
		PageData pd=new PageData();
		pd=this.getPageData();
		List<String> list=appGoodsTypeService.listGoodsClass(pd);
		return list;
	}
	
}
