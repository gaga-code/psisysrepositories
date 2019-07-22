package com.psi.service.inventorymanagement.suppsetbill.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psi.dao.DaoSupport;
import com.psi.entity.Page;
import com.psi.entity.PsiBillCode;
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
		System.out.println(strs[0]);
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
	 * 
	 * 批量审批
	 * DATA_IDS   主键 
	 * PK_SOBOOKS  帐套主键
	 */
	public void approvalAll(String substring, String string)throws Exception {
		// TODO Auto-generated method stub
		
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
	
}

