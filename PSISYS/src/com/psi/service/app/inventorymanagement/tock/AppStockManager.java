package com.psi.service.app.inventorymanagement.tock;

import java.util.List;

import com.psi.util.PageData;

public interface AppStockManager {

	List<PageData> listStockById(PageData pd) throws Exception;

	List<PageData> listGoodsUpDate(PageData pd) throws Exception;

	List<PageData> listGoodsDownNum(PageData pd) throws Exception;

	int listGoodsUpDateNum(PageData pd) throws Exception;

	int listGoodsDownNums(PageData pd) throws Exception;

}
