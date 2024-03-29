package com.psi.service.basedata.warehouse.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.psi.dao.DaoSupport;
import com.psi.entity.Page;
import com.psi.service.basedata.warehouse.WarehouseManager;
import com.psi.util.PageData;

/**
 * 说明： 仓库
 */
@Service("warehouseService")
public class WarehouseService implements WarehouseManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("WarehouseMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.update("WarehouseMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("WarehouseMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("WarehouseMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("WarehouseMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("WarehouseMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.update("WarehouseMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public String findByWid(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (String)dao.findForObject("WarehouseMapper.findByWid", pd);
	}

	@Override
	public String findByName(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (String)dao.findForObject("WarehouseMapper.findByName", pd);
	}

	@Override
	public List<PageData> listWarehouse(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("WarehouseMapper.listWarehouse", pd);
	}
	

}