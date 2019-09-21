package com.psi.service.inventorymanagement.stock;

import java.util.HashMap;
import java.util.List;

import com.psi.entity.Page;
import com.psi.util.PageData;

/**
 * 说明： 仓库调拨
 */
public interface WhallocateManager{

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
	
	public void editFromCustomer(PageData pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	/**列表
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listForCustomer(PageData pd)throws Exception;
	
	/**
	 * 结算单新增功能里的供应商选择拉出销售单
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listForCustomerAdd(Page page)throws Exception;
	
	/**
	 * 根据供应商结算单主键获取其销售单，只有结算才会有
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listForByCustomersetId(PageData pd)throws Exception;
	
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
     * 结算单反审销售单功能
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
	public void settleSalebills(List<PageData> inorderandbodylist)throws Exception;

	/**
	 * 根据表头主键查询主子表 ，所有字段，备份时调用到
	 * @param inorderandbody
	 * @return
	 * @throws Exception
	 */
	public PageData findAllById(PageData inorderandbody)throws Exception;

	/**
	 * 检查指定库存是否存在指定商品
	 * @param pd  仓库ID  商品编号GOOD_ID
	 * @return
	 */
	public Integer getStock(PageData pd)throws Exception;

	public List<PageData> listAllToExcel(PageData pd) throws Exception;
	
}

