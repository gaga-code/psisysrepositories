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

}
