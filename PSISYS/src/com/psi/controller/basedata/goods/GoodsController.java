package com.psi.controller.basedata.goods;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpRequest;
import org.apache.shiro.crypto.hash.SimpleHash;
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
import com.psi.entity.system.Role;
import com.psi.entity.system.User;
import com.psi.service.basedata.goods.GoodsManager;
import com.psi.service.basedata.goodstype.GoodsTypeManager;
import com.psi.service.basedata.supplier.SupplierManager;
import com.psi.service.basedata.warehouse.WarehouseManager;
import com.psi.service.erp.spbrand.SpbrandManager;
import com.psi.service.erp.sptype.SptypeManager;
import com.psi.service.erp.spunit.SpunitManager;
import com.psi.service.information.pictures.PicturesManager;
import com.psi.service.inventorymanagement.salebill.SalebillManager;
import com.psi.service.inventorymanagement.stock.StockManager;
import com.psi.service.system.fhlog.FHlogManager;
import com.psi.service.system.user.UserManager;
import com.psi.util.AppUtil;
import com.psi.util.BarcodeUtil;
import com.psi.util.Const;
import com.psi.util.FileDownload;
import com.psi.util.FileUpload;
import com.psi.util.GetPinyin;
import com.psi.util.Jurisdiction;
import com.psi.util.ObjectExcelRead;
import com.psi.util.ObjectExcelView;
import com.psi.util.PageData;
import com.psi.util.PathUtil;
import com.psi.util.Tools;
import com.psi.util.TwoDimensionCode;

import net.sf.json.JSONArray;

/**
 * 说明：商品管理
 */
@Controller
@RequestMapping(value="/goods")
public class GoodsController extends BaseController {
	
	String menuUrl = "goods/list.do"; //菜单地址(权限用)
	@Resource(name="goodsService")
	private GoodsManager goodsService;
	@Resource(name="picturesService")
	private PicturesManager picturesService;
	@Resource(name="spbrandService")
	private SpbrandManager spbrandService;
	@Resource(name="sptypeService")
	private SptypeManager sptypeService;
	@Resource(name="spunitService")
	private SpunitManager spunitService;
	@Resource(name="goodsTypeService")
	private GoodsTypeManager goodsTypeService;
	@Resource(name="supplierService")
	private SupplierManager supplierService;
	@Resource(name="warehouseService")
	private WarehouseManager warehouseService;
	@Resource(name="fhlogService")
	private FHlogManager FHLOG;
	@Resource(name="userService")
	private UserManager userService;
	@Resource(name="salebillService")
	private SalebillManager salebillService;
	@Resource(name="stockService")
	private StockManager stockService;
	/**
	 * 库存预警
	 * 检查商品的库存是否低于下限
	 * 返回低于下限的商品的列表  包括字段： 名称、编号、当前库存、库存下限
	 */
	@RequestMapping(value="/check_goods_stock_down_num")
	@ResponseBody
	public List<PageData> checkGoodsStockDownNum() throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		return goodsService.checkGoodsStockDownNum(pd);
	}
	
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Goods");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("GOOD_ID", this.get32UUID());	//主键 
		//库存
		if(pd.get("STOCKNUM") == null || pd.get("STOCKNUM").equals(""))
			pd.put("STOCKNUM", 0);	
		pd.put("WAREHOUSE_IDs", pd.getString("wh"));
		goodsService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object delete() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除Goods");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null ;} //校验权限
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String errInfo = "success";
		//当商品下面有图片 或者 此商品已经上架 或者 库存不为0时 不能删除
//		if(Integer.parseInt(picturesService.findCount(pd).get("zs").toString()) > 0 || Integer.parseInt( goodsService.findById(pd).get("ZCOUNT").toString()) > 0){
//			errInfo = "false";
//		}else{
//			goodsService.delete(pd);
//		}
		//无条件删除
		goodsService.delete(pd);
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Goods");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		goodsService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**二维码页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/erweima")
	public ModelAndView erweima() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String encoderImgId = pd.getString("GOOD_ID")+".png"; //encoderImgId此处二维码的图片名
		String filePath = PathUtil.getClasspath() + Const.FILEPATHTWODIMENSIONCODE + encoderImgId; 		//存放路径
		TwoDimensionCode.encoderQRCode(pd.getString("url"), filePath, "png");							//执行生成二维码
		mv.setViewName("basedata/goods/goods_erweima");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**条形码页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/barcode")
	public ModelAndView barcode() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String barcodeImgId = pd.getString("GOOD_ID")+".png"; 									//barcodeImgId此处条形码的图片名
		String filePath = PathUtil.getClasspath() + Const.FILEPATHTBARCODE + barcodeImgId; 		//存放路径
		BarcodeUtil.generateFile(pd.getString("BIANMA"), filePath);								//执行生成条形码
		mv.setViewName("basedata/goods/goods_barcode");
		mv.addObject("pd", pd);
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
			JSONArray arr = JSONArray.fromObject(goodsService.listAllDict(map));
			String json = arr.toString();
			json = json.replaceAll("GOODTYPE_ID", "id").replaceAll("PARENTS", "pId").replaceAll("TYPENAME", "name").replaceAll("subDict", "nodes").replaceAll("hasDict", "checked").replaceAll("treeurl", "url");
			model.addAttribute("zTreeNodes", json);
			mv.addObject("GOODTYPE_ID",GOODTYPE_ID);
			mv.addObject("pd", pd);	
			mv.setViewName("basedata/goods/goods_ztree");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Goods");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		pd.put("GOODTYPE_ID", pd.get("id"));
		pd.put("USERNAME", "admin".equals(Jurisdiction.getUsername())?"":Jurisdiction.getUsername());
		page.setPd(pd);
		List<PageData>	varList = goodsService.list(page);	//列出Goods列表
		List<PageData> spunitList = spunitService.listAll(Jurisdiction.getUsername()); 		//计量单位列表
		mv.setViewName("basedata/goods/goods_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("spunitList", spunitList);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表Goods");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> varList = warehouseService.listAll(pd);	//列出supplier列表
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
		List<PageData> supplierList = supplierService.listAll(pd);  //供应商列表
		List<PageData> goodsType =  goodsTypeService.listAll(pd); 						//商品分类列表
		List<PageData> spunitList = spunitService.listAll(Jurisdiction.getUsername()); 		//计量单位列表
		List<PageData> goodsTypeList = new ArrayList<PageData>();
		for(int i=0;i<goodsType.size();i++){
			int flag=0;
			for(int j=0;j<goodsType.size();j++){
				if(goodsType.get(i).get("GOODTYPE_ID").equals(goodsType.get(j).get("PARENTS"))){
					flag=1;
					break;
				}
			}
			if(flag==0){
				goodsTypeList.add(goodsType.get(i));
			}
		}
		mv.setViewName("basedata/goods/goods_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		mv.addObject("supplierList", supplierList);
		mv.addObject("spunitList", spunitList);
		mv.addObject("goodsTypeList", goodsTypeList);
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
		pd = goodsService.findById(pd);	//根据ID读取
		List<PageData> supplierList = supplierService.listAll(pd);  //供应商列表
		List<PageData> goodsType =  goodsTypeService.listAll(pd); 						//商品分类列表
		List<PageData> spunitList = spunitService.listAll(Jurisdiction.getUsername()); 		//计量单位列表
		List<PageData> goodsTypeList = new ArrayList<PageData>();
		for(int i=0;i<goodsType.size();i++){
			int flag=0;
			for(int j=0;j<goodsType.size();j++){
				if(goodsType.get(i).get("GOODTYPE_ID").equals(goodsType.get(j).get("PARENTS"))){
					flag=1;
					break;
				}
			}
			if(flag==0){
				goodsTypeList.add(goodsType.get(i));
			}
		}
		mv.setViewName("basedata/goods/goods_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		mv.addObject("supplierList", supplierList);
		mv.addObject("spunitList", spunitList);
		mv.addObject("goodsTypeList", goodsTypeList);
		return mv;
	}
	
	 /**去查看商品页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goView")
	public ModelAndView goView()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = goodsService.findByIdToCha(pd);	//根据ID读取
		mv.setViewName("basedata/goods/goods_view");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**通过产品编码
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/getByBm")
	@ResponseBody
	public Object getByBm() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"通过产品编码获取信息");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String errInfo = "success";
		pd.put("USERNAME", Jurisdiction.getUsername());	//用户名
		List<PageData> goodsList = goodsService.listByBm(pd);
		if(goodsList.size()>0){
			pd = goodsList.get(0);	//商品编码可重复,只取库存大的
			map.put("pd", pd);
		}else{
			errInfo = "errer"; 		//此用户此编码下无商品
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	

	
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		FHLOG.save(Jurisdiction.getUsername(), "导出用户信息到EXCEL");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			if(Jurisdiction.buttonJurisdiction(menuUrl, "cha")){
			
				Map<String,Object> dataMap = new HashMap<String,Object>();
				List<String> titles = new ArrayList<String>();
				titles.add("商品编号"); 		//1
				titles.add("商品名称");  		//2
				titles.add("条码");			//3
				titles.add("简称");			//4
				titles.add("品牌");			//5
				titles.add("所属柜组");			//6
				titles.add("商品规格");		//7
				titles.add("商品型号");	//8
				titles.add("计量单位"); //9
				titles.add("辅助单位");  //10
				titles.add("单位比例");   //11
				titles.add("主供应商");   //12
				titles.add("最后进价");   //13
				titles.add("成本价");   //14
				titles.add("零售价");   //15
				titles.add("会员价");   //16
				titles.add("库存数量");   //17
				titles.add("库存上限");   //18
				titles.add("库存下限");   //19
				titles.add("拼音编码");  //20
				titles.add("最后辅助单位进价"); //21
				titles.add("辅助单位零售价"); //22
				titles.add("加权平均价");//23
				titles.add("备注");//23
				titles.add("帐套");//24
				titles.add("经手人");//25
				titles.add("商品分类编号");//26
				titles.add("仓库");//27
				dataMap.put("titles", titles);
				List<PageData> goodsList = goodsService.listAllDetail(pd);
				List<PageData> varList = new ArrayList<PageData>();
				for(int i=0;i<goodsList.size();i++){
					PageData vpd = new PageData();
					vpd.put("var1", goodsList.get(i).getString("GOODCODE"));		//1
					vpd.put("var2", goodsList.get(i).getString("GOODNAME"));		//2
					vpd.put("var3", goodsList.get(i).getString("BARCODE"));			//3
					vpd.put("var4", goodsList.get(i).getString("SIMPLENAME"));	//4
					vpd.put("var5", goodsList.get(i).getString("BRANT"));		//5
					vpd.put("var6", goodsList.get(i).getString("SUBGZ_ID"));		//6
					vpd.put("var7", goodsList.get(i).getString("GOODSPECIF"));	//7
					vpd.put("var8", goodsList.get(i).getString("GOODTYPECODE"));		//8
					vpd.put("var9", goodsList.get(i).getString("UNITNAME"));//	9	
					vpd.put("var10", goodsList.get(i).getString("FZUNITNAME"));//	10	
					vpd.put("var11", goodsList.get(i).getString("UNITPROP"));//		11
					vpd.put("var12", goodsList.get(i).getString("SUPPLIERNAME"));//		12
					vpd.put("var13", goodsList.get(i).get("LASTPPRICE").toString());//		13
					vpd.put("var14", goodsList.get(i).get("CPRICE").toString());//		14
					vpd.put("var15", goodsList.get(i).get("RPRICE").toString());//		15
					vpd.put("var16", goodsList.get(i).get("MPRICE").toString());//		16
					vpd.put("var17", goodsList.get(i).get("STOCKNUM").toString());//		17
					vpd.put("var18", goodsList.get(i).get("STOCKUPNUM").toString());//		18
					vpd.put("var19", goodsList.get(i).get("STOCKDOWNNUM").toString());//		19
					vpd.put("var20", goodsList.get(i).get("YICODE").toString());//	20
					vpd.put("var21", goodsList.get(i).get("LFZUPPRICE").toString());//21	
					vpd.put("var22", goodsList.get(i).get("FZUCPRICE").toString());//	22
					vpd.put("var23", goodsList.get(i).getString("DEF1"));
					vpd.put("var24", goodsList.get(i).getString("NOTE"));//	23
					vpd.put("var25", goodsList.get(i).getString("ENTERPRISENAME"));//24	
					vpd.put("var25", goodsList.get(i).getString("NAME"));//	25
					vpd.put("var27", goodsList.get(i).getString("GOODTYPE_ID"));//26	
					
					String  wid=goodsList.get(i).getString("WAREHOUSE_IDs");//27
					String[] str=wid.split(",");
					String whname="";
					for(int j=0;j<str.length;j++){
						PageData lpd=new PageData();
						lpd.put("wid", str[j]);
						if(whname.equals("")){
							whname=warehouseService.findByWid(lpd);	
						}else{
							whname+=","+warehouseService.findByWid(lpd);
						}
					}
					vpd.put("var27", whname);//	
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
	
	
	/**打开上传EXCEL页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goUploadExcel")
	public ModelAndView goUploadExcel()throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("basedata/goods/uploadexcel");
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
				pd.put("GOOD_ID", this.get32UUID());		
				pd.put("GOODCODE", listPd.get(i).getString("var0"));// 0        
				if(goodsService.findByCode(pd) != null){									
					continue;
				}
				pd.put("GOODNAME", listPd.get(i).getString("var1"));		//2
				pd.put("BARCODE", listPd.get(i).getString("var2"));			//3
				pd.put("SIMPLENAME", listPd.get(i).getString("var3"));	//4
				pd.put("BRANT", listPd.get(i).getString("var4"));		//5
				pd.put("SUBGZ_ID", listPd.get(i).getString("var5"));		//6
				pd.put("GOODSPECIF", listPd.get(i).getString("var6"));	//7
				pd.put("GOODTYPECODE", listPd.get(i).getString("var7"));		//8
				
				String uname=listPd.get(i).getString("var8"); //9
				pd.put("name", uname);
				String danweiId=goodsService.findByname(pd);
				if(danweiId==null){
					PageData upd=new PageData();
					upd.put("name", uname);
					upd.put("Id", this.get32UUID());
					goodsService.saveUnit(upd);
					pd.put("CUNIT_ID", upd.getString("Id"));
				}else{
					pd.put("CUNIT_ID", danweiId);	//9
				}	
				
				
				String fname=listPd.get(i).getString("var9"); //10
				pd.put("name", fname);
				String fudanweiId=goodsService.findByname(pd);
				if(fudanweiId==null){
					PageData upd=new PageData();
					upd.put("name", fname);
					upd.put("Id", this.get32UUID());
					goodsService.saveUnit(upd);
					pd.put("FZUNIT_ID", upd.getString("Id"));
				}else{
					pd.put("FZUNIT_ID", fudanweiId);	//10
				}
				
				pd.put("UNITPROP", listPd.get(i).getString("var10"));//		11
				
				String supplierName=listPd.get(i).getString("var11");
				pd.put("supplierName", supplierName);
				String supplierId = supplierService.findByName(pd);
				pd.put("SUPPLIER_ID",supplierId);//		12
				
				pd.put("LASTPPRICE", listPd.get(i).get("var12"));//		13
				pd.put("CPRICE", listPd.get(i).get("var13"));//		14
				pd.put("RPRICE", listPd.get(i).get("var14"));//		15
				pd.put("MPRICE", listPd.get(i).get("var15"));//		16
				pd.put("STOCKNUM", listPd.get(i).get("var16"));//		17
				pd.put("STOCKUPNUM", listPd.get(i).get("var17"));//		18
				
				if(listPd.get(i).getString("var18").equals("")){
					pd.put("STOCKDOWNNUM", 0);//		19
				}else{
					pd.put("STOCKDOWNNUM", listPd.get(i).get("var18"));//		19
				}
				pd.put("YICODE", listPd.get(i).get("var19").toString());//	20
				pd.put("LFZUPPRICE", listPd.get(i).get("var20"));//	21
				pd.put("FZUCPRICE", listPd.get(i).get("var21"));//	22
				pd.put("DEF1", listPd.get(i).getString("var22"));//	23
				pd.put("NOTE", listPd.get(i).getString("var23"));//	23
				
				String PK_NAME=listPd.get(i).getString("var24");//24
				pd.put("PK_NAME", PK_NAME);
				String PK_ID=goodsService.findPKBYName(pd);
				if(PK_ID!=null){
					pd.put("PK_SOBOOKS",PK_ID);//	24
				}
				String username=listPd.get(i).getString("var25");//25
				pd.put("username", username);
				String userId = userService.findByname(pd);
				if(userId!=null){
					pd.put("USER_ID",userId );//	
				}
		/*		
				String goodType= listPd.get(i).getString("var25");
				pd.put("goodType", goodType);
				String goodTypeId=goodsTypeService.findByname(pd);
				pd.put("GOODTYPE_ID", goodTypeId);//	
				
				
*/				String GOODCODE = listPd.get(i).getString("var0");
				String GOODTYPE_ID=GOODCODE.substring(0, GOODCODE.length()-4);
				pd.put("GOODTYPE_ID", GOODTYPE_ID);

				String whname= listPd.get(i).getString("var27");
				if(whname!=null&&!whname.equals("")){
					String whId="";
					String[] str=whname.split(",");
					for(int j=0;j<str.length;j++){
						pd.put("whName", str[j]);
						if(whId.equals("")){
							whId=warehouseService.findByName(pd);
						}else{
							whId+=","+warehouseService.findByName(pd);
						}
					}
					pd.put("WAREHOUSE_IDs", whId);
				}else{
					pd.put("WAREHOUSE_IDs", null);
				}
				goodsService.saveGoods(pd);
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
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "Goods.xls", "Goods.xls");
	}
	
	
	@RequestMapping(value="/findCodeByGOODTYPEID")
	@ResponseBody
	public String findCodeByGOODTYPEID() throws Exception{	
		PageData pd = new PageData();
		pd = this.getPageData();
		int len=pd.getString("GOODTYPE_ID").length();
		String code =goodsService.findbyTypeId(pd);
		
	

		if(code!=null){
		String code1=code.substring(len, code.length());
			String ling="";
			for(int i=0;i<code1.length();i++){
				if(code1.charAt(i)!='0'){
					break;
				}else{
					ling+="0";
				}
			}
		
			return pd.getString("GOODTYPE_ID")+ling+(Integer.parseInt(code1)+1);
		}
	
		return pd.getString("GOODTYPE_ID")+"01";
	}
	
	
	
	@RequestMapping(value="/listsalebill")
	public ModelAndView listsalebill(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"查看商品销售情况列表");
	
		PageData pd = new PageData();
		pd= this.getPageData();
		

		ModelAndView mv= new ModelAndView();
		
		String keywords = pd.getString("keywords");				//关键词检索条件
		if( keywords !=null && !keywords.equals("")){
			pd.put("keywords", keywords.trim());
		}
		String lastLoginStart = pd.getString("lastStart");	//开始时间
		String lastLoginEnd = pd.getString("lastEnd");		//结束时间
		if(lastLoginStart != null && !"".equals(lastLoginStart)){
			pd.put("lastStart", lastLoginStart+" 00:00:00");
		}
		if(lastLoginEnd != null && !"".equals(lastLoginEnd)){
			pd.put("lastEnd", lastLoginEnd+" 23:59:59");
		} 
		pd.put("USERNAME", Jurisdiction.getUsername());
		page.setPd(pd);
		List<PageData> list;
		list=salebillService.listsalebillByGoodCode(page);//列出销售单列表
		mv.setViewName("inventorymanagement/odersale/salebill_view");
		
		mv.addObject("pd", pd);
		mv.addObject("varlist",list);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	

	
	@RequestMapping(value="/goInorderSale")
	public ModelAndView goInorderSale(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"查看商品库存进出列表");
		PageData pd = new PageData();
		pd= this.getPageData();
		ModelAndView mv= new ModelAndView();
		page.setPd(pd);
		List<PageData> list = goodsService.listAllInorderSale(page); //获取商品的加减
		
		mv.addObject("varlist", list);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		mv.setViewName("inventorymanagement/stock/odersale_list");
		return mv;
	}
}
