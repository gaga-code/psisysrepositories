package com.psi.controller.system.daying;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JFileChooser;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTSheetProtection;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.psi.controller.base.BaseController;
import com.psi.service.basedata.bzbill.BZBillManager;
import com.psi.service.inventorymanagement.inorder.InOrderManager;
import com.psi.service.inventorymanagement.salebill.SalebillManager;
import com.psi.util.Const;
import com.psi.util.ConvertMoney;
import com.psi.util.PageData;
import com.psi.util.PathUtil;

@RequestMapping("/daying")
@Controller
public class DaYingController extends BaseController{


	@Resource(name="inOrderService")
	private InOrderManager inOrderService;
	
	
	@Resource(name="bZBillService")
	private BZBillManager bZBillService;
	
	@Resource(name="salebillService")
	private SalebillManager salebillService;
	
	//导出送货单Excel
    @RequestMapping("/createExcel")
    @ResponseBody
    public String createExcel(HttpServletResponse response) throws Exception {
    	
    	PageData pd = new PageData();
    	pd =  this.getPageData();
    	
    	int flag=1; //1代表是进货   2 代表是销售 
    	List<PageData> lpd= new ArrayList();
    	if(pd.get("SALEBILL_ID")!=null){
    		lpd=salebillService.printSalebill(pd);
    		flag=2;
    	}
    	if(pd.get("INORDER_ID")!=null){
    		lpd=inOrderService.printInOrder(pd);
    	}
    	
        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet=wb.createSheet("报表");
        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1=sheet.createRow(0);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell=row1.createCell(0);
        
        // 1.生成字体对象  
        HSSFFont font = wb.createFont();  
        font.setFontHeightInPoints((short) 20);  
        font.setFontName("新宋体");  
        font.setFontName("Arial"); //什么字体 
        font.setItalic(false); //是不倾斜 
        font.setStrikeout(false); //是不是划掉 
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //字体加粗 
        
        // 2.生成样式对象，这里的设置居中样式和版本有关，我用的poi用HSSFCellStyle.ALIGN_CENTER会报错，所以用下面的
        HSSFCellStyle style = wb.createCellStyle();
//      style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//设置居中样式
        style.setFont(font); // 调用字体样式对象  
        style.setWrapText(true);  
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
        style.setLocked(true);
        // 3.单元格应用样式  
        cell.setCellStyle(style); 
        
        //设置单元格内容
        if(flag==2){
        	cell.setCellValue(lpd.get(0).getString("ENTERPRISENAME")+"送货单");
        }
        if(flag==1){
        	cell.setCellValue(lpd.get(0).getString("ENTERPRISENAME")+"进货单");
        }
        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(0,4,0,10));
       
        HSSFFont font1 = wb.createFont();  
        font1.setFontHeightInPoints((short) 12);  
        font1.setFontName("新宋体");  
        font1.setFontName("Arial"); //什么字体 
        font1.setItalic(false); //是不倾斜 
        font1.setStrikeout(false); //是不是划掉 
        font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //字体加粗 
        
        
        HSSFCellStyle style1 = wb.createCellStyle();
//      style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//设置居中样式
        style1.setFont(font1); // 调用字体样式对象  
        style1.setWrapText(true);  
        style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
        style1.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 水平
        style1.setLocked(true);
        //在sheet里创建第二行
        HSSFRow row2=sheet.createRow(5);
        //创建单元格并设置单元格内容及样式
        HSSFCell cell0=row2.createCell(1);
        cell0.setCellStyle(style1);
        cell0.setCellValue("客户名称："+lpd.get(0).getString("CUATOMERNAME"));
      //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(5,6,1,3));
        
        
        HSSFCell cell1=row2.createCell(4);
        cell1.setCellStyle(style1);
        cell1.setCellValue("经营地址："+lpd.get(0).getString("GADDRESS"));
      //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(5,6,4,7));
        
        
        
        HSSFCell cell2=row2.createCell(8);
        cell2.setCellStyle(style1);
        cell2.setCellValue("单号："+lpd.get(0).getString("BILLCODE"));
      //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(5,6,8,10));
     
        //在sheet里创建第三行
        HSSFRow row3=sheet.createRow(7);
        //创建单元格并设置单元格内容及样式
        HSSFCell cell20=row3.createCell(1);
        cell20.setCellStyle(style1);
        String CADDRESS = lpd.get(0).getString("CADDRESS");
        if(CADDRESS!=null){
        	cell20.setCellValue("客户地址："+CADDRESS);

        }else{
        	cell20.setCellValue("客户地址：");
        }
        
        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(7,8,1,3));
        
        
        HSSFCell cell21=row3.createCell(4);
        cell21.setCellStyle(style1);
        cell21.setCellValue("联系电话："+lpd.get(0).getString("PHONE"));
      //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(7,8,4,7));
        
        
        //在sheet里创建第四行
        HSSFRow row4=sheet.createRow(9);
        //创建单元格并设置单元格内容及样式
        HSSFCell cell30=row4.createCell(1);
        cell30.setCellStyle(style1);
        cell30.setCellValue("客户电话："+lpd.get(0).getString("TELEPHONE"));
      //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(9,10,1,3));
        
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy年MM月dd日");
        HSSFCell cell31=row4.createCell(4);
        cell31.setCellStyle(style1);
        cell31.setCellValue("日期："+sdf.format(new Date()));
      //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(9,10,4,7));
        
        
        
        HSSFCellStyle style2 = wb.createCellStyle();
//      style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//设置居中样式
        style2.setFont(font1); // 调用字体样式对象  
        style2.setWrapText(true);  
        style2.setLocked(true);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
        style2.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM); //下边框
        style2.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);//左边框
        style2.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);//上边框
        style2.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);//右边框
 
        
        //在sheet里创建第五行
        HSSFRow row5=sheet.createRow(11);
        //创建单元格并设置单元格内容及样式
        HSSFCell cell40=row5.createCell(0);
        cell40.setCellStyle(style2);
        cell40.setCellValue("序号");
 
        
        HSSFCell cell41=row5.createCell(1);
        cell41.setCellStyle(style2);
        cell41.setCellValue("商品编号");
      //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
   
        
        HSSFCell cell42=row5.createCell(2);
        cell42.setCellStyle(style2);
        cell42.setCellValue("商品名称");
        
        HSSFCell cell43=row5.createCell(3);
        cell43.setCellStyle(style2);
        cell43.setCellValue("规格");
        
        HSSFCell cell44=row5.createCell(4);
        cell44.setCellStyle(style2);
        cell44.setCellValue("数量");
        
        HSSFCell cell45=row5.createCell(5);
        cell45.setCellStyle(style2);
        cell45.setCellValue("单位");
       
        
        HSSFCell cell46=row5.createCell(6);
        cell46.setCellStyle(style2);
        cell46.setCellValue("单价");
     
        
        HSSFCell cell47=row5.createCell(7);
        cell47.setCellStyle(style2);
        cell47.setCellValue("金额");
      
        
        HSSFCell cell48=row5.createCell(8);
        cell48.setCellStyle(style2);
        cell48.setCellValue("货架");
       
        HSSFCell cell49=row5.createCell(9);
        cell49.setCellStyle(style2);
        cell49.setCellValue("备注");
        
        HSSFCell cell410=row5.createCell(10);
        cell410.setCellStyle(style2);
        cell410.setCellValue("出货仓库");
       
      //循环lpd
        for(int i=0;i<lpd.size();i++){
        	   //在sheet里创建第x行
        	int row = 11+1+i;
            HSSFRow rowx=sheet.createRow(row);
            //创建单元格并设置单元格内容及样式
            HSSFCell cellx0=rowx.createCell(0);
            cellx0.setCellStyle(style2);
            cellx0.setCellValue(i);
            
            
            HSSFCell cellx1=rowx.createCell(1);
            cellx1.setCellStyle(style2);
            cellx1.setCellValue(lpd.get(i).getString("GOODCODE"));
          //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
            
            HSSFCell cellx2=rowx.createCell(2);
            cellx2.setCellStyle(style2);
            cellx2.setCellValue(lpd.get(i).getString("GOODNAME"));
 
       
            
            HSSFCell cellx3=rowx.createCell(3);
            cellx3.setCellStyle(style2);
            cellx3.setCellValue(lpd.get(i).getString("GOODSPECIF"));
            
            
            HSSFCell cellx4=rowx.createCell(4);
            cellx4.setCellStyle(style2);
            cellx4.setCellValue(String.valueOf(lpd.get(i).get("PNUMBER")));
            
            HSSFCell cellx5=rowx.createCell(5);
            cellx5.setCellStyle(style2);
            cellx5.setCellValue(lpd.get(i).getString("NAME"));
           
            
            HSSFCell cellx6=rowx.createCell(6);
            cellx6.setCellStyle(style2);
            cellx6.setCellValue(String.valueOf(lpd.get(i).get("UNITPRICE_ID")));
         
            
            HSSFCell cellx7=rowx.createCell(7);
            cellx7.setCellStyle(style2);
            cellx7.setCellValue(String.valueOf(lpd.get(i).get("AMOUNT")));
          
            
            HSSFCell cellx8=rowx.createCell(8);
            cellx8.setCellStyle(style2);
            cellx8.setCellValue(lpd.get(i).getString("SUBGZ_ID"));
           
            HSSFCell cellx9=rowx.createCell(9);
            cellx9.setCellStyle(style2);
            cellx9.setCellValue(lpd.get(i).getString("NOTE"));
       
            
            
            HSSFCell cellx10=rowx.createCell(10);
            cellx10.setCellStyle(style2);
            cellx10.setCellValue(lpd.get(i).getString("WHNAME"));
          
        }
        
        
        int nowrow =  lpd.size()+12;
        //在sheet里创建第六行实际事 nowrow 行
        HSSFRow row6=sheet.createRow(nowrow);
        //创建单元格并设置单元格内容及样式
        
        double allAmount= (Double)lpd.get(0).get("ALLAMOUNT");
        HSSFCell cell60=row6.createCell(1);
        cell60.setCellStyle(style1);
        cell60.setCellValue("总金额大写："+ConvertMoney.convert(allAmount));
        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(nowrow,nowrow,1,5));
        
        HSSFCell cell61=row6.createCell(7);
        cell61.setCellStyle(style1);
        cell61.setCellValue("小写：￥"+allAmount);
        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(nowrow,nowrow,7,10));
  
        
       //在sheet里创建第七行实际事 nowrow +1行
        nowrow+=5;
        HSSFRow row7=sheet.createRow(nowrow);
        //创建单元格并设置单元格内容及样式
        HSSFCell cell70=row7.createCell(1);
        cell70.setCellStyle(style1);
        cell70.setCellValue("经手："+lpd.get(0).getString("USERNAME"));
       
        
        HSSFCell cell71=row7.createCell(2);
        cell71.setCellStyle(style1);
        cell71.setCellValue("捡货/验货：");
        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(nowrow,nowrow,2,3));
        
        
        HSSFCell cell72=row7.createCell(4);
        cell72.setCellStyle(style1);
        cell72.setCellValue("送货：");
        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(nowrow,nowrow,4,5));
        
        
        HSSFCell cell73=row7.createCell(6);
        cell73.setCellStyle(style1);
        cell73.setCellValue("封箱："+"       件");
        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(nowrow,nowrow,6,7));
        
        
        HSSFCell cell74=row7.createCell(8);
        cell74.setCellStyle(style1);
        cell74.setCellValue("客户未付款签收:");
        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(nowrow,nowrow,8,10));
        
        
        
        //在sheet里创建第七行实际事 nowrow +1行
        nowrow+=2;
        HSSFRow row8=sheet.createRow(nowrow);
        //创建单元格并设置单元格内容及样式
        HSSFCell cell80=row8.createCell(1);
        cell80.setCellStyle(style1);
        cell80.setCellValue("备注：");
        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(nowrow,nowrow,1,5));
        
        HSSFCell cell81=row8.createCell(7);
        cell81.setCellStyle(style1);
        cell81.setCellValue("本页小计："+lpd.get(0).get("ALLAMOUNT"));
        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(nowrow,nowrow,7,10));
        
        
        //在sheet里创建第七行实际事 nowrow +1行
        nowrow+=2;
        HSSFRow row9=sheet.createRow(nowrow);
        //创建单元格并设置单元格内容及样式
        HSSFCell cell90=row9.createCell(1);
        cell90.setCellStyle(style1);
        cell90.setCellValue("注意：货品数量请当面点清，如有质量问题请于三天内联系我们！谢谢合作！");
        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(nowrow,nowrow,1,10));
        
        
        setSizeColumn(sheet);
        //sheet表加密：等效excel的审阅菜单下的保护工作表
        //sheet.protectSheet(new String("333"));//333是密码
        
        sheet.protectSheet(get32UUID());

        //输出Excel文件
        OutputStream output=response.getOutputStream();
        response.reset();
        SimpleDateFormat sdf1= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        String file= sdf1.format(new Date())+".xls";
        response.setHeader("Content-disposition", "attachment; filename="+file);//文件名这里可以改
        response.setContentType("application/msexcel");
        wb.write(output);
        output.close();
        
        
  
        boolean b= printjpg(file,"1");
        return b?"OK":"false";
    }

 // 自适应宽度(中文支持)
    private void setSizeColumn(HSSFSheet sheet) {
        for (int columnNum = 1; columnNum <= 11; columnNum++) {
            int columnWidth = sheet.getColumnWidth(columnNum) / 256;
            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                HSSFRow currentRow;
                //当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }

                if (currentRow.getCell(columnNum) != null) {
                    HSSFCell currentCell = currentRow.getCell(columnNum);
                    if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                        int length = currentCell.getStringCellValue().getBytes().length;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }
                }
            }
            sheet.setColumnWidth(columnNum, columnWidth * 256);
        }
    }

    
	public boolean printjpg(String getfile, String fensu) {
		// 构造一个文件选择器，默认为当前目录

		File file = new File(getfile);// 获取选择的文件
		// 构建打印请求属性集
		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		// 设置打印格式，因为未确定文件类型，这里选择AUTOSENSE
		DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
		// 查找所有的可用打印服务
		 PrintService printService[] =
		 PrintServiceLookup.lookupPrintServices(flavor, pras);
	/*	// 定位默认的打印服务
		PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();*/
		// 显示打印对话框
		 
		 if(printService.length==0){
			 return false;
		 }
		long j = Integer.parseInt(fensu);
		for (int i = 0; i < j; i++) {
			try {
				DocPrintJob job = printService[0].createPrintJob(); // 创建打印作业
				FileInputStream fis = new FileInputStream(file); // 构造待打印的文件流
				DocAttributeSet das = new HashDocAttributeSet();
				Doc doc = new SimpleDoc(fis, flavor, das); // 建立打印文件格式
				job.print(doc, pras); // 进行文件的打印
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	
/*	public boolean createfile(){
		JFileChooser fileChooser = new JFileChooser(); // 创建打印作业
		int state = fileChooser.showOpenDialog(null);
		if (state == fileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile(); // 获取选择的文件
			// 构建打印请求属性集
			HashPrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
			// 设置打印格式，因为未确定类型，所以选择autosense
			DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
			// 查找所有的可用的打印服务
			PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
			// 定位默认的打印服务
			PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
			// 显示打印对话框
			PrintService service = ServiceUI.printDialog(null, 200, 200,
					printService, defaultService, flavor, pras);
			if (service != null){
				try {
					DocPrintJob job = service.createPrintJob(); // 创建打印作业
					FileInputStream fis = new FileInputStream(file); // 构造待打印的文件流
					DocAttributeSet das = new HashDocAttributeSet();
					Doc doc = new SimpleDoc(fis, flavor, das);
					job.print(doc, pras);
				}catch(Exception e){
					e.printStackTrace();
					
				} 	
			}
		}
		return true;
	}
*/
	
	
	
	
	
	
	
	
	//---------------------------------------------------------------------------------
	//导出送货单Excel
    @RequestMapping("/duizhang")
    @ResponseBody
    public String duizhang(HttpServletResponse response) throws Exception {
    	
    	PageData pd = new PageData();
    	pd =  this.getPageData();
    	
    	String CUSTOMER_ID= pd.getString("CUSTOMER_ID");
    	
 
        List<PageData> lpd = null;
        if(CUSTOMER_ID!=null&&CUSTOMER_ID!=""){
        	lpd = salebillService.listByCustomer(pd);
        }
     
        
    	pd = bZBillService.findByPK(pd);
        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet=wb.createSheet("报表");
        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1=sheet.createRow(0);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell=row1.createCell(0);
        
        // 1.生成字体对象  
        HSSFFont font = wb.createFont();  
        font.setFontHeightInPoints((short) 20);  
        font.setFontName("新宋体");  
        font.setFontName("Arial"); //什么字体 
        font.setItalic(false); //是不倾斜 
        font.setStrikeout(false); //是不是划掉 
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //字体加粗 
        
        // 2.生成样式对象，这里的设置居中样式和版本有关，我用的poi用HSSFCellStyle.ALIGN_CENTER会报错，所以用下面的
        HSSFCellStyle style = wb.createCellStyle();
//      style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//设置居中样式
        style.setFont(font); // 调用字体样式对象  
        style.setWrapText(true);  
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
        style.setLocked(true);
        // 3.单元格应用样式  
        cell.setCellStyle(style); 
        
   
        cell.setCellValue(pd.getString("TITLE"));
        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(0,4,0,8));
       
        HSSFFont font1 = wb.createFont();  
        font1.setFontHeightInPoints((short) 12);  
        font1.setFontName("新宋体");  
        font1.setFontName("Arial"); //什么字体 
        font1.setItalic(false); //是不倾斜 
        font1.setStrikeout(false); //是不是划掉 
        font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //字体加粗 
        
        
        HSSFCellStyle style1 = wb.createCellStyle();
//      style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//设置居中样式
        style1.setFont(font1); // 调用字体样式对象  
        style1.setWrapText(true);  
        style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
        style1.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 水平
        style1.setLocked(true);
        //在sheet里创建第二行
        HSSFRow row2=sheet.createRow(5);
        row2.setHeight((short)400);
        //创建单元格并设置单元格内容及样式
        HSSFCell cell0=row2.createCell(1);
        cell0.setCellStyle(style1);
        if(CUSTOMER_ID!=null){
        	cell0.setCellValue("客户名称："+lpd.get(0).getString("CUATOMERNAME"));
        }else{
        	cell0.setCellValue("客户名称：");
        }
      //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(5,6,1,4));
        
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy年MM月dd日");
        
        SimpleDateFormat sdf1= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        
        
        HSSFCell cell1=row2.createCell(5);
        cell1.setCellStyle(style1);
        cell1.setCellValue("日期："+sdf.format(new Date()));
      //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(5,6,5,8));
        
   
        //在sheet里创建第三行
        HSSFRow row3=sheet.createRow(7);
        row3.setHeight((short)400);
        //创建单元格并设置单元格内容及样式
        HSSFCell cell20=row3.createCell(1);
        cell20.setCellStyle(style1);
        cell20.setCellValue("如对以下款项有疑问,请致电我司对账,电话"+pd.getString("TELEPHONE")+";如无疑问,请安排付款事宜,谢谢！");
        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(7,8,1,8));
        
    
        
        
        //在sheet里创建第四行
        HSSFRow row4=sheet.createRow(9);
        row4.setHeight((short)400);
        //创建单元格并设置单元格内容及样式
        HSSFCell cell30=row4.createCell(1);
        cell30.setCellStyle(style1);
        cell30.setCellValue("付款方式一 : 二维码收款");
      //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(9,9,1,5));
        
     
        //在sheet里创建第五行
        HSSFRow row5=sheet.createRow(10);
        row5.setHeight((short)400);
        //创建单元格并设置单元格内容及样式
        HSSFCell cell40=row5.createCell(2);
        cell40.setCellStyle(style1);
        cell40.setCellValue("步骤 1:识别图中二维码");
      //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(10,10,2,6));
        
        //在sheet里创建第六行
        HSSFRow row6=sheet.createRow(11);
        row6.setHeight((short)400);
        //创建单元格并设置单元格内容及样式
        HSSFCell cell50=row6.createCell(2);
        cell50.setCellStyle(style1);
        cell50.setCellValue("步骤 2: 输入付款金额");
      //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(11,11,2,6));
        
        
        //在sheet里创建第7行
        HSSFRow row7=sheet.createRow(12);
        row7.setHeight((short)400);
        //创建单元格并设置单元格内容及样式
        HSSFCell cell60=row7.createCell(2);
        cell60.setCellStyle(style1);
        cell60.setCellValue("步骤 3: 再添加备注栏下留下付款客户信息,以便对账");
      //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(12,12,2,6));
        
        
        //在sheet里创建第8行
        HSSFRow row8=sheet.createRow(13);
        row8.setHeight((short)400);
        //创建单元格并设置单元格内容及样式
        HSSFCell cell70=row8.createCell(1);
        cell70.setCellStyle(style1);
        cell70.setCellValue("付款方式二 : 银行转账");
      //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(13,13,1,5));
        
        
        //在sheet里创建第10行
        HSSFRow row9=sheet.createRow(14);
        row9.setHeight((short)400);
        //创建单元格并设置单元格内容及样式
        HSSFCell cell80=row9.createCell(2);
        cell80.setCellStyle(style1);
        cell80.setCellValue("收款人名称 : "+pd.getString("ACCOUNTNAME"));
      //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(14,14,2,6));
        
        //在sheet里创建第11行
        HSSFRow row10=sheet.createRow(15);
        row10.setHeight((short)400);
        //创建单元格并设置单元格内容及样式
        HSSFCell cell90=row10.createCell(2);
        cell90.setCellStyle(style1);
        cell90.setCellValue("开户银行 : "+pd.getString("ACCOUNTBANK"));
      //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(15,15,2,4));
        
        
        //在sheet里创建第12行
        HSSFRow row11=sheet.createRow(16);
        row11.setHeight((short)400);
        //创建单元格并设置单元格内容及样式
        HSSFCell cell100=row11.createCell(2);
        cell100.setCellStyle(style1);
        cell100.setCellValue("账号: "+pd.getString("ACCOUNT"));
      //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(16,16,2,6));
        
        
        
    
        //创建单元格并设置单元格内容及样式
        HSSFCell cell91=row10.createCell(6);
        cell91.setCellStyle(style1);
        cell91.setCellValue(pd.getString("NOTE"));
      //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(15,15,6,8));
   
 
      //加入图片
        String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG+"1566560051.png";
        byte[] bt = FileUtils.readFileToByteArray(new File(filePath));
        int pictureIdx = wb.addPicture(bt, Workbook.PICTURE_TYPE_PNG);
        CreationHelper helper = wb.getCreationHelper();
        Drawing drawing = sheet.createDrawingPatriarch();
        ClientAnchor anchor = helper.createClientAnchor();
        anchor.setCol1(6); //图片开始列数
        anchor.setRow1(9); //图片开始行数
        anchor.setCol2(8); //图片结束列数
        anchor.setRow2(15);//图片结束行数
        drawing.createPicture(anchor, pictureIdx);

      
        
        
        
        HSSFCellStyle style2 = wb.createCellStyle();
//      style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//设置居中样式
        style2.setFont(font1); // 调用字体样式对象  
        style2.setWrapText(true);  
        style2.setLocked(true);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
        style2.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM); //下边框
        style2.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);//左边框
        style2.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);//上边框
        style2.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);//右边框
 
        
        //在sheet里创建第五行
        HSSFRow row13=sheet.createRow(18);
        row13.setHeight((short)400);
        //创建单元格并设置单元格内容及样式
        HSSFCell cell120=row13.createCell(0);
        cell120.setCellStyle(style2);
        cell120.setCellValue("序号");
 
        
        HSSFCell cell121=row13.createCell(1);
        cell121.setCellStyle(style2);
        cell121.setCellValue("单据编号");
      //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
   
        
        HSSFCell cell122=row13.createCell(2);
        cell122.setCellStyle(style2);
        cell122.setCellValue("开单日期");
        
        HSSFCell cell123=row13.createCell(3);
        cell123.setCellStyle(style2);
        cell123.setCellValue("开单月份");
        
        HSSFCell cell124=row13.createCell(4);
        cell124.setCellStyle(style2);
        cell124.setCellValue("应收金额");
        
        HSSFCell cell125=row13.createCell(5);
        cell125.setCellStyle(style2);
        cell125.setCellValue("已收金额");
       
        
        HSSFCell cell126=row13.createCell(6);
        cell126.setCellStyle(style2);
        cell126.setCellValue("未收金额");
     
        
        HSSFCell cell127=row13.createCell(7);
        cell127.setCellStyle(style2);
        cell127.setCellValue("年月份");
      
        
        HSSFCell cell138=row13.createCell(8);
        cell138.setCellStyle(style2);
        cell138.setCellValue("月份总计");
    
        
      //循环lpd
        HashMap<String,Double> map = new HashMap();
        double yinshou =0; // 应收金额
        double yifu = 0; //已付金额
        double weishou =0; //未收金额
        if(lpd!=null && lpd.size()!=0){
	        for(int i=0;i<lpd.size();i++){
	        	String yearMouth = lpd.get(i).get("yearMouth").toString();
	        	if(map.get(yearMouth)==null){
	        		map.put(yearMouth, (Double) lpd.get(i).get("ALLAMOUNT"));
	        	}else{
	        		double ALLAMOUNT=map.get(yearMouth)+(Double) lpd.get(i).get("ALLAMOUNT");
	        		map.put(yearMouth, ALLAMOUNT);
	        	}
	        	yinshou+=(Double)lpd.get(i).get("ALLAMOUNT");
	        	weishou+=(Double)lpd.get(i).get("UNPAIDAMOUNT");
	        	yifu+=(Double)lpd.get(i).get("PAIDAMOUNT");
	        }
	        
	  
	        for(int i=0;i<lpd.size();i++){
	        	   //在sheet里创建第x行
	        	int row = 19+i;
	            HSSFRow rowx=sheet.createRow(row);
	            //创建单元格并设置单元格内容及样式
	            HSSFCell cellx0=rowx.createCell(0);
	            cellx0.setCellStyle(style2);
	            cellx0.setCellValue(i);
	            
	            
	            HSSFCell cellx1=rowx.createCell(1);
	            cellx1.setCellStyle(style2);
	            cellx1.setCellValue(lpd.get(i).getString("BILLCODE"));
	          //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
	            
	            HSSFCell cellx2=rowx.createCell(2);
	            cellx2.setCellStyle(style2);
	            cellx2.setCellValue(lpd.get(i).getString("CREATETIME"));
	 
	       
	            
	            HSSFCell cellx3=rowx.createCell(3);
	            cellx3.setCellStyle(style2);
	            cellx3.setCellValue(lpd.get(i).getString("yearMouth"));
	            
	            
	            HSSFCell cellx4=rowx.createCell(4);
	            cellx4.setCellStyle(style2);
	            cellx4.setCellValue(String.valueOf(lpd.get(i).get("ALLAMOUNT")));
	            
	            HSSFCell cellx5=rowx.createCell(5);
	            cellx5.setCellStyle(style2);
	            cellx5.setCellValue(String.valueOf(lpd.get(i).get("PAIDAMOUNT")));
	           
	            
	            HSSFCell cellx6=rowx.createCell(6);
	            cellx6.setCellStyle(style2);
	            cellx6.setCellValue(String.valueOf(lpd.get(i).get("UNPAIDAMOUNT")));        
	            
	            
	            
	            Iterator<Entry<String, Double>> map1it=map.entrySet().iterator();
	            int j=0;
	            while(map1it.hasNext())
	            {
	            	Map.Entry<String, Double> entry=(Entry<String, Double>) map1it.next();
	            	System.out.println("Key: "+entry.getKey()+" Value: "+entry.getValue());
	            	if(j==i){
		                HSSFCell cellx7=rowx.createCell(7);
		                cellx7.setCellStyle(style2);
		                cellx7.setCellValue(entry.getKey());     
		      
		                HSSFCell cellx8=rowx.createCell(8);
		                cellx8.setCellStyle(style2);
		                cellx8.setCellValue(entry.getValue());     
		              
	            	}
	            	  j++;
	                
	            }  
	        }
	        
	        int nowrow =  lpd.size()+19;
	        //在sheet里创建第六行实际事 nowrow 行
	        HSSFRow row19=sheet.createRow(nowrow);
	        HSSFCell celln0=row19.createCell(1);
	        celln0.setCellStyle(style2);
	        celln0.setCellValue("总计");     
	        
	        HSSFCell celln1=row19.createCell(4);
	        celln1.setCellStyle(style2);
	        celln1.setCellValue(String.valueOf(yinshou));     
	        
	        HSSFCell celln2=row19.createCell(5);
	        celln2.setCellStyle(style2);
	        celln2.setCellValue(String.valueOf(yifu));     
	        
	        HSSFCell celln3=row19.createCell(6);
	        celln3.setCellStyle(style2);
	        celln3.setCellValue(String.valueOf(weishou));     
        }
        
        
       
        
   
        //  setSizeColumn(sheet);
        //sheet表加密：等效excel的审阅菜单下的保护工作表
        //sheet.protectSheet(new String("333"));//333是密码
        for(int j = 1; j < 11; j++) {
            sheet.setColumnWidth(j, MSExcelUtil.pixel2WidthUnits(100)); //设置列宽
        }
    
        sheet.protectSheet(get32UUID());

        //输出Excel文件
        OutputStream output=response.getOutputStream();
        response.reset();
      
        String file= sdf1.format(new Date())+".xls";
        response.setHeader("Content-disposition", "attachment; filename="+file);//文件名这里可以改
        response.setContentType("application/msexcel");
        wb.write(output);
        output.close();
        
        
  
        boolean b= printjpg(file,"1");
        return b?"OK":"false";
    }
    
    public static class MSExcelUtil {
    	 
        public static final short EXCEL_COLUMN_WIDTH_FACTOR = 256;
        public static final int UNIT_OFFSET_LENGTH = 7;
        public static final int[] UNIT_OFFSET_MAP = new int[] { 0, 36, 73, 109, 146, 182, 219 };
     
        /**
         * pixel units to excel width units(units of 1/256th of a character width)
         * 
         * @param pxs
         * @return
         */
        public static short pixel2WidthUnits(int pxs) {
            short widthUnits = (short) (EXCEL_COLUMN_WIDTH_FACTOR * (pxs / UNIT_OFFSET_LENGTH));
            widthUnits += UNIT_OFFSET_MAP[(pxs % UNIT_OFFSET_LENGTH)];
            return widthUnits;
        }
     
        /**
         * excel width units(units of 1/256th of a character width) to pixel units
         * 
         * @param widthUnits
         * @return
         */
        public static int widthUnits2Pixel(int widthUnits) {
            int pixels = (widthUnits / EXCEL_COLUMN_WIDTH_FACTOR) * UNIT_OFFSET_LENGTH;
            int offsetWidthUnits = widthUnits % EXCEL_COLUMN_WIDTH_FACTOR;
            pixels += Math.round(offsetWidthUnits
                    / ((float) EXCEL_COLUMN_WIDTH_FACTOR / UNIT_OFFSET_LENGTH));
     
            return pixels;
        }
    }
}
