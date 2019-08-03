package com.psi.service.app.system.user.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.psi.dao.DaoSupport;
import com.psi.service.app.system.user.AppUserManager;
import com.psi.util.PageData;

@Service
public class AppUserService implements AppUserManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Override
	public PageData getUserByNameAndPwd(PageData pd) throws Exception {
		
		return (PageData)dao.findForObject("AppUserMapper.getUserInfo", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listUsers(PageData pd) throws Exception {
		
		return (List<PageData>)dao.findForList("AppUserMapper.listUsers", pd);
	}

}
