package com.psi.service.basedata.supplier;

import java.util.List;

import com.psi.entity.Page;
import com.psi.util.PageData;

/**
 * 说明： 供应商管理
 */
public interface SupplierManager{

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

	public PageData findByCode(PageData pd) throws Exception;

	public void saveSupplier(PageData pd) throws Exception;

	public String findByName(PageData pd) throws Exception;

	public List<PageData> listAllSupp(PageData pd) throws Exception;
	//<!-- 修改当前应支付金额 -->
	public void editAmount(PageData pd) throws Exception;
	
	//根据编号获取当前应付金额
	public String findAmountByCode(PageData pd) throws Exception;
	
	
}

