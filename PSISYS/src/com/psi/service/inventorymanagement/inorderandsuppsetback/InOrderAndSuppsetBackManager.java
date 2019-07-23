package com.psi.service.inventorymanagement.inorderandsuppsetback;

import com.psi.util.PageData;

/*
 * 备份进货单与结算的接口
 */
public interface InOrderAndSuppsetBackManager {
	//结算单接口
	public void savesuppback(PageData pd)throws Exception;
	public void deletesuppback(PageData pd)throws Exception;
	public void editsuppback(PageData pd)throws Exception;
	public PageData findSuppBackById(PageData pd)throws Exception;
	
	//进货单表头接口
	public void saveinback(PageData pd)throws Exception;
	public void deleteinback(PageData pd)throws Exception;
	public void editinback(PageData pd)throws Exception;
	public PageData findInBackById(PageData pd)throws Exception;
	
	//进货单表体接口
	public void saveinbodyback(PageData pd)throws Exception;
	public void deleteinbodyback(PageData pd)throws Exception;
	public void editinbodyback(PageData pd)throws Exception;
	public PageData findInBodyBackById(PageData pd)throws Exception;
	
	
	
}
