package com.psi.service.inventorymanagement.reinorder;

import java.util.List;

import com.psi.entity.Page;
import com.psi.util.PageData;

public interface ReInOrderManager {

	List<PageData> list(Page page) throws Exception;

	void updatefanshen(PageData pd) throws Exception;

	void save(PageData pd) throws Exception;

}
