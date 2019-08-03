package com.psi.service.app.system.userphoto.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.psi.dao.DaoSupport;
import com.psi.service.app.system.userphoto.AppUserPhotoManager;

@Service
public class AppUserPhotoService implements AppUserPhotoManager{
	
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
}
