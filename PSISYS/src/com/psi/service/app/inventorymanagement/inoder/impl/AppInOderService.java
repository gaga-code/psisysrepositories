package com.psi.service.app.inventorymanagement.inoder.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psi.dao.DaoSupport;
import com.psi.entity.PsiBillCode;
import com.psi.service.app.inventorymanagement.inoder.AppInOderManager;
import com.psi.service.system.BillCodePsi.BillCodeManager;
import com.psi.util.Const;
import com.psi.util.PageData;
import com.psi.util.ProductBillCodeUtil;
import com.psi.util.UuidUtil;

@Service
public class AppInOderService implements AppInOderManager {
	
	@Resource(name="daoSupport")
	private DaoSupport dao;

	
	@Autowired
	private ProductBillCodeUtil productBillCodeUtil;
	
	
	@Resource(name="billCodeService")
	private BillCodeManager billCodeService;
	
	@SuppressWarnings("unchecked")
	public List<PageData> listInOderByToday(PageData pd) throws Exception {
		
		return (List<PageData>)dao.findForList("AppInOderMapper.listInOderByToday", pd);
	}

	@Override
	public PageData listMountAndNum(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData)dao.findForObject("AppInOderMapper.listMountAndNum",pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listInOderGoods(PageData pd) throws Exception {
		
		return (List<PageData>)dao.findForList("AppInOderMapper.listInOderGoods", pd);
	}

	@Override
	public PageData listMountAndNumByMD(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData)dao.findForObject("AppInOderMapper.listMountAndNumByMD",pd);
	}

	@Override
	public List<PageData> listInOderGoodsByMD(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("AppInOderMapper.listInOderGoodsByMD",pd);
	}

	@Override
	public List<PageData> listInOrder(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("AppInOderMapper.listInOrder", pd);
	}

	@Override
	public List<PageData> listInOrderBody(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return  (List<PageData>)dao.findForList("AppInOderMapper.listInOrderBody", pd);
	}
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public PageData save(PageData pd)throws Exception{
		String[] strs = productBillCodeUtil.getBillCode(Const.BILLCODE_INORDER_PFIX); //获取该编号类型的最大编号
		pd.put("BILLCODE", strs[0]);
		//保存商品
		String goodslist = (String) pd.get("inorderbody");
		String[] split = goodslist.split("#");
		//遍历每行数据
		for(int i = 0; i < split.length; i++) {
			String[] agoods = split[i].split(",");
			PageData pageData = new PageData();
			pageData.put("INORDERBODY_ID", UuidUtil.get32UUID());
			pageData.put("INORDER_ID", pd.get("INORDER_ID"));
			pageData.put("PK_SOBOOKS", pd.get("PK_SOBOOKS"));
			pageData.put("APPBILLNO", strs[0]);
			
			pageData.put("BARCODE", agoods[1]);
			pageData.put("WAREHOUSE_ID", agoods[2]);
			pageData.put("UNITPRICE_ID", agoods[3]);
			pageData.put("PNUMBER", agoods[4]);
			pageData.put("AMOUNT", agoods[5]);
			pageData.put("NOTE", agoods[6]);
			
			if(agoods.length==8){ //如果BARCODE有值，agoods的长度是9
				pageData.put("GOODCODE_ID", agoods[7]);
			}else{  //否则长度为8
				pageData.put("GOODCODE_ID", null);
			}
			
			dao.save("InOrderBodyMapper.save", pageData);
		}
		dao.save("InOrderMapper.save", pd);
		//保存进货单编号
		if(strs[1] == null){ //新增
			PsiBillCode psiBillCode = new PsiBillCode();
			psiBillCode.setCode_ID(UuidUtil.get32UUID());
			psiBillCode.setCodeType(Const.BILLCODE_INORDER_PFIX);
			psiBillCode.setMaxNo(strs[0]);
			psiBillCode.setNOTE("进货单编号");
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
	public int listInOrderNum(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (Integer)dao.findForObject("AppInOderMapper.listInOrderNum", pd);
	}
	

	/**
	 * 审批退货单
	 */
	@Override
	public void updateshenpi(PageData pd) throws Exception {
		
		//把进货单的状态改为已审核
		dao.update("InOrderMapper.shenpi", pd);
		//获取进货单表头数据
		PageData head =  (PageData)dao.findForObject("InOrderMapper.findById", pd);
		//通过进货单ID获取该进货单的商品信息
		List<PageData> goodslist = (List<PageData>)dao.findForList("InOrderBodyMapper.findById", pd);
		
		//依次把商品数量添加到 仓库-商品 表中和商品的总数量中
		for (PageData pageData : goodslist) {
			//把商品的编号加入到查询条件
			head.put("GOOD_ID", pageData.get("GOODCODE_ID"));
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
			price=(price*STOCKNUM+amount)/(num+STOCKNUM);
			
			map.put("price", price);
			map.put("GOODCODE", pageData.get("GOODCODE_ID"));
			dao.save("GoodsMapper.updateDEF1ByCode", map);
			//=========================操作商品表===================
			//更新最后进价 和 库存总数量
			PageData aGoods =  (PageData)dao.findForObject("GoodsMapper.findByGOODCODE", head);
			head.put("LASTPPRICE", pageData.get("UNITPRICE_ID"));
			head.put("STOCKNUM", (Integer)aGoods.get("STOCKNUM") + (Integer)pageData.get("PNUMBER"));
			dao.update("GoodsMapper.editStocknumAndLastprice", head);
			
			//=========================操作进价表===================
			//直接插入一条价格数据
			head.put("INCOMERECORD_ID", UuidUtil.get32UUID());
			head.put("INCOME", pageData.get("UNITPRICE_ID"));
			dao.save("IncomerecordMapper.save", head);
			
			//=========================操作库存表===================
			//先查看 仓库-商品 表中是否包含相应的 仓库-商品
			head.put("WAREHOUSE_ID", pageData.get("WAREHOUSE_ID"));
			PageData aGood =  (PageData)dao.findForObject("StockMapper.findByWarehouseAndGood", head);
			//有，把数量更新
			if(aGood != null) {
				//把仓库中的库存加上进货单商品的数量
				head.put("STOCK", (Integer)aGood.get("STOCK") + (Integer)pageData.get("PNUMBER"));
				dao.update("StockMapper.edit", head);
			}
			//没有，新增数据
			else {
				head.put("WAREHOUSE_GOOD_ID", UuidUtil.get32UUID());
				head.put("STOCK", pageData.get("PNUMBER"));
				dao.save("StockMapper.save", head);
			}
			
		}
	}

	
}
