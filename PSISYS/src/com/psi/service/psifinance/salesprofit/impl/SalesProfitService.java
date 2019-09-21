package com.psi.service.psifinance.salesprofit.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.psi.dao.DaoSupport;
import com.psi.entity.Page;
import com.psi.service.psifinance.salesprofit.SalesProfitManager;
import com.psi.util.PageData;

/**
 * 说明： 商品管理
 */
@Service("salesprofitService")
public class SalesProfitService implements SalesProfitManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SalesProfitMapper.datalistPage", page);
	}

	@Override
	public List<PageData> listAllToExcel(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("SalesProfitMapper.listAllToExcel", pd);
	}
	
}

