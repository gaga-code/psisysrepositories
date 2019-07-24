package com.psi.service.inventorymanagement.suppsetbill.impl;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psi.dao.DaoSupport;
import com.psi.entity.Page;
import com.psi.entity.PsiBillCode;
import com.psi.service.inventorymanagement.inorder.InOrderManager;
import com.psi.service.inventorymanagement.inorder.impl.InOrderService;
import com.psi.service.inventorymanagement.inorderandsuppsetback.InOrderAndSuppsetBackManager;
import com.psi.service.inventorymanagement.suppsetbill.SuppsetbillManager;
import com.psi.service.system.BillCodePsi.BillCodeManager;
import com.psi.util.Const;
import com.psi.util.DateUtil;
import com.psi.util.JdbcTempUtil;
import com.psi.util.PageData;
import com.psi.util.ProductBillCodeUtil;
import com.psi.util.UuidUtil;

/**
 * 说明：  供应商结算单
 */
@Service("suppsetbillService")
public class SuppsetbillService implements SuppsetbillManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Autowired
	private JdbcTempUtil jdbcTempUtil;
	@Autowired
	private ProductBillCodeUtil productBillCodeUtil;
	@Resource(name="billCodeService")
	private BillCodeManager billCodeService;
	@Resource(name="inOrderService")
	private InOrderManager inOrderService;
	@Resource(name="inOrderAndSuppsetBackService")
	private InOrderAndSuppsetBackManager  inOrderAndSuppsetBackService;
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		String[] strs = productBillCodeUtil.getBillCode(Const.BILLCODE_SUPPSETBILL_PFIX); //获取该编号类型的最大编号
		pd.put("SUPPSETBILL_ID", UuidUtil.get32UUID());
		pd.put("BILLCODE",strs[0]);
		pd.put("BILLTYPE", 3);
		pd.put("LDATE", DateUtil.getTime().toString());
		pd.put("BILLSTATUS", 1);
		dao.save("SuppsetbillMapper.save", pd);
		if(strs[1] == null){ //新增
			PsiBillCode psiBillCode = new PsiBillCode();
			psiBillCode.setCode_ID(UuidUtil.get32UUID());
			psiBillCode.setCodeType(Const.BILLCODE_SUPPSETBILL_PFIX);
			psiBillCode.setMaxNo(strs[0]);
			psiBillCode.setNOTE("供应商结算单编号");
			billCodeService.insertBillCode(psiBillCode);
		}else{//修改
			PageData ppp = new PageData();
			ppp.put("MaxNo",strs[0]);
			ppp.put("Code_ID", strs[1]);
			billCodeService.updateMaxNo(ppp);
		}
		//更改进货单与结算单的关联
//		inOrderService.updateSuppId(pd);
		//更新前先取出来
		String inorder_ids = pd.getString("INORDER_IDS");
		String[] ioids = inorder_ids.split(",");
		for(int i = 0; i < ioids.length; i++) {
			PageData inorderpd = new PageData();
			inorderpd.put("INORDER_ID",ioids[i].substring(1, ioids[i].length()-1) );
			inorderpd = inOrderService.findById(inorderpd);
			if(inorderpd.get("SUPPSETBILL_ID")==null || inorderpd.get("SUPPSETBILL_ID") == "") {
				inorderpd.put("SUPPSETBILL_ID",pd.getString("SUPPSETBILL_ID"));
			}else {
				inorderpd.put("SUPPSETBILL_ID", inorderpd.get("SUPPSETBILL_ID")+","+pd.getString("SUPPSETBILL_ID"));
			}
			inorderpd.put("ISSETTLEMENTED", "2");
			inOrderService.editFromSupp(inorderpd);
		}
		/*String sql = "update psi_inorder set SUPPSETBILL_ID ='"+pd.getString("SUPPSETBILL_ID")+"' , ISSETTLEMENTED='2' where INORDER_ID in ("+pd.getString("INORDER_IDS")+") and "
				+ "PK_SOBOOKS='"+pd.getString("PK_SOBOOKS")+"' and IFNULL(dr,0)=0";
		jdbcTempUtil.update(sql);*/
		
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("SuppsetbillMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("SuppsetbillMapper.edit", pd);
		String inorder_ids = pd.getString("INORDER_IDS");
		String[] ioids = inorder_ids.split(",");
		for(int i = 0; i < ioids.length; i++) {
			PageData inorderpd = new PageData();
			inorderpd.put("INORDER_ID",ioids[i].substring(1, ioids[i].length()-1) );
			inorderpd = inOrderService.findById(inorderpd);
			if(inorderpd.get("SUPPSETBILL_ID")==null || inorderpd.get("SUPPSETBILL_ID") == "") {
				inorderpd.put("SUPPSETBILL_ID",pd.getString("SUPPSETBILL_ID"));
			}else {
				inorderpd.put("SUPPSETBILL_ID", inorderpd.get("SUPPSETBILL_ID")+","+pd.getString("SUPPSETBILL_ID"));
			}
			inorderpd.put("ISSETTLEMENTED", "2");
			inOrderService.editFromSupp(inorderpd);
		}
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SuppsetbillMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SuppsetbillMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SuppsetbillMapper.findById", pd);
	}
	
	
	/**
	 * 批量删除
	 * DATA_IDS   主键 
	 * PK_SOBOOKS  帐套主键
	 */
	public void deleteAll(String DATA_IDS,String PK_SOBOOKS)throws Exception{
		//表名和主键字段名
		jdbcTempUtil.deleteAll(DATA_IDS, PK_SOBOOKS, "psi_suppsetbill", "SUPPSETBILL_ID");
	}

	/**
	 * 批量审批
	 * DATA_IDS   主键 
	 * PK_SOBOOKS  帐套主键
	 */
	public void approvalAll(String[] ids)throws Exception {
		
		for(int i = 0; i < ids.length; i++) {
			PageData pd = new PageData();
			pd.put("SUPPSETBILL_ID", ids[i]);
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
	 * 2、根据结算单的进货单主键获取进货单表头
	 * 3、先对当前快照做一份备份，为了后面反审用
	 * 4、根据结算单的实付金额对进货单依次结算
	 * @param pd
	 * @throws Exception
	 */
	public void approvalone(PageData pd) throws Exception {
		
		//================================================================1、进行备份===========================================================//
		//查找结算单
		pd = (PageData)dao.findForObject("SuppsetbillMapper.findById", pd);
		String inorder_ids = pd.getString("INORDER_IDS");
		String[] ioids = inorder_ids.split(",");
//		List<>
		List<PageData> inorderandbodylist = new ArrayList<PageData>();
		for(int i = 0 ; i < ioids.length; i++) {
			PageData inorderandbody = new PageData();
			inorderandbody.put("INORDER_ID",ioids[i].substring(1, ioids[i].length()-1) );
			inorderandbody = inOrderService.findAllById(inorderandbody);
			
			//备份进货单明细
			List<PageData> inorderbodylist = (List<PageData>) inorderandbody.get("goodslist");
			for(int j = 0; j<inorderbodylist.size();j++) {
				inorderbodylist.get(j).put("INORDERBODYBACK_ID", UuidUtil.get32UUID());
				inOrderAndSuppsetBackService.saveinbodyback(inorderbodylist.get(j));
			}
			inorderandbody.put("goodslist", inorderbodylist);
			//备份进货单表头
			inorderandbody.put("INORDERBACK_ID",  UuidUtil.get32UUID());
			inOrderAndSuppsetBackService.saveinback(inorderandbody);
			inorderandbodylist.add(inorderandbody);
		}
		//备份结算单
		String suppsetid = UuidUtil.get32UUID();
		pd.put("SUPPSETBILLBACK_ID", suppsetid);
		inOrderAndSuppsetBackService.savesuppback(pd);
		//================================================================2、根据实付金额依次对进货单进行结算===========================================================//
		Double thispay = (Double) pd.get("PAYMENTAMOUNT");//本次结算的实付金额
		int settleNum = 1;
		for(int k  = 0; k < inorderandbodylist.size(); k++) {
			PageData headpd = inorderandbodylist.get(k);
			String INORDERBACK_ID = (String)headpd.get("INORDERBACK_ID");//当前进货单备份主键
			Double unpay = (Double)headpd.get("UNPAIDAMOUNT");
			if(thispay >= unpay) {//全部结算完，状态为“已结算”
				headpd.put("PAIDAMOUNT",(Double)headpd.get("PAIDAMOUNT") + unpay);
				headpd.put("THISPAY",unpay);
				headpd.put("UNPAIDAMOUNT", 0);
				headpd.put("ISSETTLEMENTED", 1);
				headpd.put("SETTEDNUMANDID",INORDERBACK_ID);
				thispay -= unpay;
			}else {//部分结算，状态为“未结算”
				String last = "";
				String SETTEDNUMANDID = "";
				String laststr = (String) headpd.get("SETTEDNUMANDID");//上一个的备份1id|1id
				settleNum = (laststr == null || "".equals(laststr) )?1:Integer.parseInt(laststr.split("&")[0].split("|")[0])+1;
				if(settleNum == 1) {
					SETTEDNUMANDID=settleNum+"|"+ INORDERBACK_ID+"&"+settleNum+"|"+ INORDERBACK_ID;
				}else {
					
					String curstr = settleNum+"|"+INORDERBACK_ID;//当前2id
					SETTEDNUMANDID=curstr+"&"+curstr;//2id&2id
					last = laststr.split("&")[0]+"&"+curstr; //上一个 1id&2id
					String lastbackId = laststr.split("&")[0].split("|")[1];//上一个备份id
					
					//更新上一个备份里的SETTEDNUMANDID字段，来判断之后是否有再次结算
					PageData lastinorderbackpd = new PageData();
					lastinorderbackpd.put("INORDERBACK_ID", lastbackId);
					lastinorderbackpd = inOrderAndSuppsetBackService.findInBackById(lastinorderbackpd);
					lastinorderbackpd.put("SETTEDNUMANDID", last);
					inOrderAndSuppsetBackService.editinback(lastinorderbackpd);
					
					//更新当前一个备份里的SETTEDNUMANDID字段
					PageData curinorderbackpd = new PageData();
					curinorderbackpd.put("INORDERBACK_ID", INORDERBACK_ID);
					curinorderbackpd = inOrderAndSuppsetBackService.findInBackById(curinorderbackpd);
					inOrderAndSuppsetBackService.editinback(curinorderbackpd);
				}
				
				headpd.put("PAIDAMOUNT",(Double)headpd.get("PAIDAMOUNT") + thispay );
				headpd.put("THISPAY",thispay);
				headpd.put("UNPAIDAMOUNT", unpay-thispay);
				headpd.put("ISSETTLEMENTED", 0);
				headpd.put("SETTEDNUMANDID",SETTEDNUMANDID);
			}
			//进货单的表体明细
			List<PageData> inorderbodylist = (List<PageData>) headpd.get("goodslist");
			for(int j = 0; j<inorderbodylist.size();j++) {
				PageData bodypd = inorderbodylist.get(j);
				bodypd.put("SETTEDNUMANDID", bodypd.get("INORDERBODYBACK_ID"));
				//更新表体的SETTEDNUMANDID字段
				dao.update("InOrderBodyMapper.updatebodysettleid", bodypd);
			}
			//更新进货单表头
			dao.update("InOrderMapper.updateForSettle", headpd);
		}
		//================================================================3、对结算单进行处理===========================================================//		
		pd.put("SETTEDNUMANDID","1"+"|"+pd.get("SUPPSETBILLBACK_ID"));
		pd.put("BILLSTATUS", "2");
		dao.update("SuppsetbillMapper.updateForSettle", pd);
	}
	
}

