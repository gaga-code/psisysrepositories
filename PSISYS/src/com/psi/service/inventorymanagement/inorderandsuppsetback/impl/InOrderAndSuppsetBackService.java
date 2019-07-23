package com.psi.service.inventorymanagement.inorderandsuppsetback.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.psi.dao.DaoSupport;
import com.psi.service.inventorymanagement.inorderandsuppsetback.InOrderAndSuppsetBackManager;
import com.psi.util.PageData;
/**
 * 备份进货单与结算的接口
 * @author cx
 *
 */
@Service("inOrderAndSuppsetBackService")
public class InOrderAndSuppsetBackService implements InOrderAndSuppsetBackManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	//结算单接口
	@Override
	public void savesuppback(PageData pd) throws Exception {
		dao.save("InOrderAndSuppsetBackMapper.savesuppback", pd);
	}

	@Override
	public void deletesuppback(PageData pd) throws Exception {
		dao.update("InOrderAndSuppsetBackMapper.deletesuppback",pd);
	}

	@Override
	public void editsuppback(PageData pd) throws Exception {
		dao.update("InOrderAndSuppsetBackMapper.editsuppback",pd);
	}

	@Override
	public PageData findSuppBackById(PageData pd) throws Exception {
		return (PageData)dao.findForObject("InOrderAndSuppsetBackMapper.findSuppBackById", pd);
	}

	//进货单表头接口
	@Override
	public void saveinback(PageData pd) throws Exception {
		dao.save("InOrderAndSuppsetBackMapper.saveinback", pd);
	}

	@Override
	public void deleteinback(PageData pd) throws Exception {
		dao.update("InOrderAndSuppsetBackMapper.deleteinback",pd);
	}

	@Override
	public void editinback(PageData pd) throws Exception {
		dao.update("InOrderAndSuppsetBackMapper.editinback",pd);
	}

	@Override
	public PageData findInBackById(PageData pd) throws Exception {
		return (PageData)dao.findForObject("InOrderAndSuppsetBackMapper.findInBackById", pd);
	}

	
	//进货单表体接口
	@Override
	public void saveinbodyback(PageData pd) throws Exception {
		dao.save("InOrderAndSuppsetBackMapper.saveinbodyback", pd);
	}

	@Override
	public void deleteinbodyback(PageData pd) throws Exception {
		dao.update("InOrderAndSuppsetBackMapper.deleteinbodyback",pd);
	}

	@Override
	public void editinbodyback(PageData pd) throws Exception {
		dao.update("InOrderAndSuppsetBackMapper.editinbodyback",pd);
	}

	@Override
	public PageData findInBodyBackById(PageData pd) throws Exception {
		return (PageData)dao.findForObject("InOrderAndSuppsetBackMapper.findInBodyBackById", pd);
	}

}
