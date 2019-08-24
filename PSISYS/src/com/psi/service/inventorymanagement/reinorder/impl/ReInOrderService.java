package com.psi.service.inventorymanagement.reinorder.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.psi.dao.DaoSupport;
import com.psi.entity.Page;
import com.psi.service.inventorymanagement.reinorder.ReInOrderManager;
import com.psi.util.PageData;

@Service
@SuppressWarnings("unchecked")
public class ReInOrderService implements ReInOrderManager{

	@Resource(name="daoSupport")
	private DaoSupport dao;
	@Override
	public List<PageData> list(Page page) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("ReInOrderMapper.datalistPage", page);
	}

}
