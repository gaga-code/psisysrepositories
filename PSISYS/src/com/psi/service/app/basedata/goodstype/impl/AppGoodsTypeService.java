package com.psi.service.app.basedata.goodstype.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.psi.dao.DaoSupport;
import com.psi.service.app.basedata.goodstype.AppGoodsTypeManager;
import com.psi.util.PageData;
@Service
public class AppGoodsTypeService implements AppGoodsTypeManager {
	
	@Resource(name="daoSupport")
	private DaoSupport dao;

	@Override
	public List<String> listGoodsClass(PageData pd) throws Exception {

		return (List<String>) dao.findForList("AppGoodsTypeMapper.listGoodsClass", pd) ;
	}

}
