package com.psi.controller.app.inventorymanagement.stock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.psi.controller.base.BaseController;
import com.psi.service.app.inventorymanagement.tock.AppStockManager;
import com.psi.util.PageData;

@Controller
@RequestMapping("appStock")
public class AppStockController extends BaseController{

	@Resource
	private AppStockManager appStockService;
	
	@RequestMapping("/getStockById")//根据商品编号查询每个库的库存
	public List<PageData> listStockById() throws Exception{
		PageData pd=new PageData();
		pd=this.getPageData();
		List<PageData> list=appStockService.listStockById(pd);
		return list;
	}

	// 获取今日商品库存预警信息 预断货品数 
	@RequestMapping("/getGoosWarningDown")
	@ResponseBody
	public Map<String,Object> getGoosWarningDown( HttpServletRequest request) throws Exception{
		PageData pd = new PageData();
		pd=this.getPageData();

		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/uploadFiles/uploadImgs/";
		pd.put("pageNum", Integer.valueOf(pd.getString("pageNum"))*10);
		int TOTALNUM=appStockService.listGoodsDownNums(pd);
		List<PageData> list=appStockService.listGoodsDownNum(pd);//查询商品库存不足
		if(list!=null){
			for(int i=0;i<list.size();i++){
				list.get(i).put("Path", basePath+list.get(i).getString("GOODPIC"));
			}
		}
		HashMap<String,Object> map=new HashMap();
		map.put("list", list);
		map.put("TOTALNUM", TOTALNUM);
		return map;
	}
	
	// 获取今日商品库存预警信息积压货品数
		@RequestMapping("/getGoosWarningUpdate")
		@ResponseBody
		public Map<String,Object> getGoosWarning( HttpServletRequest request) throws Exception{
			PageData pd = new PageData();
			pd=this.getPageData();

			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/uploadFiles/uploadImgs/";
			int pageNum= Integer.valueOf(pd.getString("pageNum"));
			pd.put("pageNum", (pageNum-1)*10);
			int TOTALNUM=appStockService.listGoodsUpDateNum(pd);
			List<PageData> list=appStockService.listGoodsUpDate(pd);//查询积压过久的商品
			if(list!=null){
				for(int i=0;i<list.size();i++){
					list.get(i).put("Path", basePath+list.get(i).getString("GOODPIC"));
				}
			}
			HashMap<String,Object> map=new HashMap();
			map.put("list", list);
			map.put("TOTALNUM", TOTALNUM);
			return map;
		}
		
	
	
}
