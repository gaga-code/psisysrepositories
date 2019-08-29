package com.psi.controller.app.basedata.goods;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.psi.controller.base.BaseController;
import com.psi.service.app.basedata.goods.AppGoodsManager;
import com.psi.service.app.inventorymanagement.salebill.AppSalebillManager;
import com.psi.service.app.inventorymanagement.tock.AppStockManager;
import com.psi.service.basedata.goods.GoodsManager;
import com.psi.service.erp.spbrand.SpbrandManager;
import com.psi.service.erp.sptype.SptypeManager;
import com.psi.service.erp.spunit.SpunitManager;
import com.psi.service.information.pictures.PicturesManager;
import com.psi.service.system.fhlog.FHlogManager;
import com.psi.util.Base64Image;
import com.psi.util.Const;
import com.psi.util.DateUtil;
import com.psi.util.ImageAnd64Binary;
import com.psi.util.Jurisdiction;
import com.psi.util.PageData;
import com.psi.util.PathUtil;
import com.psi.util.Tools;


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
	@Resource(name="picturesService")
	private PicturesManager picturesService;
	@Resource(name="goodsService")
	private GoodsManager goodsService;
	@Resource(name = "fhlogService")
	private FHlogManager FHLOG;
	
	@Resource(name="appStockService")
	private AppStockManager appStockService;
	
	@Resource(name="appSalebillService")
	private AppSalebillManager appSalelbillService;
	

	//获取商品表
	@RequestMapping("/getGoodsList")
	@ResponseBody
	public HashMap<String,Object> getGoodsList( HttpServletRequest request) throws Exception{
		PageData pd=new PageData();
		pd=this.getPageData();

		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/uploadFiles/uploadImgs/";
		
		HashMap<String,Object> map =  new HashMap();
		Session session = Jurisdiction.getSession();
		Object PK_SOBOOKS;
		if(pd.getString("PK_SOBOOKS")!=null){
		    PK_SOBOOKS=pd.getString("PK_SOBOOKS");
		}else{
		     PK_SOBOOKS=session.getAttribute("PK_SOBOOKS");
		}
		int pageNum= Integer.valueOf(pd.getString("pageNum"));
		int pageSize= Integer.valueOf(pd.getString("pageSize"));
		pd.put("pageNum", (pageNum-1)*10);
		pd.put("pageSize", pageSize);
		Double TOTALNUM = appGoodsService.findAll(pd);
		map.put("TOTALNUM", TOTALNUM);
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
			pd.put("PK_SOBOOKS", PK_SOBOOKS);
			pd.put("GOODCODE", GOODCODE);
			lists.get(i).put("Path", basePath+lists.get(i).getString("GOODPIC"));
			List<PageData> lpd=appSalelbillService.listDataAndNumAndPrice(pd); //获取商品最近五次销售信息
			if(lpd!=null&&lpd.size()!=0){
				  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				for(int j =0;j<lpd.size();j++){
					String d=formatter.format(lpd.get(j).get("CREATETIME"));
					lpd.get(j).put("CREATETIME", d);
				}
			     lists.get(i).put("PNDlist",lpd);
			}else{
			     lists.get(i).put("PNDlist","");
			}
		}
		map.put("lists", lists);
		return map;
	}
	//根据类别搜索商品信息
	@RequestMapping("/getGoodsListByClass")
	@ResponseBody
	public 	HashMap<String,Object>  getGoodsListByClass( HttpServletRequest request) throws Exception{
		PageData pd=new PageData();
		pd=this.getPageData();

		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/uploadFiles/uploadImgs/";
		int pageNum= Integer.valueOf(pd.getString("pageNum"));
		int pageSize= Integer.valueOf(pd.getString("pageSize"));
		pd.put("pageNum", (pageNum-1)*10);
		pd.put("pageSize", pageSize);
		HashMap<String,Object> map =  new HashMap();
		Double TOTALNUM = appGoodsService.findAllByClass(pd);
		map.put("TOTALNUM", TOTALNUM);
		
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
			if(fpd!=null&&fpd.size()!=0){
				pd.put("PK_SOBOOKS", fpd.get(0).getString("PK_SOBOOKS"));
			}
			pd.put("GOODCODE", GOODCODE);
			lists.get(i).put("Path", basePath+lists.get(i).getString("GOODPIC"));
			List<PageData> lpd=appSalelbillService.listDataAndNumAndPrice(pd); //获取商品最近五次销售信息
			if(lpd!=null&&lpd.size()!=0){
			    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				for(int j =0;j<lpd.size();j++){
					String d=formatter.format(lpd.get(j).get("CREATETIME"));
					lpd.get(j).put("CREATETIME", d);
				}
			     lists.get(i).put("PNDlist",lpd);
			}else{
			     lists.get(i).put("PNDlist","");
			}
		}
		map.put("lists", lists);
		return map;
	}
	
	//根据名称搜索商品信息
		@RequestMapping("/getGoodsListByName")
		@ResponseBody
		public 	HashMap<String,Object>  getGoodsListByName( HttpServletRequest request) throws Exception{

			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/uploadFiles/uploadImgs/";
			PageData pd=new PageData();
			pd=this.getPageData();
			int pageNum= Integer.valueOf(pd.getString("pageNum"));
			int pageSize= Integer.valueOf(pd.getString("pageSize"));
			pd.put("pageNum", (pageNum-1)*10);
			pd.put("pageSize", pageSize);
			HashMap<String,Object> map =  new HashMap();
			Double TOTALNUM = appGoodsService.findAllByName(pd);
			map.put("TOTALNUM", TOTALNUM);
			
			
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
				lists.get(i).put("Path", basePath+lists.get(i).getString("GOODPIC"));
				if(fpd!=null&&fpd.size()!=0){
					pd.put("PK_SOBOOKS", fpd.get(0).getString("PK_SOBOOKS"));
				}
				pd.put("GOODCODE", GOODCODE);
				
				List<PageData> lpd=appSalelbillService.listDataAndNumAndPrice(pd);//获取商品最近五次销售信息
				if(lpd!=null&&lpd.size()!=0){
				    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					for(int j =0;j<lpd.size();j++){
						String d=formatter.format(lpd.get(j).get("CREATETIME"));
						lpd.get(j).put("CREATETIME", d);
					}
				     lists.get(i).put("PNDlist",lpd);
				}else{
				     lists.get(i).put("PNDlist","");
				}
			}
			map.put("lists", lists);
			return map;
		}
		

	

	@RequestMapping(value = "/insLetter")
	@ResponseBody
	public HashMap<String,Object> insLetter(String details, HttpSession session, HttpServletRequest request) throws Exception {
        PageData pd =new PageData();
        pd= this.getPageData();
		
    	String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/uploadFiles/uploadImgs/";
		
        HashMap<String,Object> map =  new HashMap();
		MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
		// 获取文件名集合放入迭代器
		Iterator<String> files = mRequest.getFileNames();
		while (files.hasNext()) {
			MultipartFile mFile = mRequest.getFile(files.next());
 
	        byte[] bytes = null;
			try {
				bytes = mFile.getBytes();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	 
	        // 当前app根目录
	     //   String rootPath = request.getServletContext().getRealPath("/");
	 
	        // 需要上传的相对地址（application.properties中获取）
	      //  String relativePath = "letterImg";
			String  ffile = DateUtil.getDays();
	    	String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG +ffile;		//文件上传路径
	        // 文件夹是否存在，不存在就创建
	        File dir = new File(filePath);
	        if (!dir.exists())
	            dir.mkdirs();
	       // String fileExtension = getFileExtension(file);
	        String filename1 = mFile.getOriginalFilename();
	        String fileExtension = filename1.substring(filename1.lastIndexOf(".")+1);
//	 
	        // 生成UUID样式的文件名
	        String filename = java.util.UUID.randomUUID().toString() + "." + fileExtension;
	 
	        // 文件全名
	        String fullFilename = dir.getAbsolutePath() + "/"+filename;
	 
	 
	        // 保存图片
	        File serverFile = new File(fullFilename);
	        try {
	        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
	        stream.write(bytes);
	        stream.close();
	        }catch (Exception e) {
				// TODO: handle exception
	    
			}
			String GOOD_ID= request.getParameter("GOODCODE");
			pd.put("GOOD_ID", GOOD_ID);
			PageData p = (PageData) goodsService.findByGOODCODE(pd);
			String MASTER_ID=p.getString("GOOD_ID");
			
			pd.put("PICTURES_ID", this.get32UUID());			//主键
			pd.put("TITLE", "商品图片");								//标题
			pd.put("NAME", filename);							//文件名
			pd.put("PATH", ffile+"/"+filename);				//路径
			pd.put("CREATETIME", Tools.date2Str(new Date()));	//创建时间
			pd.put("MASTER_ID", MASTER_ID);						//附属与
			pd.put("BZ", "商品图片");							//备注
			pd.put("ORDER_BY", 1);								//排序
			picturesService.save(pd);
			goodsService.editPic(pd);
			map.put("Message", "OK");
			map.put("Path", basePath+ffile+"/"+filename);
			return map;
		}
		map.put("Message", "Error");
		return map;
	}


	
}
	
