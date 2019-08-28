package com.psi.service.app.inventorymanagement.tock.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.psi.dao.DaoSupport;
import com.psi.entity.Page;
import com.psi.service.app.inventorymanagement.tock.AppStockManager;
import com.psi.util.PageData;

@Service
public class AppStockService implements AppStockManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	@Override
	public List<PageData> listStockById(PageData pd) throws Exception {
		
		return (List<PageData>)dao.findForList("AppStockMapper.listStockById", pd);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listGoodsUpDate(PageData pd) throws Exception {
		
		return ( List<PageData>)dao.findForList("AppStockMapper.listGoodsUpDate", pd);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listGoodsDownNum(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return ( List<PageData> )dao.findForList("AppStockMapper.listGoodsDownNum", pd);
	}


	@Override
	public int listGoodsUpDateNum(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return  (Integer)dao.findForList("AppStockMapper.listGoodsUpDateNum", pd);
	}


	@Override
	public int listGoodsDownNums(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (Integer)dao.findForList("AppStockMapper.listGoodsDownNums", pd);
	}

}
