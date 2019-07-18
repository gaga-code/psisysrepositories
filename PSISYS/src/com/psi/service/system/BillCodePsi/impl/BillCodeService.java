package com.psi.service.system.BillCodePsi.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.psi.dao.DaoSupport;
import com.psi.entity.PsiBillCode;
import com.psi.service.system.BillCodePsi.BillCodeManager;
import com.psi.util.PageData;

@Service("billCodeService")
public class BillCodeService implements BillCodeManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Override
	public void insertBillCode(PsiBillCode psiBillCode) throws Exception {
		dao.save("BillCodeMapper.insertBillCode",psiBillCode);
	}

	@Override
	public PageData findMaxNoByCodeType(PageData pd) throws Exception {
		return (PageData) dao.findForObject("BillCodeMapper.findMaxNoByCodeType", pd);
	}

	@Override
	public void updateMaxNo(PageData pd) throws Exception {
		dao.update("BillCodeMapper.updateMaxNo", pd);
	}
	

}
