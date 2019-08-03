package com.psi.service.app.basedata.supplier;

import java.util.List;

import com.psi.util.PageData;

public interface AppSupplierManager {

	List<PageData> listSuppliers(PageData pd) throws Exception;

}
