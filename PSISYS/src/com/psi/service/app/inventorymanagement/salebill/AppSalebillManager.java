package com.psi.service.app.inventorymanagement.salebill;

import java.util.List;

import com.psi.util.PageData;

public interface AppSalebillManager {

	List<PageData> listDataAndNumAndPrice(PageData pd) throws Exception;

	List<PageData> listSaleInfoByToday(PageData pd) throws Exception;

	List<PageData> listSaleInfoDayByMouth(PageData pd);

	List<PageData> listSaledGoodsBySTT(PageData pd) throws Exception;


}
