package com.psi.service.app.basedata.customer;

import java.util.List;

import com.psi.util.PageData;

public interface AppCustomerManager {

	PageData findCustomerByCode(PageData pd) throws Exception;

	List<PageData> listCutomer(PageData pd) throws Exception;

}
