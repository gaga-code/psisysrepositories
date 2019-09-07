package com.psi.service.inventorymanagement.resalebill.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psi.dao.DaoSupport;
import com.psi.entity.PsiBillCode;
import com.psi.service.inventorymanagement.resalebill.ReSalebillManager;
import com.psi.service.system.BillCodePsi.BillCodeManager;
import com.psi.util.Const;
import com.psi.util.PageData;
import com.psi.util.ProductBillCodeUtil;
import com.psi.util.UuidUtil;

@Service
public class ReSalebillService implements ReSalebillManager{
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	

	@Autowired
	private ProductBillCodeUtil productBillCodeUtil;
	
	@Resource(name="billCodeService")
	private BillCodeManager billCodeService;
	
	/**
	 * 反审销售单
	 */
	@Override
	public void updatefanshen(PageData pd) throws Exception {
		
		//获取销售单表头数据
		PageData head =  (PageData)dao.findForObject("SalebillMapper.findById", pd);
		PageData newHead = (PageData)head.clone();
		//通过销售单ID获取该销售单的商品信息
		List<PageData> goodslist = (List<PageData>)dao.findForList("SalebillBodyMapper.findById", pd);
		//依次把商品数量在 仓库-商品 表中增加
		for (PageData pageData : goodslist) {
			head.put("GOOD_ID", pageData.get("GOODCODE_ID"));
			//获取原来的库存
			head.put("WAREHOUSE_ID", pageData.get("WAREHOUSE_ID"));
			PageData aGood =  (PageData)dao.findForObject("StockMapper.findByWarehouseAndGood", head);
			
			//=========================操作商品表===================
			//更新最后进价 和 库存总数量
			PageData aGoods =  (PageData)dao.findForObject("GoodsMapper.findByGOODCODE", head);
			head.put("LASTPPRICE", aGoods.get("LASTPPRICE"));//销售时不修改进价
			head.put("STOCKNUM", (Integer)aGoods.get("STOCKNUM") + (Integer)pageData.get("PNUMBER"));
			dao.update("GoodsMapper.editStocknumAndLastprice", head);
			
			//=========================操作库存表===================
			//把仓库中的库存增加销售单商品的数量
			head.put("STOCK", (Integer)aGood.get("STOCK") + (Integer)pageData.get("PNUMBER"));
			dao.update("StockMapper.edit", head);
		}
		
		PageData pd1=(PageData)dao.findForObject("SalebillMapper.findAllById", pd);//查询单据是销售退货单还是销售单
		if(pd1.getString("BILLTYPE").equals("8")){
			//作废单保留反审前状态，也就是更新反审前的单据，把单据状态改为作废即可   update
			pd.put("BILLSTATUS", 2);//把销售单的状态改为作废
			dao.update("SalebillMapper.fanshen", pd);
		}else{
			//作废单保留反审前状态，也就是更新反审前的单据，把单据状态改为作废即可   update
			pd.put("BILLSTATUS", 3);//把销售单的状态改为作废
			dao.update("SalebillMapper.fanshen", pd);
			//新的单属于插入，主键、单据编号、状态为未审核               insert
			//表头
			newHead.put("SALEBILL_ID", UuidUtil.get32UUID());
			newHead.put("BILLSTATUS", 1);
			String[] strs = productBillCodeUtil.getBillCode(Const.BILLCODE_SALEBILL_PFIX); //获取该编号类型的最大编号
			newHead.put("BILLCODE", strs[0]);
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
			dao.save("SalebillMapper.save", newHead);
			//表体
			for (PageData pageData : goodslist) {
				pageData.put("SALEBILL_ID", newHead.getString("SALEBILL_ID"));
				pageData.put("FGROUP_ID", UuidUtil.get32UUID());
				dao.save("SalebillBodyMapper.save", pageData);
			}
		}
	}


}
