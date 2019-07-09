package com.psi.service.erp.outku.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.psi.dao.DaoSupport;
import com.psi.entity.Page;
import com.psi.service.erp.outku.OutKuManager;
import com.psi.util.PageData;

/**
 * 说明：  商品出库
 */
@Service("outkuService")
public class OutKuService implements OutKuManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("OutKuMapper.save", pd);
	}
	
	/**新增到库存临时表
	 * @param pd
	 * @throws Exception
	 */
	public void saveOne(PageData pd)throws Exception{
		dao.save("OutKuMapper.saveOne", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("OutKuMapper.delete", pd);
	}
	
	/**删除非今天数据(从出库记录临时表中)
	 * @param pd
	 * @throws Exception
	 */
	public void deleteOld(PageData pd)throws Exception{
		dao.delete("OutKuMapper.deleteOld", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("OutKuMapper.edit", pd);
	}
	
	/**出库列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OutKuMapper.datalistPage", page);
	}
	
	/**列表(某一订单号下的出库记录)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllByDingdan(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("OutKuMapper.alllistbydingdan", pd);
	}
	
	/**订单列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> dingdanList(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OutKuMapper.dingdanlistPage", page);
	}
	
	/**商品销售报表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> salesReport(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OutKuMapper.SalesReportlistPage", page);
	}
	
	/**列表临时(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listTemp(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("OutKuMapper.listTemp", pd);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("OutKuMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("OutKuMapper.findById", pd);
	}
	
	/**通过id获取数据(临时表)
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByIdFromTemp(PageData pd)throws Exception{
		return (PageData)dao.findForObject("OutKuMapper.findByIdFromTemp", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("OutKuMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**总金额
	 * @param pd
	 * @throws Exception
	 */
	public PageData priceSum(PageData pd) throws Exception{
		return (PageData)dao.findForObject("OutKuMapper.priceSum", pd);
	}
	
	/**临时表总金额
	 * @param pd
	 * @throws Exception
	 */
	public PageData priceSumTemp(PageData pd) throws Exception {
		return (PageData)dao.findForObject("OutKuMapper.priceSumTemp", pd);
	}
}

