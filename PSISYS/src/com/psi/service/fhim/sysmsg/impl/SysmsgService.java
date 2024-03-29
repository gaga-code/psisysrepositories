package com.psi.service.fhim.sysmsg.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.psi.dao.DaoSupport;
import com.psi.entity.Page;
import com.psi.service.fhim.sysmsg.SysmsgManager;
import com.psi.util.PageData;

/**
 * 说明： IM系统消息
 */
@Service("sysmsgService")
public class SysmsgService implements SysmsgManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("SysmsgMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("SysmsgMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("SysmsgMapper.edit", pd);
	}
	
	/**消息设置成已读
	 * @param pd
	 * @throws Exception
	 */
	public void read(PageData pd)throws Exception{
		dao.update("SysmsgMapper.read", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SysmsgMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SysmsgMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SysmsgMapper.findById", pd);
	}
	
	/**获取未读总数 
	 * @param pd
	 * @throws Exception
	 */
	public PageData getMsgCount(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SysmsgMapper.getMsgCount", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("SysmsgMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

