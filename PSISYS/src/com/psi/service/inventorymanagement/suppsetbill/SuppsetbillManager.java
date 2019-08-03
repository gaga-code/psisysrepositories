package com.psi.service.inventorymanagement.suppsetbill;


import java.util.List;

import com.psi.entity.Page;
import com.psi.util.PageData;

/**
 * 说明： 供应商结算单
 */
public interface SuppsetbillManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String DATA_IDS,String PK_SOBOOKS)throws Exception;

	/**
	 * 批量审批
	 * @param substring
	 * @param string
	 */
	public void approvalAll(String[] ids)throws Exception;
	/**
	 * 批量结算
	 * @param substring
	 * @param string
	 */
	public void settleAll(String substring, String string)throws Exception;

	/**
	 * 单张审批
	 * @param pd
	 * @throws Exception
	 */
	public void approvalone(PageData pd)throws Exception;

	/**
	 * 单张反审
	 * @param pd
	 * @throws Exception
	 */
	public void unapprovalone(PageData pd)throws Exception;

	public List<PageData> listInOderBypayment(Page page) throws Exception;
	
	
	
}

