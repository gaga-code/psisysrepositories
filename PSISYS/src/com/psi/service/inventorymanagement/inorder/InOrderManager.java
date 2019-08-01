package com.psi.service.inventorymanagement.inorder;

import java.util.HashMap;
import java.util.List;

import com.psi.entity.Page;
import com.psi.util.PageData;

/**
 * 说明： 进货单管理
 */
public interface InOrderManager{

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
	
	public void editFromSupp(PageData pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	/**列表
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listForSuppset(PageData pd)throws Exception;
	
	/**
	 * 结算单新增功能里的供应商选择拉出进货单
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listForSuppAdd(Page page)throws Exception;
	
	/**
	 * 根据供应商结算单主键获取其进货单，只有结算才会有
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listForBySuppsetId(PageData pd)throws Exception;
	
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
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
    /**
     * 结算单反审进货单功能
     * @param pd
     * @throws Exception
     */
	public void retrialInorder(PageData pd)throws Exception;

	
	/**审批
	 * @param pd
	 * @throws Exception
	 */
	public void updateshenpi(PageData pd)throws Exception;
	
	/**反审
	 * @param pd
	 * @throws Exception
	 */
	public void updatefanshen(PageData pd)throws Exception;

	/**批量审批
	 * @param arrayDATA_IDS
	 * @throws Exception
	 */
	public void updateshenpiAll(PageData pd)throws Exception;

	/**
	 * 
	 * @param inorderandbodylist
	 * @throws Exception
	 */
	public void settleInOrders(List<PageData> inorderandbodylist)throws Exception;

	/**
	 * 根据表头主键查询主子表 ，所有字段，备份时调用到
	 * @param inorderandbody
	 * @return
	 * @throws Exception
	 */
	public PageData findAllById(PageData inorderandbody)throws Exception;

	/**
	 * 根据进货单ID  获取其子表的列表
	 * @param pd
	 * @return
	 */
	public List<PageData> inOrderlistBody(PageData pd) throws Exception ;
	
}

