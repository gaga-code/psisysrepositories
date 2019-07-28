package com.psi.service.basedata.inoutcometype.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psi.dao.DaoSupport;
import com.psi.entity.Page;
import com.psi.service.basedata.inoutcometype.InOutComeTypeManager;
import com.psi.util.Const;
import com.psi.util.JdbcTempUtil;
import com.psi.util.Jurisdiction;
import com.psi.util.PageData;
import com.psi.util.UuidUtil;

/**
 * 说明： 收入支出类型 
 */
@Service("inOutComeTypeService")
public class InOutComeTypeService implements InOutComeTypeManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		pd.put("TYPECODE", UuidUtil.get32UUID());
		dao.save("InOutComeTypeMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.update("InOutComeTypeMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("InOutComeTypeMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("InOutComeTypeMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("InOutComeTypeMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("InOutComeTypeMapper.findById", pd);
	}
	
	
	@Autowired
	private JdbcTempUtil jdbcTempUtil;

/**
	 * 批量删除
	 * DATA_IDS   主键 
	 * PK_SOBOOKS  帐套主键
	 */
	public void deleteAll(String ArrayDATA_IDS[])throws Exception{
		StringBuffer idstr = new StringBuffer("");
		for(int i = 0; i < ArrayDATA_IDS.length; i++) {
			idstr.append("'"+ArrayDATA_IDS[i]+"',");
		}
		//表名和主键字段名
		jdbcTempUtil.deleteAll(idstr.toString().substring(0,idstr.toString().length()-1), (String)Jurisdiction.getSession().getAttribute(Const.SESSION_PK_SOBOOKS), "SYS_FHBUTTON", "FHBUTTON_ID");
	}
}

