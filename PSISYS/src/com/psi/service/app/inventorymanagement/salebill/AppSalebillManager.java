package com.psi.service.app.inventorymanagement.salebill;

import java.util.List;

import com.psi.util.PageData;

public interface AppSalebillManager {

	List<PageData> listDataAndNumAndPrice(PageData pd) throws Exception;

	List<PageData> listSaleInfoByToday(PageData pd) throws Exception;

	List<PageData> listSaleInfoDayByMouth(PageData pd) throws Exception;

	List<PageData> listSaledGoodsBySTT(PageData pd) throws Exception;

	List<PageData> listsalebill(PageData pd) throws Exception;

	List<PageData> listsalebillBody(PageData pd) throws Exception;

	List<PageData> listSalebillByMouth(PageData pd) throws Exception;

	PageData findPureAmountById(String Id) throws Exception;


	List<PageData> listSaledByCustomer(PageData pd) throws Exception;

	List<PageData> listSaledByUser(PageData pd) throws Exception;

	PageData save(PageData pd) throws Exception;

	int listSaledGoodsBySTTNum(PageData pd) throws Exception;

	int listSaledByCustomerNum(PageData pd) throws Exception;

	int listSaledByUserNum(PageData pd) throws Exception;

	int listsalebillNum(PageData pd) throws Exception;

	void updateshenpi(PageData pd) throws Exception;


}
