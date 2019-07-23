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
			pageData.put("UNITPRICE_ID", agoods[2]);
			pageData.put("PNUMBER", agoods[3]);
			pageData.put("AMOUNT", agoods[5]);
			pageData.put("NOTE", agoods[6]);
			
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
			pageData.put("UNITPRICE_ID", agoods[2]);
			pageData.put("PNUMBER", agoods[3]);
			pageData.put("AMOUNT", agoods[5]);
			pageData.put("NOTE", agoods[6]);
			
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
	public List<PageData> listForSuppset(Page page)throws Exception{
		List<PageData> list = (List<PageData>)dao.findForList("InOrderMapper.datalistPageBySuppset", page);
		/*for(int i = 0; i < list.size(); i++) {
			PageData pd = new PageData();
			pd.put("PK_SOBOOKS", list.get(i).get("PK_SOBOOKS"));
			pd.put("ISSETTLEMENTED", 2);//更改进货单状态，表示当前进货已处于结算中状态
			pd.put("INORDER_ID",list.get(i).get("INORDER_ID"));
			dao.update("InOrderMapper.updateSettleStatus", pd);
		}*/
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
		result.put("goodslist", (List<PageData>)dao.findForList("InOrderBodyMapper.findInBodyById", pd));
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
	public void shenpi(PageData pd) throws Exception {
		dao.update("InOrderMapper.shenpi", pd);
	}

	/**
	 * 反审进货单
	 */
	@Override
	public void fanshen(PageData pd) throws Exception {
		dao.update("InOrderMapper.fanshen", pd);
	}

	/**
	 * 批量审批
	 * DATA_IDS   主键 
	 * PK_SOBOOKS  帐套主键
	 */
	@Override
	public void fanshenAll(String[] arrayDATA_IDS) throws Exception {
		StringBuffer idstr = new StringBuffer("");
		for(int i = 0; i < arrayDATA_IDS.length; i++) {
			idstr.append("'"+arrayDATA_IDS[i]+"',");
		}
		//表名和主键字段名
		jdbcTempUtil.shenpiAll(idstr.toString().substring(0,idstr.toString().length()-1), (String)Jurisdiction.getSession().getAttribute(Const.SESSION_PK_SOBOOKS), "psi_inorder", "BILLSTATUS");

	}
	
}

