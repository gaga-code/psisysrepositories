package com.psi.service.inventorymanagement.stock;

import java.util.List;
import java.util.Map;

import com.psi.entity.Page;
import com.psi.entity.basedata.GoodsType;
import com.psi.util.PageData;

/**
 * 说明： 商品管理
 */
public interface StockManager{

	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
}

