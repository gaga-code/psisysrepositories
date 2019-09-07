package com.psi.service.app.basedata.goods;

import java.util.List;

import com.psi.util.PageData;

public interface AppGoodsManager {
	List<PageData> listGoods(PageData pd) throws Exception;


	List<PageData> listGoodsListByClass(PageData pd) throws Exception;


	List<PageData> listGoodsListByName(PageData pd) throws Exception;


	void editGoodsPhoto(PageData pd) throws Exception;


	Double findAll(PageData pd) throws Exception;


	Double findAllByClass(PageData pd) throws Exception;


	Double findAllByName(PageData pd) throws Exception;


	PageData findByBarCode(PageData pd) throws Exception;

}
