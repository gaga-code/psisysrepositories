package com.psi.service.inventorymanagement.salebillandcustomersetback.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.psi.dao.DaoSupport;
import com.psi.service.inventorymanagement.inorderandsuppsetback.InOrderAndSuppsetBackManager;
import com.psi.service.inventorymanagement.salebillandcustomersetback.SaleBillAndCustomersetBackManager;
import com.psi.util.PageData;
/**
 * 备份进货单与结算的接口
 * @author cx
 *
 */
@Service("saleBillAndCustomersetBackService")
public class SaleBillAndCustomersetBackService implements SaleBillAndCustomersetBackManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	//客户结算单接口
	@Override
	public void savecustomerback(PageData pd) throws Exception {
		dao.save("SalebillAndCustomersetBackMapper.savecustomerback", pd);
	}

	@Override
	public void deletecustomerback(PageData pd) throws Exception {
		dao.update("SalebillAndCustomersetBackMapper.deletecustomerback",pd);
	}

	@Override
	public void editcustomerback(PageData pd) throws Exception {
		dao.update("SalebillAndCustomersetBackMapper.editcustomerback",pd);
	}

	@Override
	public PageData findCustomerBackById(PageData pd) throws Exception {
		return (PageData)dao.findForObject("SalebillAndCustomersetBackMapper.findCustomerBackById", pd);
	}

	//销售单表头接口
	@Override
	public void savesaleback(PageData pd) throws Exception {
		dao.save("SalebillAndCustomersetBackMapper.savesaleback", pd);
	}

	@Override
	public void deletesaleback(PageData pd) throws Exception {
		dao.update("SalebillAndCustomersetBackMapper.deletesaleback",pd);
	}

	@Override
	public void editsaleback(PageData pd) throws Exception {
		dao.update("SalebillAndCustomersetBackMapper.editsaleback",pd);
	}

	@Override
	public PageData findSaleBackById(PageData pd) throws Exception {
		return (PageData)dao.findForObject("SalebillAndCustomersetBackMapper.findSaleBackById", pd);
	}

	
	//销售单表体接口
	@Override
	public void savesalebodyback(PageData pd) throws Exception {
		dao.save("SalebillAndCustomersetBackMapper.savesalebodyback", pd);
	}

	@Override
	public void deletesalebodyback(PageData pd) throws Exception {
		dao.update("SalebillAndCustomersetBackMapper.deletesalebodyback",pd);
	}

	@Override
	public void editsalebodyback(PageData pd) throws Exception {
		dao.update("SalebillAndCustomersetBackMapper.editsalebodyback",pd);
	}

	@Override
	public PageData findSaleBodyBackById(PageData pd) throws Exception {
		return (PageData)dao.findForObject("SalebillAndCustomersetBackMapper.findSaleBodyBackById", pd);
	}

}
