package com.psi.service.inventorymanagement.reinorder.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psi.dao.DaoSupport;
import com.psi.entity.Page;
import com.psi.entity.PsiBillCode;
import com.psi.service.inventorymanagement.reinorder.ReInOrderManager;
import com.psi.service.system.BillCodePsi.BillCodeManager;
import com.psi.util.Const;
import com.psi.util.JdbcTempUtil;
import com.psi.util.PageData;
import com.psi.util.ProductBillCodeUtil;
import com.psi.util.UuidUtil;

@Service
@SuppressWarnings("unchecked")
public class ReInOrderService implements ReInOrderManager{

	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	
	//用于批量删除
	@Autowired
	private JdbcTempUtil jdbcTempUtil;
	
	@Autowired
	private ProductBillCodeUtil productBillCodeUtil;
	
	@Resource(name="billCodeService")
	private BillCodeManager billCodeService;
	
	@Override
	public List<PageData> list(Page page) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("ReInOrderMapper.datalistPage", page);
	}
	
	@Override
	public void updatefanshen(PageData pd) throws Exception {
		/**
		 * 反审进货单
		 */
	
			//把进货单的状态改为未审核
			dao.update("InOrderMapper.fanshen", pd);
			//获取进货单表头数据
			PageData head =  (PageData)dao.findForObject("InOrderMapper.findById", pd);
			//通过进货单ID获取该进货单的商品信息
			List<PageData> goodslist = (List<PageData>)dao.findForList("InOrderBodyMapper.findById", pd);
			
			//依次把商品数量在 仓库-商品 表中删去
			for (PageData pageData : goodslist) {
				
				
				//=========================修改商品表的平均成本价===================
				HashMap map=new HashMap();
				map.put("GOODCODE",  pageData.get("GOODCODE_ID"));
				map.put("PK_SOBOOKS", pageData.get("PK_SOBOOKS"));
				PageData gpd=(PageData)dao.findForObject("GoodsMapper.findDefAndNumByCode", map); //查询数据库商品的平均成本价和库存
				
				int STOCKNUM= (Integer)gpd.get("STOCKNUM");
				double price;
				if(gpd.getString("DEF1")!=null){
				    price= Double.parseDouble(gpd.getString("DEF1"));
				}else{
					price=0;
				}
				int num=(Integer)pageData.get("PNUMBER");
				double amount=(Double)pageData.get("AMOUNT");
				if((price*STOCKNUM-amount)==0 || (STOCKNUM-num)==0){
					price=0;
				}else{
					price=(price*STOCKNUM-amount)/(STOCKNUM-num);
				}
				map.put("price", price);
				map.put("GOODCODE", pageData.get("GOODCODE_ID"));
				dao.save("GoodsMapper.updateDEF1ByCode", map);
				
				/////////////////////////////////////
				head.put("WAREHOUSE_ID", pageData.get("WAREHOUSE_ID"));
				head.put("GOOD_ID", pageData.get("GOODCODE_ID"));
				//获取原来的库存
				PageData aGood =  (PageData)dao.findForObject("StockMapper.findByWarehouseAndGood", head);
				
				//=========================操作商品表===================
				//更新最后进价 和 库存总数量
				PageData aGoods =  (PageData)dao.findForObject("GoodsMapper.findByGOODCODE", head);
				head.put("LASTPPRICE", aGoods.get("LASTPPRICE"));
				head.put("STOCKNUM", (Integer)aGoods.get("STOCKNUM") - (Integer)pageData.get("PNUMBER"));
				dao.update("GoodsMapper.editStocknumAndLastprice", head);
				
				//=========================操作库存表===================
				//把仓库中的库存减去进货单商品的数量
				if(aGood!=null){
					head.put("STOCK", (Integer)aGood.get("STOCK") - (Integer)pageData.get("PNUMBER"));
				}
				dao.update("StockMapper.edit", head);
			}
		
		
	}
	
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void save(PageData pd)throws Exception{
		String[] strs = productBillCodeUtil.getBillCode(Const.BILLCODE_SUPPRETURN_PFIX); //获取该编号类型的最大编号
		pd.put("BILLCODE", strs[0]);
		//保存商品
		String goodslist = (String) pd.get("goodslist");
		String[] split = goodslist.split("#");
		//遍历每行数据
		for(int i = 0; i < split.length; i++) {
			String[] agoods = split[i].split(",");
			PageData pageData = new PageData();
			pageData.put("INORDERBODY_ID", UuidUtil.get32UUID());
			pageData.put("INORDER_ID", pd.get("INORDER_ID"));
			pageData.put("PK_SOBOOKS", pd.get("PK_SOBOOKS"));
			pageData.put("APPBILLNO", strs[0]);
			
			pageData.put("GOODCODE_ID", agoods[1]);
			pageData.put("WAREHOUSE_ID", agoods[2]);
			pageData.put("UNITPRICE_ID", agoods[3]);
			pageData.put("PNUMBER", agoods[4]);
			pageData.put("AMOUNT", agoods[6]);
			
			if(agoods.length==8){ //如果BARCODE有值，agoods的长度是9
				pageData.put("NOTE", agoods[7]);
			}else{  //否则长度为8
				pageData.put("NOTE", "");
			}
			
			if(agoods.length==9){ //如果BARCODE有值，agoods的长度是9
				pageData.put("BARCODE", agoods[8]);
			}else{  //否则长度为8
				pageData.put("BARCODE", null);
			}
			
			dao.save("InOrderBodyMapper.save", pageData);
		}
		dao.save("InOrderMapper.save", pd);
		//保存进货单编号
		if(strs[1] == null){ //新增
			PsiBillCode psiBillCode = new PsiBillCode();
			psiBillCode.setCode_ID(UuidUtil.get32UUID());
			psiBillCode.setCodeType(Const.BILLCODE_SUPPRETURN_PFIX);
			psiBillCode.setMaxNo(strs[0]);
			psiBillCode.setNOTE("退货编号");
			billCodeService.insertBillCode(psiBillCode);
		}else{//修改
			PageData ppp = new PageData();
			ppp.put("MaxNo",strs[0]);
			ppp.put("Code_ID", strs[1]);
			billCodeService.updateMaxNo(ppp);
		}
	}
	
	
}
