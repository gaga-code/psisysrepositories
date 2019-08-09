package com.psi.service.app.inventorymanagement.salebill.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.psi.dao.DaoSupport;
import com.psi.service.app.inventorymanagement.salebill.AppSalebillManager;
import com.psi.util.PageData;

@Service("appSalebillService")
public class AppSalebillService implements AppSalebillManager {

	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	@Override
	public List<PageData> listDataAndNumAndPrice(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("AppSalebillMapper.listDataAndNumAndPrice", pd);
	}

	@Override
	public List<PageData> listSaleInfoByToday(PageData pd) throws Exception {
		
		return (List<PageData>)dao.findForList("AppSalebillMapper.listSaleInfoByToday", pd);
	}

	@Override
	public List<PageData> listSaleInfoDayByMouth(PageData pd) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listSaledGoodsBySTT(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("AppSalebillMapper.listSaledGoodsBySTT", pd);
	}

}
