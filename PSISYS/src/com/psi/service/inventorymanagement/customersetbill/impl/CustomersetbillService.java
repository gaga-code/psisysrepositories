package com.psi.service.inventorymanagement.customersetbill.impl;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psi.dao.DaoSupport;
import com.psi.entity.Page;
import com.psi.entity.PsiBillCode;
import com.psi.service.inventorymanagement.customersetbill.CustomersetbillManager;
import com.psi.service.inventorymanagement.salebill.SalebillManager;
import com.psi.service.inventorymanagement.salebillandcustomersetback.impl.SaleBillAndCustomersetBackService;
import com.psi.service.system.BillCodePsi.BillCodeManager;
import com.psi.util.Const;
import com.psi.util.DateUtil;
import com.psi.util.JdbcTempUtil;
import com.psi.util.PageData;
import com.psi.util.ProductBillCodeUtil;
import com.psi.util.UuidUtil;

/**
 * 说明：  客户结算单
 */
@Service("customersetbillService")
public class CustomersetbillService implements CustomersetbillManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Autowired
	private JdbcTempUtil jdbcTempUtil;
	@Autowired
	private ProductBillCodeUtil productBillCodeUtil;
	@Resource(name="billCodeService")
	private BillCodeManager billCodeService;
	@Resource(name="salebillService")
	private SalebillManager salebillService;
	@Resource(name="saleBillAndCustomersetBackService")
	private SaleBillAndCustomersetBackService  saleBillAndCustomersetBackService;
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		String[] strs = productBillCodeUtil.getBillCode(Const.BILLCODE_CUSTONMERSETBILL_PFIX); //获取该编号类型的最大编号
		pd.put("CUSTOMERSETBILL_ID", UuidUtil.get32UUID());
		pd.put("BILLCODE",strs[0]);
		pd.put("BILLTYPE", 4);
		pd.put("LDATE", DateUtil.getTime().toString());
		pd.put("BILLSTATUS", 1);
		dao.save("CustomersetbillMapper.save", pd);
		if(strs[1] == null){ //新增
			PsiBillCode psiBillCode = new PsiBillCode();
			psiBillCode.setCode_ID(UuidUtil.get32UUID());
			psiBillCode.setCodeType(Const.BILLCODE_CUSTONMERSETBILL_PFIX);
			psiBillCode.setMaxNo(strs[0]);
			psiBillCode.setNOTE("客户结算单编号");
			billCodeService.insertBillCode(psiBillCode);
		}else{//修改
			PageData ppp = new PageData();
			ppp.put("MaxNo",strs[0]);
			ppp.put("Code_ID", strs[1]);
			billCodeService.updateMaxNo(ppp);
		}
		//更改销售单与结算单的关联
//		salebillService.updateSuppId(pd);
		//更新前先取出来
		String SALEBILL_IDs = pd.getString("SALEBILL_IDS");
		String[] ioids = SALEBILL_IDs.split(",");
		for(int i = 0; i < ioids.length; i++) {
			PageData salebillpd = new PageData();
			salebillpd.put("SALEBILL_ID",ioids[i].substring(1, ioids[i].length()-1) );
			salebillpd = salebillService.findById(salebillpd);
			if(salebillpd.get("CUSTOMERSETBILL_ID")==null || salebillpd.get("CUSTOMERSETBILL_ID") == "" || "".equals(salebillpd.get("CUSTOMERSETBILL_ID")) ) {
				salebillpd.put("CUSTOMERSETBILL_ID",pd.getString("CUSTOMERSETBILL_ID"));
			}else if(!((String)salebillpd.get("CUSTOMERSETBILL_ID")).contains(pd.getString("CUSTOMERSETBILL_ID"))){
				salebillpd.put("CUSTOMERSETBILL_ID", salebillpd.get("CUSTOMERSETBILL_ID")+","+pd.getString("CUSTOMERSETBILL_ID"));
			}
			salebillpd.put("ISSETTLEMENTED", "2");
			salebillService.editFromCustomer(salebillpd);
		}
		/*String sql = "update psi_inorder set CUSTOMERSETBILL_ID ='"+pd.getString("CUSTOMERSETBILL_ID")+"' , ISSETTLEMENTED='2' where SALEBILL_ID in ("+pd.getString("SALEBILL_IDS")+") and "
				+ "PK_SOBOOKS='"+pd.getString("PK_SOBOOKS")+"' and IFNULL(dr,0)=0";
		jdbcTempUtil.update(sql);*/
		
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		pd = (PageData)dao.findForObject("CustomersetbillMapper.findById", pd);
		String SALEBILL_IDs = pd.getString("SALEBILL_IDS");
		if(SALEBILL_IDs != "" && !"".equals(SALEBILL_IDs) && SALEBILL_IDs != null) {
			String[] ioids = SALEBILL_IDs.split(",");
			for(int i = 0; i < ioids.length; i++) {
				PageData salebillpd = new PageData();
				salebillpd.put("SALEBILL_ID",ioids[i].substring(1, ioids[i].length()-1) );
				salebillpd = salebillService.findById(salebillpd);
				String suppid = salebillpd.getString("CUSTOMERSETBILL_ID");
				if(suppid.contains(pd.getString("CUSTOMERSETBILL_ID"))) {
					String[] split = suppid.split(",");
					String newsuppid="";
					for(String str:split) {
						if(!str.equalsIgnoreCase(pd.getString("CUSTOMERSETBILL_ID"))) {
							newsuppid+=str+",";
						}
					}
					if(newsuppid != "") {
						newsuppid = newsuppid.substring(0,newsuppid.length()-1);
					}
					salebillpd.put("CUSTOMERSETBILL_ID", newsuppid);
					salebillpd.put("CUSTOMERSETBILL_ID", suppid);
					salebillpd.put("ISSETTLEMENTED", "0");
					salebillService.editFromCustomer(salebillpd);
				}
			}
		}
		dao.delete("CustomersetbillMapper.delete", pd);
		
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("CustomersetbillMapper.edit", pd);
		String SALEBILL_IDs = pd.getString("SALEBILL_IDS");
		String[] ioids = SALEBILL_IDs.split(",");
		for(int i = 0; i < ioids.length; i++) {
			PageData salebillpd = new PageData();
			salebillpd.put("SALEBILL_ID",ioids[i].substring(1, ioids[i].length()-1) );
			salebillpd = salebillService.findById(salebillpd);
			if(salebillpd.get("CUSTOMERSETBILL_ID")==null || salebillpd.get("CUSTOMERSETBILL_ID") == "") {
				salebillpd.put("CUSTOMERSETBILL_ID",pd.getString("CUSTOMERSETBILL_ID"));
			}else if(((String)salebillpd.get("CUSTOMERSETBILL_ID")).contains(pd.getString("CUSTOMERSETBILL_ID"))){
				//无操作
			}else {
				salebillpd.put("CUSTOMERSETBILL_ID", salebillpd.get("CUSTOMERSETBILL_ID")+","+pd.getString("CUSTOMERSETBILL_ID"));
			}
			salebillpd.put("ISSETTLEMENTED", "2");
			salebillService.editFromCustomer(salebillpd);
		}
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("CustomersetbillMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("CustomersetbillMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("CustomersetbillMapper.findById", pd);
	}
	
	
	/**
	 * 批量删除
	 * DATA_IDS   主键 
	 * PK_SOBOOKS  帐套主键
	 */
	public void deleteAll(String DATA_IDS,String PK_SOBOOKS)throws Exception{
		//表名和主键字段名
		jdbcTempUtil.deleteAll(DATA_IDS, PK_SOBOOKS, "psi_suppsetbill", "CUSTOMERSETBILL_ID");
	}

	/**
	 * 批量审批
	 * DATA_IDS   主键 
	 * PK_SOBOOKS  帐套主键
	 */
	public void approvalAll(String[] ids)throws Exception {
		
		for(int i = 0; i < ids.length; i++) {
			PageData pd = new PageData();
			pd.put("CUSTOMERSETBILL_ID", ids[i]);
			approvalone(pd);
		}
	}
	/**
	 * 
	 * 批量结算
	 * DATA_IDS   主键 
	 * PK_SOBOOKS  帐套主键
	 */
	public void settleAll(String substring, String string)throws Exception {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 单张审批
	 * 1、获取结算单id
	 * 2、根据结算单的销售单主键获取销售单表头
	 * 3、先对当前快照做一份备份，为了后面反审用
	 * 4、根据结算单的实收金额对销售单依次结算
	 * @param pd
	 * @throws Exception
	 */
	public void approvalone(PageData pd) throws Exception {
		
		//================================================================1、进行备份===========================================================//
		//查找结算单
		pd = (PageData)dao.findForObject("CustomersetbillMapper.findById", pd);
		String cursuppid = pd.getString("CUSTOMERSETBILL_ID");
		String SALEBILL_IDs = pd.getString("SALEBILL_IDS");
		String[] ioids = SALEBILL_IDs.split(",");
		List<PageData> salebillandbodylist = new ArrayList<PageData>();
		for(int i = 0 ; i < ioids.length; i++) {
			PageData salebillandbody = new PageData();
			salebillandbody.put("SALEBILL_ID",ioids[i].substring(1, ioids[i].length()-1) );
			salebillandbody = salebillService.findAllById(salebillandbody);
			String salebill = salebillandbody.getString("CUSTOMERSETBILL_ID");
			if(!salebill.contains(cursuppid)) {
				salebillandbody.put("CUSTOMERSETBILL_ID", salebill+","+cursuppid);
			}
			//备份销售单明细
			List<PageData> salebillbodylist = (List<PageData>) salebillandbody.get("goodslist");
			for(int j = 0; j<salebillbodylist.size();j++) {
				salebillbodylist.get(j).put("SALEBILLBODYBACK_ID", UuidUtil.get32UUID());
				saleBillAndCustomersetBackService.savesalebodyback(salebillbodylist.get(j));
			}
			salebillandbody.put("goodslist", salebillbodylist);
			//备份销售单表头
			salebillandbody.put("SALEBILLBACK_ID",  UuidUtil.get32UUID());
			saleBillAndCustomersetBackService.savesaleback(salebillandbody);
			salebillandbodylist.add(salebillandbody);
		}
		//备份结算单
		String customerid = UuidUtil.get32UUID();
		pd.put("CUSTOMERSETBILLBACK_ID", customerid);
		saleBillAndCustomersetBackService.savecustomerback(pd);
		//================================================================2、根据实收金额依次对销售单进行结算===========================================================//
		Double thispay = (Double) pd.get("PAYMENTAMOUNT");//本次结算的实收金额
		int settleNum = 1;
		for(int k  = 0; k < salebillandbodylist.size(); k++) {
			PageData headpd = salebillandbodylist.get(k);
			String SALEBILLBACK_ID = (String)headpd.get("SALEBILLBACK_ID");//当前销售单备份主键
			Double unpay = (Double)headpd.get("UNPAIDAMOUNT");
			if(thispay >= unpay) {//全部结算完，状态为“已结算”
				headpd.put("PAIDAMOUNT",(Double)headpd.get("PAIDAMOUNT") + unpay);
				headpd.put("THISPAY",unpay);
				headpd.put("UNPAIDAMOUNT", 0);
				headpd.put("ISSETTLEMENTED", 1);
				headpd.put("SETTEDNUMANDID",SALEBILLBACK_ID);
				thispay -= unpay;
			}else {//部分结算，状态为“未结算”
				headpd.put("PAIDAMOUNT",(Double)headpd.get("PAIDAMOUNT") + thispay );
				headpd.put("THISPAY",thispay);
				headpd.put("UNPAIDAMOUNT", unpay-thispay);
				headpd.put("ISSETTLEMENTED", 0);
				headpd.put("SETTEDNUMANDID",SALEBILLBACK_ID);
			}
			if(headpd.getString("CUSTOMERSETBILL_ID").split(",").length==1) {
				headpd.put("LDATE", DateUtil.getTime().toString());
			}
			//销售单的表体明细
			List<PageData> salebillbodylist = (List<PageData>) headpd.get("goodslist");
			for(int j = 0; j<salebillbodylist.size();j++) {
				PageData bodypd = salebillbodylist.get(j);
				bodypd.put("SETTEDNUMANDID", bodypd.get("SALEBILLBODYBACK_ID"));
				//更新表体的SETTEDNUMANDID字段
				dao.update("SalebillBodyMapper.updatebodysettleid", bodypd);
			}
			//更新销售单表头
			dao.update("SalebillMapper.updateForSettle", headpd);
		}
		//================================================================3、对结算单进行处理===========================================================//		
		pd.put("SETTEDNUMANDID",pd.get("CUSTOMERSETBILLBACK_ID"));
		pd.put("BILLSTATUS", "2");
		dao.update("CustomersetbillMapper.updateForSettle", pd);
	}

	/**
	 * 单张反审
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void unapprovalone(PageData pd) throws Exception {
		//====================================先做校验，判断是否有销售单存在多次结算情况========================================
		pd = (PageData)dao.findForObject("CustomersetbillMapper.findById", pd);
		String SALEBILL_IDs = pd.getString("SALEBILL_IDS");
		String[] ioids = SALEBILL_IDs.split(",");
		PageData salebillpd = new PageData();
		salebillpd.put("SALEBILL_ID",ioids[ioids.length-1].substring(1, ioids[ioids.length-1].length()-1) );
		salebillpd = salebillService.findById(salebillpd);
		String ids = (String)salebillpd.get("CUSTOMERSETBILL_ID");
		if(ids.isEmpty()) {
			throw new Exception("该结算单有问题");
		}
		String[] split = ids.split(",");
		for(int i = 0; i < split.length; i++) {
			if(split[i].equals(pd.getString("CUSTOMERSETBILL_ID"))) {
				if(i != split.length-1) {
					PageData customer = new PageData();
					customer.put("CUSTOMERSETBILL_ID", split[i+1]);//查找下一张客户结算单是否审核
					customer = (PageData)dao.findForObject("CustomersetbillMapper.findById", customer);
					if(customer.get("BILLSTATUS").equals("2")) {//下一张结算单已审核，则进if
						String billcode = (String) customer.get("BILLCODE");
						throw new Exception("该结算单里的销售单有出现多次结算情况，请先反审单据编号为"+billcode+"的结算单");
					}else {
						break;
					}
				}
			}
		}
		
		//=================================进行真正的反审操作，也就是恢复审批前的快照============================================
		//1、查找结算单、销售单以及明细的快照主键 SETTEDNUMANDID
		String customerbackid = pd.getString("SETTEDNUMANDID");//结算单的快照主键
		List<String> salebillbackidlist = new ArrayList<String>();//销售单的快照主键
		List<String> salebillbodybackidlist = new ArrayList<String>();//销售明细单的快照主键
		for(int i = 0 ; i < ioids.length; i++) {
			PageData salebillandbody = new PageData();
			salebillandbody.put("SALEBILL_ID",ioids[i].substring(1, ioids[i].length()-1) );
			salebillandbody = salebillService.findAllById(salebillandbody);
			salebillbackidlist.add(salebillandbody.getString("SETTEDNUMANDID"));
			List<PageData> salebillbodylist = (List<PageData>) salebillandbody.get("goodslist");
			for(int j = 0; j<salebillbodylist.size();j++) {
				salebillbodybackidlist.add(salebillbodylist.get(j).getString("SETTEDNUMANDID"));
			}
		}
		PageData customerback = new PageData();
		customerback.put("CUSTOMERSETBILLBACK_ID", customerbackid);
		customerback = saleBillAndCustomersetBackService.findCustomerBackById(customerback);
		dao.update("CustomersetbillMapper.snapshotedit", customerback);
		
		for(int i = 0 ; i < salebillbackidlist.size(); i++) {
			PageData salebillback = new PageData();
			salebillback.put("SALEBILLBACK_ID", salebillbackidlist.get(i));
			salebillback=saleBillAndCustomersetBackService.findSaleBackById(salebillback);
			dao.update("SalebillMapper.salebillsnapshotedit", salebillback);
		}
		for(int i = 0 ; i < salebillbodybackidlist.size(); i++) {
			PageData inbodyback = new PageData();
			inbodyback.put("SALEBILLBODYBACK_ID", salebillbodybackidlist.get(i));
			inbodyback=saleBillAndCustomersetBackService.findSaleBodyBackById(inbodyback);
			dao.update("SalebillBodyMapper.salebillbodysnapshotedit", inbodyback);
		}
	}
	
}

