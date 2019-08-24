package com.psi.service.basedata.supplier.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psi.dao.DaoSupport;
import com.psi.entity.Page;
import com.psi.service.basedata.supplier.SupplierManager;
import com.psi.util.JdbcTempUtil;
import com.psi.util.PageData;

/**
 * 说明： 供应商管理
 */
@Service("supplierService")
public class SupplierService implements SupplierManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Autowired
	private JdbcTempUtil jdbcTempUtil;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("SupplierMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("SupplierMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("SupplierMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SupplierMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SupplierMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SupplierMapper.findById", pd);
	}
	
	
	/**
	 * 批量删除
	 * DATA_IDS   主键 
	 * PK_SOBOOKS  帐套主键
	 */
	public void deleteAll(String DATA_IDS,String PK_SOBOOKS)throws Exception{
		//表名和主键字段名
		jdbcTempUtil.deleteAll(DATA_IDS, PK_SOBOOKS, "base_supplier", "SUPPLIER_ID");
	}

	@Override
	public PageData findByCode(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData)dao.findForObject("SupplierMapper.findByCode", pd);
	}

	@Override
	public void saveSupplier(PageData pd) throws Exception {
		
		dao.save("SupplierMapper.saveSupplier", pd);
	}

	@Override
	public String findByName(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (String)dao.findForObject("SupplierMapper.findBySupplierName",pd);
	}

	@Override
	public List<PageData> listAllSupp(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("SupplierMapper.listAllSupp", pd);
	}

	//<!-- 修改当前应支付金额 -->
	@Override
	public void editAmount(PageData pd) throws Exception {
		dao.update("SupplierMapper.editAmount", pd);
	}

	//根据编号获取当前应付金额
	@Override
	public String findAmountByCode(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (String)dao.findForObject("SupplierMapper.findAmountByCode", pd);
	}

	
}

