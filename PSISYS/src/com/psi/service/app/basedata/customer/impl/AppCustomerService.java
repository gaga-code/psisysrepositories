package com.psi.service.app.basedata.customer.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.psi.dao.DaoSupport;
import com.psi.service.app.basedata.customer.AppCustomerManager;
import com.psi.util.PageData;

@Service
public class AppCustomerService implements AppCustomerManager{

	@Resource(name="daoSupport")
	private DaoSupport dao;

	@Override
	public PageData findCustomerByCode(PageData pd) throws Exception {
		
		return (PageData)dao.findForObject("AppCustomerMapper.findCustomerByCode", pd);
	}

	@Override
	public List<PageData> listCutomer(PageData pd) throws Exception {
	
		return  (List<PageData>)dao.findForList("AppCustomerMapper.listCutomer", pd);
	}
	
}
