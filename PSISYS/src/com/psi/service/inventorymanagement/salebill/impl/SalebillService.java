package com.psi.service.inventorymanagement.salebill.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.psi.service.inventorymanagement.salebill.SalebillManager;
import com.psi.service.system.BillCodePsi.BillCodeManager;
import com.psi.util.Const;
import com.psi.util.JdbcTempUtil;
import com.psi.util.Jurisdiction;
import com.psi.util.PageData;
import com.psi.util.ProductBillCodeUtil;
import com.psi.util.UuidUtil;

/**
 * 说明： 销售单管理
 */
@Service("salebillService")
public class SalebillService implements SalebillManager{
	
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
	@Override
	public PageData save(PageData pd) throws Exception {
		String[] strs = productBillCodeUtil.getBillCode(Const.BILLCODE_SALEBILL_PFIX); //获取该编号类型的最大编号
		pd.put("BILLCODE", strs[0]);
		//保存商品
		String goodslist = (String) pd.get("goodslist");
		String[] split = goodslist.split("#");
		//遍历每行数据
		for(int i = 0; i < split.length; i++) {
			String[] agoods = split[i].split(",");
			PageData pageData = new PageData();   //[白铜, 190016, f5a0a40208c04725b7ecae3af9e8cba2, 60.0, 10, 公斤, 600, 1, 23] 
			pageData.put("FGROUP_ID", UuidUtil.get32UUID()); //[白铜, , f5a0a40208c04725b7ecae3af9e8cba2, 60.0, 10, 公斤, 600, 1, 12, 190016]
			pageData.put("SALEBILL_ID", pd.get("SALEBILL_ID"));
			pageData.put("PK_SOBOOKS", pd.get("PK_SOBOOKS"));
			pageData.put("APPBILLNO", strs[0]);
			
			pageData.put("GOODCODE_ID", agoods[1]);
			pageData.put("WAREHOUSE_ID", agoods[2]);
			pageData.put("UNITPRICE_ID", agoods[3]);
			pageData.put("PNUMBER", agoods[4]);
			pageData.put("AMOUNT", agoods[6]);
			pageData.put("ISFREE", agoods[7]);
			if(agoods.length==9){ //如果BARCODE有值，agoods的长度是9
				pageData.put("NOTE", agoods[8]);
			}else{  //否则长度为8
				pageData.put("NOTE", "");
			}
			
			if(agoods.length==10){ //如果BARCODE有值，agoods的长度是10
				pageData.put("BARCODE", agoods[9]);
			}else{  //否则长度为9
				pageData.put("BARCODE", null);
			}
			
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

	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.update("SalebillMapper.delete", pd);
		dao.update("SalebillBodyMapper.delete", pd);
	}
	/**
	 * 批量结算销售单
	 */
	@Override
	public void settleSalebills(List<PageData> inorderandbodylist) throws Exception {
		for(int i = 0; i < inorderandbodylist.size(); i++) {
			PageData pd = inorderandbodylist.get(i);
			dao.update("SalebillMapper.updateForSettle", pd);
		}
		
	}
	
	/**
	 * 根据供应商结算单主键获取其销售单，只有结算才会有
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<PageData> listForByCustomersetId(PageData pd) throws Exception {
		List<PageData> list = (List<PageData>)dao.findForList("SalebillMapper.listForByCustomersetId", pd);
		for(int i = 0; i < list.size(); i++) {
			list.get(i).put("bodylist", (List<PageData>)dao.findForList("SalebillBodyMapper.findById", list.get(i)));
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
		dao.update("SalebillBodyMapper.delete", pd);
		//重新加入商品
		String goodslist = (String) pd.get("goodslist");
		String[] split = goodslist.split("#");
		for(int i = 0; i < split.length; i++) {
			String[] agoods = split[i].split(",");
			PageData pageData = new PageData();
			pageData.put("FGROUP_ID", UuidUtil.get32UUID());
			pageData.put("SALEBILL_ID", pd.get("SALEBILL_ID"));
			pageData.put("PK_SOBOOKS", pd.get("PK_SOBOOKS"));
			pageData.put("APPBILLNO", pd.get("BILLCODE"));
			
			
			pageData.put("GOODCODE_ID", agoods[1]);
			pageData.put("WAREHOUSE_ID", agoods[2]);
			pageData.put("UNITPRICE_ID", agoods[3]);
			pageData.put("PNUMBER", agoods[4]);
			pageData.put("AMOUNT", agoods[6]);
			pageData.put("ISFREE", agoods[7]);
			
			if(agoods.length==9){ //如果BARCODE有值，agoods的长度是9
				pageData.put("NOTE", agoods[8]);
			}else{  //否则长度为8
				pageData.put("NOTE", "");
			}
			
			if(agoods.length==10){ //如果BARCODE有值，agoods的长度是10
				pageData.put("BARCODE", agoods[9]);
			}else{  //否则长度为9
				pageData.put("BARCODE", null);
			}
			dao.save("SalebillBodyMapper.save", pageData);
		}
		dao.update("SalebillMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SalebillMapper.datalistPage", page);
	}
	/**
	 * 结算单里获取销售单列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listForCustomer(PageData pd)throws Exception{
		pd = (PageData)dao.findForObject("CustomersetbillMapper.findById", pd);
		String SALEBILL_IDS  = (String) pd.get("SALEBILL_IDS");
		List<PageData> list = new ArrayList<PageData>();
		if(SALEBILL_IDS != "" && !"".equals(SALEBILL_IDS) && SALEBILL_IDS != null) {
			String[] sbids = SALEBILL_IDS.split(",");
			for(int i = 0 ; i < sbids.length; i++) {
				PageData salebillandbody = new PageData();
				salebillandbody.put("SALEBILL_ID",sbids[i].substring(1, sbids[i].length()-1) );
				list.add((PageData)dao.findForObject("SalebillMapper.findBySalebillId", salebillandbody));
			}
		}
		return list;
	}
	/**
	 * 结算单新增功能里的供应商选择拉出销售单
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listForCustomerAdd(Page page)throws Exception{
		List<PageData> list = (List<PageData>)dao.findForList("SalebillMapper.datalistPageByCustomerset", page);
		return list;
	}
	/**
	 * 根据表头主键查询主子表 ，所有字段，备份时调用到
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findAllById(PageData pd)throws Exception{
		PageData result =  (PageData)dao.findForObject("SalebillMapper.findAllById", pd);
		List<PageData> list = (List<PageData>)dao.findForList("SalebillBodyMapper.findInBodyById", pd);
		result.put("goodslist", list );
		return result;
	}
	
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SalebillMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		PageData result =  (PageData)dao.findForObject("SalebillMapper.findById", pd);
		result.put("goodslist", (List<PageData>)dao.findForList("SalebillBodyMapper.findById", pd));
		
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
//		dao.update("SalebillMapper.deleteAll", ArrayDATA_IDS);
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

	/**结算单反审销售单功能
	 * @param
	 * @throws Exception
	 */
	public void retrialInorder(PageData pd) throws Exception {
		dao.update("SalebillMapper.retrialInorder", pd);
	}

	/**结算单结算一张销售单功能
	 * @param
	 * @throws Exception
	 */
	public PageData settleOneSalebill(PageData pd) throws Exception {
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
		dao.update("SalebillMapper.settleOneSalebill", pd);
		return pd;
	}

	/**结算单批量结算销售单功能
	 * @param
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HashMap> settleAllSalebill(PageData pd) throws Exception {
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
				dao.update("SalebillMapper.settleOneSalebill", map);
			}
		}
		return list;
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

	/**
	 * 反审销售单
	 */
	@Override
	public void updatefanshen(PageData pd) throws Exception {
		
		String NOTE=pd.getString("NOTE");
		String SALEBILL_ID= pd.getString("SALEBILL_ID");
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
		//作废单备注
		pd.put("NOTE", NOTE);
		pd.put("SALEBILL_ID", SALEBILL_ID);
		dao.update("SalebillMapper.updateNoteByCode", pd);
	}

	/**
	 * 批量审批
	 * DATA_IDS   主键 
	 * PK_SOBOOKS  帐套主键
	 */
	@Override
	public void updateshenpiAll(PageData pd) throws Exception {
		
		//循环遍历销售单
		String DATA_IDS = pd.getString("DATA_IDS");
		String ArrayDATA_IDS[] = DATA_IDS.split(",");
		for (String string : ArrayDATA_IDS) {
			pd.put("SALEBILL_ID", string);
			updateshenpi(pd);
		}
		
		
//		StringBuffer idstr = new StringBuffer("");
//		for(int i = 0; i < arrayDATA_IDS.length; i++) {
//			idstr.append("'"+arrayDATA_IDS[i]+"',");
//		}
		//更新销售单的审批状态
//		jdbcTempUtil.shenpiAllOrder(idstr.toString().substring(0,idstr.toString().length()-1), (String)Jurisdiction.getSession().getAttribute(Const.SESSION_PK_SOBOOKS), "psi_inorder", "INORDER_ID");
		
		//更新库存的数量
		
	}

	@Override
	public void editFromCustomer(PageData pd) throws Exception {
		dao.update("SalebillMapper.editFromCustomer", pd);
	}

	/**
	 * 检查指定库存是否存在指定商品
	 * @param pd  仓库ID  商品编号GOOD_ID
	 * @return
	 * @throws Exception 
	 */
	@Override
	public PageData getStock(PageData pd) throws Exception {
		PageData result = (PageData)dao.findForObject("StockMapper.getStock", pd);
		return result;
	}

	/**
	 * 超期查询
	 */
	@Override
	public List<PageData> listForPassTimeSaleBill(Page page) throws Exception {
		return (List<PageData>)dao.findForList("SalebillMapper.datalistPageForPassTimeSaleBill", page);
	}
	
	/**
	 * 检查客户超期未付总金额以及信誉额度
	 * 输入参数：客户id、帐套id
	 * 返回：客户超期未付总金额、信誉额度
	 */
	@Override
	public PageData customerunpaidandgreen(PageData pd) throws Exception {
		return (PageData)dao.findForObject("SalebillMapper.customerunpaidandgreen", pd);
	}
	
	
	@Override
	public List<PageData> listSaleInfo(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("SalebillMapper.getSaleInfo", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> salebillListBody(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("SalebillBodyMapper.findById", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listInOderSale(Page page) throws Exception {
		return (List<PageData>)dao.findForList("SalebillMapper.datalistPageByOderSale", page);
	}

	@Override
	public List<PageData> listSalebillByCondition(Page page) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("SalebillMapper.datalistPageBySalebill", page);
	}

	public List<PageData> listSalebillByID(Page page) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("SalebillMapper.datalistPageByID", page);
	}


	@Override
	public List<PageData> printSalebill(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("SalebillBodyMapper.printSalebill", pd);
	}


	@Override
	public List<PageData> listByCustomer(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return  (List<PageData>)dao.findForList("SalebillMapper.listByCustomer", pd);
	}


	@Override
	public List<PageData> listPassTimeSaleBill(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("SalebillMapper.listPassTimeSaleBill", pd);
	}


	@Override
	public List<PageData> listsalebillByGoodCode(Page page) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("SalebillMapper.datalistPageByGoodCode", page);
	}

	
}

