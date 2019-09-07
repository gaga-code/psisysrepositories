package com.psi.service.app.basedata.goods.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.psi.dao.DaoSupport;
import com.psi.service.app.basedata.goods.AppGoodsManager;
import com.psi.util.PageData;

@Service("appGoodsService")
public class AppGoodsService implements AppGoodsManager{
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listGoods(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("AppGoodsMapper.listGoods", pd);
	}


	@Override
	public List<PageData> listGoodsListByClass(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("AppGoodsMapper.listGoodsListByClass", pd);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listGoodsListByName(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("AppGoodsMapper.listGoodsListByName", pd);
	}


	@Override
	public void editGoodsPhoto(PageData pd) throws Exception {
		dao.findForList("AppGoodsMapper.editGoodsPhoto", pd);
		
	}


	@Override
	public Double findAll(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return  (Double)dao.findForObject("AppGoodsMapper.findAll", pd);
	}


	@Override
	public Double findAllByClass(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return  (Double)dao.findForObject("AppGoodsMapper.findAllByClass", pd);
	}


	@Override
	public Double findAllByName(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (Double)dao.findForObject("AppGoodsMapper.findAllByName", pd);
	}


	@Override
	public PageData findByBarCode(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData)dao.findForObject("AppGoodsMapper.findByBarCode", pd);
	}

}
