package com.psi.service.inventorymanagement.salebill.impl;

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
	public void save(PageData pd)throws Exception{
		String[] strs = productBillCodeUtil.getBillCode(Const.BILLCODE_SALEBILL_PFIX); //获取该编号类型的最大编号
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
			
			pageData.put("BARCODE", agoods[1]);
			pageData.put("UNITPRICE_ID", agoods[2]);
			pageData.put("PNUMBER", agoods[3]);
			pageData.put("AMOUNT", agoods[5]);
			pageData.put("NOTE", agoods[6]);
			pageData.put("GOODCODE_ID", agoods[7]);
			
			dao.save("SalebillBodyMapper.save", pageData);
		}
		dao.save("SalebillMapper.save", pd);
		//保存销售单编号
		if(strs[1] == null){ //新增
			PsiBillCode psiBillCode = new PsiBillCode();
			psiBillCode.setCode_ID(UuidUtil.get32UUID());
			psiBillCode.setCodeType(Const.BILLCODE_INORDER_PFIX);
			psiBillCode.setMaxNo(strs[0]);
			psiBillCode.setNOTE("销售单编号");
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
			pageData.put("INORDERBODY_ID", UuidUtil.get32UUID());
			pageData.put("INORDER_ID", pd.get("INORDER_ID"));
			pageData.put("PK_SOBOOKS", pd.get("PK_SOBOOKS"));
			pageData.put("APPBILLNO", pd.get("BILLCODE"));
			
			
			pageData.put("BARCODE", agoods[1]);
			pageData.put("UNITPRICE_ID", agoods[2]);
			pageData.put("PNUMBER", agoods[3]);
			pageData.put("AMOUNT", agoods[5]);
			pageData.put("NOTE", agoods[6]);
			pageData.put("GOODCODE_ID", agoods[7]);
			
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
		String[] sbids = SALEBILL_IDS.split(",");
		List<PageData> list = new ArrayList<PageData>();
		for(int i = 0 ; i < sbids.length; i++) {
			PageData salebillandbody = new PageData();
			salebillandbody.put("INORDER_ID",sbids[i].substring(1, sbids[i].length()-1) );
			list.add((PageData)dao.findForObject("SalebillMapper.findBySalebillId", salebillandbody));
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
		dao.update("SalebillMapper.shenpi", pd);
		//获取销售单表头数据
		PageData head =  (PageData)dao.findForObject("SalebillMapper.findById", pd);
		//通过销售单ID获取该销售单的商品信息
		List<PageData> goodslist = (List<PageData>)dao.findForList("SalebillBodyMapper.findById", pd);
		
		//依次把商品数量添加到 仓库-商品 表中和商品的总数量中
		for (PageData pageData : goodslist) {
			//把商品的编号加入到查询条件
			head.put("GOOD_ID", pageData.get("GOODCODE_ID"));
			
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
			PageData aGood =  (PageData)dao.findForObject("Warehouse_Good_Mapper.findByWarehouseAndGood", head);
			//有，把数量更新
			if(aGood != null) {
				//把仓库中的库存加上销售单商品的数量
				head.put("STOCK", (Integer)aGood.get("STOCK") + (Integer)pageData.get("PNUMBER"));
				dao.update("Warehouse_Good_Mapper.edit", head);
			}
			//没有，新增数据
			else {
				head.put("WAREHOUSE_GOOD_ID", UuidUtil.get32UUID());
				head.put("STOCK", pageData.get("PNUMBER"));
				dao.save("Warehouse_Good_Mapper.save", head);
			}
			
		}
	}

	/**
	 * 反审销售单
	 */
	@Override
	public void updatefanshen(PageData pd) throws Exception {
		//把销售单的状态改为未审核
		dao.update("SalebillMapper.fanshen", pd);
		//获取销售单表头数据
		PageData head =  (PageData)dao.findForObject("SalebillMapper.findById", pd);
		//通过销售单ID获取该销售单的商品信息
		List<PageData> goodslist = (List<PageData>)dao.findForList("SalebillBodyMapper.findById", pd);
		
		//依次把商品数量在 仓库-商品 表中删去
		for (PageData pageData : goodslist) {
			head.put("GOOD_ID", pageData.get("GOODCODE_ID"));
			//获取原来的库存
			PageData aGood =  (PageData)dao.findForObject("Warehouse_Good_Mapper.findByWarehouseAndGood", head);
			
			//=========================操作商品表===================
			//更新最后进价 和 库存总数量
			PageData aGoods =  (PageData)dao.findForObject("GoodsMapper.findByGOODCODE", head);
			head.put("STOCKNUM", (Integer)aGoods.get("STOCKNUM") - (Integer)pageData.get("PNUMBER"));
			dao.update("GoodsMapper.editStocknumAndLastprice", head);
			
			//=========================操作库存表===================
			//把仓库中的库存减去销售单商品的数量
			head.put("STOCK", (Integer)aGood.get("STOCK") - (Integer)pageData.get("PNUMBER"));
			dao.update("Warehouse_Good_Mapper.edit", head);
		}
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
			pd.put("INORDER_ID", string);
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
	
}

