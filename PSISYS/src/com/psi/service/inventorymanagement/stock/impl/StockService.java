package com.psi.service.inventorymanagement.stock.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.psi.dao.DaoSupport;
import com.psi.entity.Page;
import com.psi.service.inventorymanagement.stock.StockManager;
import com.psi.util.PageData;

/**
 * 说明： 商品管理
 */
@Service("stockService")
public class StockService implements StockManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("StockMapper.stocklistPage", page);
	}


}

