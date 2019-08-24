package com.psi.service.basedata.bzbill.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.psi.dao.DaoSupport;
import com.psi.service.basedata.bzbill.BZBillManager;
import com.psi.util.PageData;

@Service("bZBillService")
public class BZBillService implements BZBillManager{
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Override
	public PageData findByPK(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData)dao.findForObject("BZBillMapper.findByPK", pd);
	}

	@Override
	public void edit(PageData pd) throws Exception {
	
		 dao.update("BZBillMapper.edit", pd);
	}
}
