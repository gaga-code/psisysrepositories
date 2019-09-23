package com.psi.service.app.inventorymanagement.salebill.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psi.dao.DaoSupport;
import com.psi.entity.PsiBillCode;
import com.psi.service.app.inventorymanagement.salebill.AppSalebillManager;
import com.psi.service.system.BillCodePsi.BillCodeManager;
import com.psi.util.Const;
import com.psi.util.PageData;
import com.psi.util.ProductBillCodeUtil;
import com.psi.util.UuidUtil;

@Service("appSalebillService")
public class AppSalebillService implements AppSalebillManager {

	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	
	@Autowired
	private ProductBillCodeUtil productBillCodeUtil;
	
	
	@Resource(name="billCodeService")
	private BillCodeManager billCodeService;
	
	
	@Override
	public List<PageData> listDataAndNumAndPrice(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("AppSalebillMapper.listDataAndNumAndPrice", pd);
	}

	@Override
	public List<PageData> listSaleInfoByToday(PageData pd) throws Exception {
		
		return (List<PageData>)dao.findForList("AppSalebillMapper.listSaleInfoByToday", pd);
	}

	@Override
	public List<PageData> listSaleInfoDayByMouth(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("AppSalebillMapper.listSaleInfoDayByMouth", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listSaledGoodsBySTT(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("AppSalebillMapper.listSaledGoodsBySTT", pd);
	}

	@Override
	public List<PageData> listsalebill(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("AppSalebillMapper.listsalebill",pd);
	}

	@Override
	public List<PageData> listsalebillBody(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("AppSalebillMapper.listsalebillBody", pd);
	}

	@Override
	public List<PageData> listSalebillByMouth(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("AppSalebillMapper.listSalebillByMouth", pd);
	}

	@Override
	public PageData findPureAmountById(String Id) throws Exception {
		// TODO Auto-generated method stub
		return (PageData)dao.findForObject("AppSalebillMapper.findPureAmountById", Id);
	}


	@Override
	public List<PageData> listSaledByCustomer(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("AppSalebillMapper.listSaledByCustomer", pd);
	}

	@Override
	public List<PageData> listSaledByUser(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return  (List<PageData>)dao.findForList("AppSalebillMapper.listSaledByUser", pd);
	}

	@Override
	public PageData save(PageData pd) throws Exception {
		String[] strs = productBillCodeUtil.getBillCode(Const.BILLCODE_SALEBILL_PFIX); //获取该编号类型的最大编号
		pd.put("BILLCODE", strs[0]);
		//保存商品
		String goodslist = (String) pd.get("salebillbody");
		String[] split = goodslist.split("#");
		//遍历每行数据
		for(int i = 0; i < split.length; i++) {
			String[] agoods = split[i].split(",");
			PageData pageData = new PageData();
			pageData.put("FGROUP_ID", UuidUtil.get32UUID());
			pageData.put("SALEBILL_ID", pd.get("SALEBILL_ID"));
			pageData.put("PK_SOBOOKS", pd.get("PK_SOBOOKS"));
			pageData.put("APPBILLNO", strs[0]);
			
			pageData.put("BARCODE", agoods[1]);
			pageData.put("WAREHOUSE_ID", agoods[2]);
			pageData.put("UNITPRICE_ID", agoods[3]);
			pageData.put("PNUMBER", agoods[4]);
			pageData.put("AMOUNT", agoods[5]);
			pageData.put("ISFREE", agoods[6]);
			pageData.put("NOTE", agoods[7]);
			pageData.put("GOODCODE_ID", agoods[8]);
			
			dao.save("SalebillBodyMapper.save", pageData);
		}
		dao.save("SalebillMapper.save", pd);
		//保存销售单编号
		if(strs[1] == null){ //新增
			PsiBillCode psiBillCode = new PsiBillCode();
			psiBillCode.setCode_ID(UuidUtil.get32UUID());
			psiBillCode.setCodeType(Const.BILLCODE_SALEBILL_PFIX);
			psiBillCode.setMaxNo(strs[0]);
			psiBillCode.setNOTE("销售单编号");
			billCodeService.insertBillCode(psiBillCode);
		}else{//修改
			PageData ppp = new PageData();
			ppp.put("MaxNo",strs[0]);
			ppp.put("Code_ID", strs[1]);
			billCodeService.updateMaxNo(ppp);
		}
		return pd;
	}

	@Override
	public int listSaledGoodsBySTTNum(PageData pd) throws Exception {
		// TODO Auto-generated method stub
	
		List<PageData> lpd=(List<PageData>)dao.findForList("AppSalebillMapper.listSaledGoodsBySTTNum", pd);
		if(lpd==null&&lpd.size()==0) {
			return 0;
		}else {
			return lpd.size();
		}
	
	}

	@Override
	public int listSaledByCustomerNum(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (Integer)dao.findForObject("AppSalebillMapper.listSaledByCustomerNum", pd);
	}

	@Override
	public int listSaledByUserNum(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (Integer)dao.findForObject("AppSalebillMapper.listSaledByUserNum", pd);
	}

	@Override
	public int listsalebillNum(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (Integer)dao.findForObject("AppSalebillMapper.listsalebillNum", pd);
	}

	
	/**
	 * 审批销售单
	 */
	@Override
	public void updateshenpi(PageData pd) throws Exception {
		//把销售单的状态改为已审核
		PageData pd1=(PageData)dao.findForObject("SalebillMapper.findAllById", pd);//查询单据是销售退货单还是销售单
		if(pd1.getString("BILLTYPE").equals("8")){
			pd.put("BILLSTATUS", 1);
			dao.update("SalebillMapper.shenpi", pd);
		}else{
			pd.put("BILLSTATUS", 2);
			dao.update("SalebillMapper.shenpi", pd);
		}
		//获取销售单表头数据
		PageData head =  (PageData)dao.findForObject("SalebillMapper.findById", pd);
		//通过销售单ID获取该销售单的商品信息
		List<PageData> goodslist = (List<PageData>)dao.findForList("SalebillBodyMapper.findById", pd);
		
		//依次把商品数量更新到 仓库-商品 表中和商品的总数量中
		for (PageData pageData : goodslist) {
			//把商品的编号加入到查询条件
			head.put("GOOD_ID", pageData.get("GOODCODE_ID"));
			
			//=========================操作商品表===================
			//更新最后进价 和 库存总数量
			PageData aGoods =  (PageData)dao.findForObject("GoodsMapper.findByGOODCODE", head);
			head.put("LASTPPRICE", aGoods.get("LASTPPRICE"));//销售时不修改进价
			head.put("STOCKNUM", (Integer)aGoods.get("STOCKNUM") - (Integer)pageData.get("PNUMBER"));
			dao.update("GoodsMapper.editStocknumAndLastprice", head);
			
			//=========================操作售价表===================
			//直接插入一条价格数据
			head.put("SALEPRECORD_ID", UuidUtil.get32UUID());
			head.put("SALECOME", pageData.get("UNITPRICE_ID"));
			SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间 
	        sdf.applyPattern("yyyy-MM-dd"); 
	        Date date = new Date();// 获取当前时间 
			head.put("SALEDATE", sdf.format(date).toString());//日期
			dao.save("SalePriceRecordMapper.save", head);
			
			//=========================操作库存表===================
			//先查看 仓库-商品 表中是否包含相应的 仓库-商品
			head.put("WAREHOUSE_ID", pageData.get("WAREHOUSE_ID"));
			PageData aGood =  (PageData)dao.findForObject("StockMapper.findByWarehouseAndGood", head);
			//有，把数量更新
			if(aGood != null) {
				//把仓库中的库存加上销售单商品的数量
				head.put("STOCK", (Integer)aGood.get("STOCK") - (Integer)pageData.get("PNUMBER"));
				dao.update("StockMapper.edit", head);
			}
			//没有，出错！
			else {
//				head.put("WAREHOUSE_GOOD_ID", UuidUtil.get32UUID());
//				head.put("STOCK", pageData.get("PNUMBER"));
//				dao.save("StockMapper.save", head);
			}
			
		}
	}
	
}
