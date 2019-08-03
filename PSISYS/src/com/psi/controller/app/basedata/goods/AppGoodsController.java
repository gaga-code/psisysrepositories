package com.psi.controller.app.basedata.goods;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.psi.controller.base.BaseController;
import com.psi.service.app.basedata.goods.AppGoodsManager;
import com.psi.service.app.inventorymanagement.salebill.AppSalebillManager;
import com.psi.service.app.inventorymanagement.tock.AppStockManager;
import com.psi.service.erp.spbrand.SpbrandManager;
import com.psi.service.erp.sptype.SptypeManager;
import com.psi.service.erp.spunit.SpunitManager;
import com.psi.service.information.pictures.PicturesManager;
import com.psi.service.system.fhlog.FHlogManager;
import com.psi.util.Jurisdiction;
import com.psi.util.PageData;


/**
  * 商品信息-接口类 
  * 相关参数协议：
  * 00	请求失败
  * 01	请求成功
  * 02	返回空值
  * 03	请求协议参数不完整    
  * 04  用户名或密码错误
  * 05  FKEY验证失败
 */
@Controller
@RequestMapping(value="/appGoods")
public class AppGoodsController extends BaseController {
    
	
	@Resource(name="appGoodsService")
	private AppGoodsManager appGoodsService;

	@Resource(name = "fhlogService")
	private FHlogManager FHLOG;
	
	@Resource(name="appStockService")
	private AppStockManager appStockService;
	
	@Resource(name="appSalebillService")
	private AppSalebillManager appSalelbillService;
	
	//获取商品表
	@RequestMapping("/getGoodsList")
	@ResponseBody
	public Object getGoodsList() throws Exception{
		PageData pd=new PageData();
		pd=this.getPageData();
		
		List<PageData> lists=appGoodsService.listGoods(pd); //获取商品信息
		for(int i=0;i<lists.size();i++){
			String GOODCODE=lists.get(i).getString("GOODCODE");
			pd.put("GOODCODE", GOODCODE);
			List<PageData> fpd=appStockService.listStockById(pd); //获取商品在不同库存的数量
			/*for(int j=0;j<fpd.size();j++){
				String PostionNum=fpd.get(i).getString("WHNAME")+"的库存数量："+fpd.get(i).get("STOCK");
				lists.get(i).put("PostionNum", PostionNum);
			}*/
			lists.get(i).put("stockNum", fpd);
			pd.put("PK_SOBOOKS", fpd.get(0).getString("PK_SOBOOKS"));
			pd.put("GOODCODE", GOODCODE);
			
			List<PageData> lpd=appSalelbillService.listDataAndNumAndPrice(pd); //获取商品最近五次销售信息
			if(lpd!=null&&lpd.size()!=0){
			     lists.get(i).put("PNDlist",lpd);
			}else{
			     lists.get(i).put("PNDlist","");
			}
		}
		
		return lists;
	}
	
	//根据类别搜索商品信息
	@RequestMapping("/getGoodsListByClass")
	@ResponseBody
	public Object getGoodsListByClass() throws Exception{
		PageData pd=new PageData();
		pd=this.getPageData();
		
		List<PageData> lists=appGoodsService.listGoodsListByClass(pd);//获取商品信息
		for(int i=0;i<lists.size();i++){
			String GOODCODE=lists.get(i).getString("GOODCODE");
			pd.put("GOODCODE", GOODCODE);
			List<PageData> fpd=appStockService.listStockById(pd); //获取商品在不同库存的数量
			/*for(int j=0;j<fpd.size();j++){
				String PostionNum=fpd.get(i).getString("WHNAME")+"的库存数量："+fpd.get(i).get("STOCK");
				lists.get(i).put("PostionNum", PostionNum);
			}*/
			lists.get(i).put("stockNum", fpd);
			pd.put("PK_SOBOOKS", fpd.get(0).getString("PK_SOBOOKS"));
			pd.put("GOODCODE", GOODCODE);
			
			List<PageData> lpd=appSalelbillService.listDataAndNumAndPrice(pd); //获取商品最近五次销售信息
			if(lpd!=null&&lpd.size()!=0){
			     lists.get(i).put("PNDlist",lpd);
			}else{
			     lists.get(i).put("PNDlist","");
			}
		}
		return lists;
	}
	
	//根据名称搜索商品信息
		@RequestMapping("/getGoodsListByName")
		@ResponseBody
		public Object getGoodsListByName() throws Exception{
			PageData pd=new PageData();
			pd=this.getPageData();
			
			List<PageData> lists=appGoodsService.listGoodsListByName(pd);//获取商品信息
			for(int i=0;i<lists.size();i++){
				String GOODCODE=lists.get(i).getString("GOODCODE");
				pd.put("GOODCODE", GOODCODE);
				List<PageData> fpd=appStockService.listStockById(pd);//获取商品在不同库存的数量
				/*for(int j=0;j<fpd.size();j++){
					String PostionNum=fpd.get(i).getString("WHNAME")+"的库存数量："+fpd.get(i).get("STOCK");
					lists.get(i).put("PostionNum", PostionNum);
				}*/
				lists.get(i).put("stockNum", fpd);
				pd.put("PK_SOBOOKS", fpd.get(0).getString("PK_SOBOOKS"));
				pd.put("GOODCODE", GOODCODE);
				
				List<PageData> lpd=appSalelbillService.listDataAndNumAndPrice(pd);//获取商品最近五次销售信息
				if(lpd!=null&&lpd.size()!=0){
				     lists.get(i).put("PNDlist",lpd);
				}else{
				     lists.get(i).put("PNDlist","");
				}
			}
			return lists;
		}
		
	//编辑商品图片路径
	@RequestMapping("/editGoodsPhoto")
	@ResponseBody
	public String editGoodsPhoto(){
		
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
		appGoodsService.editGoodsPhoto(pd);
		}catch(Exception e){
			return "Error";
		}
		return "OK";
	}
	

	/*@Resource(name="goodsService")
	private GoodsManager goodsService;
	@Resource(name="picturesService")
	private PicturesManager picturesService;
	@Resource(name="spbrandService")
	private SpbrandManager spbrandService;
	@Resource(name="sptypeService")
	private SptypeManager sptypeService;
	@Resource(name="spunitService")
	private SpunitManager spunitService;
	
	 *//**商品详情页面
	 * @param
	 * @throws Exception
	 * http://127.0.0.1:8080/FHSHGL/appGoods/goods.do?GOODS_ID=284e2238d7fc4bd481c6324fc4c160cf
	 *//*
	@RequestMapping(value="/goods")
	public ModelAndView goView()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData>	varList = picturesService.listAll(pd);	//列出Pictures列表
		pd = goodsService.findByIdToCha(pd);					//根据ID读取
		mv.setViewName("erp/goods/goods_app_view");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		return mv;
	}*/
	

	
}
	
