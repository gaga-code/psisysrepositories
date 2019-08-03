package com.psi.service.app.inventorymanagement.inoder.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.psi.dao.DaoSupport;
import com.psi.service.app.inventorymanagement.inoder.AppInOderManager;
import com.psi.util.PageData;

@Service
public class AppInOderService implements AppInOderManager {
	
	@Resource(name="daoSupport")
	private DaoSupport dao;

	@SuppressWarnings("unchecked")
	public List<PageData> listInOderByToday(PageData pd) throws Exception {
		
		return (List<PageData>)dao.findForList("AppInOderMapper.listInOderByToday", pd);
	}

	@Override
	public PageData listMountAndNum(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData)dao.findForObject("AppInOderMapper.listMountAndNum",pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listInOderGoods(PageData pd) throws Exception {
		
		return (List<PageData>)dao.findForList("AppInOderMapper.listInOderGoods", pd);
	}

	@Override
	public PageData listMountAndNumByMD(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData)dao.findForObject("AppInOderMapper.listMountAndNumByMD",pd);
	}

	@Override
	public List<PageData> listInOderGoodsByMD(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("AppInOderMapper.listInOderGoodsByMD",pd);
	}

}
