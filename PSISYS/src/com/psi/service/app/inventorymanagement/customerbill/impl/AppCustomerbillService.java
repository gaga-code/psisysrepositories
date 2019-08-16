package com.psi.service.app.inventorymanagement.customerbill.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.psi.dao.DaoSupport;
import com.psi.service.app.inventorymanagement.customerbill.AppCustomerbillManager;
import com.psi.util.PageData;

@Service
public class AppCustomerbillService implements AppCustomerbillManager{

	

	@Resource(name="daoSupport")
	private DaoSupport dao;
	


	@Override
	public List<PageData> listPayAndAmountByMouth(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return ( List<PageData>)dao.findForList("AppCustomersetbillMapper.listPayAndAmountByMouth", pd);
	}



	@Override
	public List<PageData> listPayAndAmountByMouthDay(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return  ( List<PageData>)dao.findForList("AppCustomersetbillMapper.listPayAndAmountByMouthDay", pd);
	}

}
