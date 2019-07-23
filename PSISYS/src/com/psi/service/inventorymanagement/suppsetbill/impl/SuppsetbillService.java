package com.psi.service.inventorymanagement.suppsetbill.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psi.dao.DaoSupport;
import com.psi.entity.Page;
import com.psi.entity.PsiBillCode;
import com.psi.service.inventorymanagement.inorder.impl.InOrderService;
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
	private InOrderService inOrderService;
	
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
		String sql = "update psi_inorder set SUPPSETBILL_ID ='"+pd.getString("SUPPSETBILL_ID")+"' , ISSETTLEMENTED='2' where INORDER_ID in ("+pd.getString("INORDER_IDS")+") and "
				+ "PK_SOBOOKS='"+pd.getString("PK_SOBOOKS")+"' and IFNULL(dr,0)=0";
		jdbcTempUtil.update(sql);
		
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
		String sql = "update psi_inorder set SUPPSETBILL_ID ='"+pd.getString("SUPPSETBILL_ID")+"' , ISSETTLEMENTED='2' where INORDER_ID in ("+pd.getString("INORDER_IDS")+") and "
				+ "PK_SOBOOKS='"+pd.getString("PK_SOBOOKS")+"' and IFNULL(dr,0)=0";
		jdbcTempUtil.update(sql);
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
		//1、获取结算单id
		//2、根据结算单的进货单主键获取进货单表头
		for(int i = 0; i < ids.length; i++) {
			PageData pd = new PageData();
			pd.put("SUPPSETBILL_ID", ids[i]);
			pd = (PageData)dao.findForObject("SuppsetbillMapper.findById", pd);
			String inorder_ids = pd.getString("INORDER_IDS");
			
		}
		
		//3、先对当前快照做一份备份，为了后面反审用
		//4、根据结算单的实付金额对进货单依次结算
		
		
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
	 * @param pd
	 * @throws Exception
	 */
	public void approvalone(PageData pd) throws Exception {
		pd = (PageData)dao.findForObject("SuppsetbillMapper.findById", pd);
		String inorder_ids = pd.getString("INORDER_IDS");
		String[] ioids = inorder_ids.split(",");
		for(int i = 0 ; i < ioids.length; i++) {
			System.out.println(ioids[i]);
			PageData inorderandbody = new PageData();
			inorderandbody.put("INORDER_ID",ioids[i].substring(1, ioids[i].length()-1) );
			inorderandbody = inOrderService.findById(inorderandbody);
			
		}
		
	}
	
}

