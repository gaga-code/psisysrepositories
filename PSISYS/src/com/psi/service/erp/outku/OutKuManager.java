package com.psi.service.erp.outku;

import java.util.List;

import com.psi.entity.Page;
import com.psi.util.PageData;

/**
 * 说明：  商品出库
 */
public interface OutKuManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**新增到库存临时表
	 * @param pd
	 * @throws Exception
	 */
	public void saveOne(PageData pd)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**删除非今天数据(从出库记录临时表中)
	 * @param pd
	 * @throws Exception
	 */
	public void deleteOld(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**出库列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**列表(某一订单号下的出库记录)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAllByDingdan(PageData pd)throws Exception;
	
	/**订单列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> dingdanList(Page page)throws Exception;
	
	/**商品销售报表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> salesReport(Page page)throws Exception;
	
	/**列表临时(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listTemp(PageData pd)throws Exception;
	
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
	
	/**通过id获取数据(临时表)
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByIdFromTemp(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	/**总金额
	 * @param pd
	 * @throws Exception
	 */
	public PageData priceSum(PageData pd) throws Exception;
	
	/**临时表总金额
	 * @param pd
	 * @throws Exception
	 */
	public PageData priceSumTemp(PageData pd) throws Exception;
	
}

