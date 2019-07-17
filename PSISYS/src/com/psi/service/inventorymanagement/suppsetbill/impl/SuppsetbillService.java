package com.psi.service.inventorymanagement.suppsetbill.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psi.dao.DaoSupport;
import com.psi.entity.Page;
import com.psi.service.inventorymanagement.suppsetbill.SuppsetbillManager;
import com.psi.util.JdbcTempUtil;
import com.psi.util.PageData;

/**
 * 说明：  供应商结算单
 */
@Service("suppsetbillService")
public class SuppsetbillService implements SuppsetbillManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Autowired
	private JdbcTempUtil jdbcTempUtil;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("SuppsetbillMapper.save", pd);
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

