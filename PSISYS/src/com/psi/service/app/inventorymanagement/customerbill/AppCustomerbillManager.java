package com.psi.service.app.inventorymanagement.customerbill;

import java.util.List;

import com.psi.util.PageData;

public interface AppCustomerbillManager {


	List<PageData> listPayAndAmountByMouth(PageData pd) throws Exception;

	List<PageData> listPayAndAmountByMouthDay(PageData pd) throws Exception;

}
