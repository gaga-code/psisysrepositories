package com.psi.service.basedata.bzbill;

import com.psi.util.PageData;

public interface BZBillManager {

	PageData findByPK(PageData pd) throws Exception;

	void edit(PageData pd) throws Exception;

}
