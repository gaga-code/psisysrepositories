package com.psi.service.app.basedata.supplier.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.psi.dao.DaoSupport;
import com.psi.service.app.basedata.supplier.AppSupplierManager;
import com.psi.util.PageData;

@Service
public class AppSupplierService implements AppSupplierManager {

	
	@Resource(name="daoSupport")
	private DaoSupport dao;

	@Override
	public List<PageData> listSuppliers(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("AppSupplierMapper.listSuppliers",pd);
	}
}
