package com.psi.service.psifinance.salesprofit;

import java.util.List;

import com.psi.entity.Page;
import com.psi.util.PageData;

/**
 * 说明： 商品管理
 */
public interface SalesProfitManager{

	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;

	public List<PageData> listAllToExcel(PageData pd) throws Exception;
	
}

