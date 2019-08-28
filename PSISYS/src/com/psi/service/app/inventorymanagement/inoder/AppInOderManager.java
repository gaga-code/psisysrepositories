package com.psi.service.app.inventorymanagement.inoder;

import java.util.List;
import java.util.Map;

import com.psi.util.PageData;

public interface AppInOderManager {

	List<PageData> listInOderByToday(PageData pd) throws Exception;

	PageData listMountAndNum(PageData pd) throws Exception;

	List<PageData> listInOderGoods(PageData pd) throws Exception;

	PageData listMountAndNumByMD(PageData pd) throws Exception;

	List<PageData> listInOderGoodsByMD(PageData pd) throws Exception;

	List<PageData> listInOrder(PageData pd) throws Exception;

	List<PageData> listInOrderBody(PageData pd) throws Exception;

	PageData save(PageData pd) throws Exception;

	int listInOrderNum(PageData pd) throws Exception;

}
