package com.psi.service.inventorymanagement.salebillandcustomersetback;

import com.psi.util.PageData;

/*
 * 备份销售单与客户结算的接口
 */
public interface SaleBillAndCustomersetBackManager {
	//客户结算单接口
	public void savecustomerback(PageData pd)throws Exception;
	public void deletecustomerback(PageData pd)throws Exception;
	public void editcustomerback(PageData pd)throws Exception;
	public PageData findCustomerBackById(PageData pd)throws Exception;
	
	//销售单表头接口
	public void savesaleback(PageData pd)throws Exception;
	public void deletesaleback(PageData pd)throws Exception;
	public void editsaleback(PageData pd)throws Exception;
	public PageData findSaleBackById(PageData pd)throws Exception;
	
	//销售单表体接口
	public void savesalebodyback(PageData pd)throws Exception;
	public void deletesalebodyback(PageData pd)throws Exception;
	public void editsalebodyback(PageData pd)throws Exception;
	public PageData findSaleBodyBackById(PageData pd)throws Exception;
	
	
	
}
