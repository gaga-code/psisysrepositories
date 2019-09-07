package com.psi.service.inventorymanagement.inorder.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.psi.dao.DaoSupport;
import com.psi.entity.Page;
import com.psi.entity.PsiBillCode;
import com.psi.service.inventorymanagement.inorder.InOrderManager;
import com.psi.service.system.BillCodePsi.BillCodeManager;
import com.psi.util.Const;
import com.psi.util.JdbcTempUtil;
import com.psi.util.Jurisdiction;
import com.psi.util.PageData;
import com.psi.util.ProductBillCodeUtil;
import com.psi.util.UuidUtil;

/**
 * 说明： 进货单管理
 */
@Service("inOrderService")
public class InOrderService implements InOrderManager{
	
	//用于批量删除
	@Autowired
	private JdbcTempUtil jdbcTempUtil;
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Autowired
	private ProductBillCodeUtil productBillCodeUtil;
	
	@Resource(name="billCodeService")
	private BillCodeManager billCodeService;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		String[] strs = productBillCodeUtil.getBillCode(Const.BILLCODE_INORDER_PFIX); //获取该编号类型的最大编号
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
			pageData.put("AMOUNT", agoods[8]);
			
			if(agoods.length==10){ //如果BARCODE有值，agoods的长度是9
				pageData.put("NOTE", agoods[9]);
			}else{  //否则长度为8
				pageData.put("NOTE", "");
			}
			
			if(agoods.length==11){ //如果BARCODE有值，agoods的长度是9
				pageData.put("BARCODE", agoods[10]);
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
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.update("InOrderMapper.delete", pd);
		dao.update("InOrderBodyMapper.delete", pd);
	}
	/**
	 * 批量结算进货单
	 */
	@Override
	public void settleInOrders(List<PageData> inorderandbodylist) throws Exception {
		for(int i = 0; i < inorderandbodylist.size(); i++) {
			PageData pd = inorderandbodylist.get(i);
			dao.update("InOrderMapper.updateForSettle", pd);
		}
		
	}
	
	/**
	 * 根据供应商结算单主键获取其进货单，只有结算才会有
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<PageData> listForBySuppsetId(PageData pd) throws Exception {
		List<PageData> list = (List<PageData>)dao.findForList("InOrderMapper.listForBySuppsetId", pd);
		for(int i = 0; i < list.size(); i++) {
			list.get(i).put("bodylist", (List<PageData>)dao.findForList("InOrderBodyMapper.findById", list.get(i)));
		}
		return list;
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		//----------------编辑商品-------
		//删除本来的数据
		dao.update("InOrderBodyMapper.delete", pd);
		//重新加入商品
		String goodslist = (String) pd.get("goodslist");
		String[] split = goodslist.split("#");
		for(int i = 0; i < split.length; i++) {
			String[] agoods = split[i].split(",");
			PageData pageData = new PageData();
			pageData.put("INORDERBODY_ID", UuidUtil.get32UUID());
			pageData.put("INORDER_ID", pd.get("INORDER_ID"));
			pageData.put("PK_SOBOOKS", pd.get("PK_SOBOOKS"));
			pageData.put("APPBILLNO", pd.get("BILLCODE"));
			
			
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
		dao.update("InOrderMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("InOrderMapper.datalistPage", page);
	}
	/**
	 * 结算单里获取进货单列表，并对每张进货单更改结算状态为 结算中 状态
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listForSuppset(PageData pd)throws Exception{
		pd = (PageData)dao.findForObject("SuppsetbillMapper.findById", pd);
		String inorder_ids  = (String) pd.get("INORDER_IDS");
		List<PageData> list = new ArrayList<PageData>();
		if(inorder_ids != "" && !"".equals(inorder_ids) && inorder_ids != null) {
			String[] ioids = inorder_ids.split(",");
			for(int i = 0 ; i < ioids.length; i++) {
				PageData inorderandbody = new PageData();
				inorderandbody.put("INORDER_ID",ioids[i].substring(1, ioids[i].length()-1) );
				list.add((PageData)dao.findForObject("InOrderMapper.findByInOrderId", inorderandbody));
			}
		}
		return list;
	}
	/**
	 * 结算单新增功能里的供应商选择拉出进货单
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listForSuppAdd(Page page)throws Exception{
		List<PageData> list = (List<PageData>)dao.findForList("InOrderMapper.datalistPageBySuppset", page);
		return list;
	}
	/**
	 * 根据表头主键查询主子表 ，所有字段，备份时调用到
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findAllById(PageData pd)throws Exception{
		PageData result =  (PageData)dao.findForObject("InOrderMapper.findAllById", pd);
		List<PageData> list = (List<PageData>)dao.findForList("InOrderBodyMapper.findInBodyById", pd);
		result.put("goodslist", list );
		return result;
	}
	
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("InOrderMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		PageData result =  (PageData)dao.findForObject("InOrderMapper.findById", pd);
		result.put("goodslist", (List<PageData>)dao.findForList("InOrderBodyMapper.findById", pd));
		
		PageData map = new PageData(); //查询条件
		PageData WareHouse = new PageData(); //仓库
		PageData temp = new PageData(); //临时保存查询到的仓库ID，仓库名，库存
		map.put("PK_SOBOOKS", pd.get("PK_SOBOOKS"));
		for (PageData inorder : (List<PageData>)result.get("goodslist")) {
			map.put("GOOD_ID", inorder.get("GOODCODE_ID"));
			String WH = (String)inorder.get("WAREHOUSE_IDs");
			String[] strings = WH.split(",");
			String WAREHOUSE_ID_NAME_STOCK = "";
			for (String WID : strings) {
				map.put("WAREHOUSE_ID", WID);
				temp = (PageData)dao.findForObject("StockMapper.findByWarehouseAndGood", map);
				WareHouse = (PageData)dao.findForObject("WarehouseMapper.findById", map);
				Integer stock = 0;
				String  WHNAME = (String) WareHouse.get("WHNAME");
				if(temp != null) {
					stock =  (Integer) temp.get("STOCK");
				}
					WAREHOUSE_ID_NAME_STOCK = WAREHOUSE_ID_NAME_STOCK + WID + "," + WHNAME +"," + stock + "#";
			}
			inorder.put("WAREHOUSE_ID_NAME_STOCK", WAREHOUSE_ID_NAME_STOCK);
		}
		return result;
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
//	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
//		dao.update("InOrderMapper.deleteAll", ArrayDATA_IDS);
//	}
	
	

	/**
	 * 批量删除
	 * DATA_IDS   主键 
	 * PK_SOBOOKS  帐套主键
	 */
	public void deleteAll(String ArrayDATA_IDS[])throws Exception{
		StringBuffer idstr = new StringBuffer("");
		for(int i = 0; i < ArrayDATA_IDS.length; i++) {
			idstr.append("'"+ArrayDATA_IDS[i]+"',");
		}
		//表名和主键字段名
		jdbcTempUtil.deleteAll(idstr.toString().substring(0,idstr.toString().length()-1), (String)Jurisdiction.getSession().getAttribute(Const.SESSION_PK_SOBOOKS), "psi_inorder", "INORDER_ID");
		jdbcTempUtil.deleteAll(idstr.toString().substring(0,idstr.toString().length()-1), (String)Jurisdiction.getSession().getAttribute(Const.SESSION_PK_SOBOOKS), "psi_inorder_body", "INORDER_ID");
	}

	/**结算单反审进货单功能
	 * @param
	 * @throws Exception
	 */
	public void retrialInorder(PageData pd) throws Exception {
		dao.update("InOrderMapper.retrialInorder", pd);
	}

	/**结算单结算一张进货单功能
	 * @param
	 * @throws Exception
	 */
	public PageData settleOneInOrder(PageData pd) throws Exception {
		double canpaid = 0.0;
		double unpaid = 0.0;
		if(pd.containsKey("CANPAID")) {
			canpaid = Double.parseDouble((String)pd.get("CANPAID"));
		}
		if(pd.containsKey("UNPAIDAMOUNT")) {
			unpaid = Double.parseDouble((String)pd.get("UNPAIDAMOUNT"));
		}
		if(canpaid < unpaid) {//未结算
			pd.put("ISSETTLEMENTED", 0);
		}else {//能结算
			pd.put("ISSETTLEMENTED", 1);
		}
		pd.put("UNPAIDAMOUNT", unpaid - canpaid);
		pd.put("PAIDAMOUNT", Double.parseDouble((String)pd.get("PAIDAMOUNT")) + canpaid);
		pd.put("THISPAY", canpaid);
		dao.update("InOrderMapper.settleOneInOrder", pd);
		return pd;
	}

	/**结算单批量结算进货单功能
	 * @param
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HashMap> settleAllInOrder(PageData pd) throws Exception {
		List<HashMap> list = new ArrayList<HashMap>();
		String createArr = (String) pd.get("SettleList");
		if(createArr!="" || createArr != null){
			JSONArray pa = JSONArray.parseArray(createArr);
			for(int i=0; i < pa.size(); i++) {
				HashMap map = new HashMap();
				String jsonString = JSONObject.toJSONString(pa.get(i));
				int issettle = JSONObject.parseObject(jsonString).getIntValue("ISSETTLEMENTED");
				Double thispay = JSONObject.parseObject(jsonString).getDouble("THISPAY");
				Double paidam = JSONObject.parseObject(jsonString).getDouble("PAIDAMOUNT");
				Double unpaid = JSONObject.parseObject(jsonString).getDouble("UNPAIDAMOUNT");
				map.put("INORDER_ID", JSONObject.parseObject(jsonString).getString("INORDER_ID"));
				map.put("THISPAY", thispay);
				if(issettle == 1) {
					map.put("UNPAIDAMOUNT", 0.0);
					map.put("PAIDAMOUNT", paidam+thispay);
				}else if(issettle == 0) {
					map.put("UNPAIDAMOUNT",unpaid-thispay);
					map.put("PAIDAMOUNT", paidam+thispay);
				}
				map.put("ALLAMOUNT", JSONObject.parseObject(jsonString).getString("ALLAMOUNT"));
				map.put("ISSETTLEMENTED", issettle);
				map.put("PK_SOBOOKS",Jurisdiction.getSession().getAttribute(Const.SESSION_PK_SOBOOKS));
				list.add(map);
				dao.update("InOrderMapper.settleOneInOrder", map);
			}
		}
		return list;
	}

	/**
	 * 审批进货单
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

	/**
	 * 反审进货单
	 */
	@Override
	public void updatefanshen(PageData pd) throws Exception {
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

	/**
	 * 批量审批
	 * DATA_IDS   主键 
	 * PK_SOBOOKS  帐套主键
	 */
	@Override
	public void updateshenpiAll(PageData pd) throws Exception {
		
		//循环遍历进货单
		String DATA_IDS = pd.getString("DATA_IDS");
		String ArrayDATA_IDS[] = DATA_IDS.split(",");
		for (String string : ArrayDATA_IDS) {
			pd.put("INORDER_ID", string);
			updateshenpi(pd);
		}
		
		
//		StringBuffer idstr = new StringBuffer("");
//		for(int i = 0; i < arrayDATA_IDS.length; i++) {
//			idstr.append("'"+arrayDATA_IDS[i]+"',");
//		}
		//更新进货单的审批状态
//		jdbcTempUtil.shenpiAllOrder(idstr.toString().substring(0,idstr.toString().length()-1), (String)Jurisdiction.getSession().getAttribute(Const.SESSION_PK_SOBOOKS), "psi_inorder", "INORDER_ID");
		
		//更新库存的数量
		
	}

	@Override
	public void editFromSupp(PageData pd) throws Exception {
		dao.update("InOrderMapper.editFromSupp", pd);
	}

	/**
	 * 根据进货单ID  获取子表详情列表
	 * @throws Exception 
	 */
	@Override
	public List<PageData> inOrderlistBody(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("InOrderBodyMapper.findById", pd);
	}

	@Override
	public List<PageData> listInOderSale(Page page) throws Exception {
		return (List<PageData>)dao.findForList("InOrderMapper.datalistPageByOderSale", page);
	}

	@Override
	public List<PageData> listInOderByCondition(Page page) throws Exception {
		
		return (List<PageData>)dao.findForList("InOrderMapper.datalistPageByCondition", page);
	}

	@Override
	public List<PageData> printInOrder(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("InOrderBodyMapper.printInOrder", pd);
	}
	
}

