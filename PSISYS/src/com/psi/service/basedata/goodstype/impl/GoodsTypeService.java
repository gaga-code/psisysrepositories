package com.psi.service.basedata.goodstype.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.psi.dao.DaoSupport;
import com.psi.entity.Page;
import com.psi.entity.basedata.GoodsType;
import com.psi.service.basedata.goodstype.GoodsTypeManager;
import com.psi.util.PageData;

/**
 * 说明： 商品分类
 */
@Service("goodsTypeService")
public class GoodsTypeService implements GoodsTypeManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("GoodsTypeMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.update("GoodsTypeMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("GoodsTypeMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("GoodsTypeMapper.datalistPage", page);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("GoodsTypeMapper.findById", pd);
	}
	
	/**通过编码获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByBianma(PageData pd)throws Exception{
		return (PageData)dao.findForObject("GoodsTypeMapper.findByBianma", pd);
	}
	
	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<GoodsType> listSubDictByParentId(Map<String,String> parentIdAndPK_SOBOOKS) throws Exception {
		return (List<GoodsType>) dao.findForList("GoodsTypeMapper.listSubDictByParentId", parentIdAndPK_SOBOOKS);
	}
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<GoodsType> listAllDict(Map<String,String> parentIdAndPK_SOBOOKS) throws Exception {
		List<GoodsType> dictList = this.listSubDictByParentId(parentIdAndPK_SOBOOKS);
		Map<String,String> map = new HashMap<String, String>();
		map.put("PK_SOBOOKS", parentIdAndPK_SOBOOKS.get("PK_SOBOOKS"));
		for(GoodsType dict : dictList){
			dict.setTreeurl("goodstype/list.do?GOODTYPE_ID="+dict.getGOODTYPE_ID());
			//dict.setSubDict(this.listAllDict(dict.getGOODTYPE_ID()));
			map.put("PARENTS", dict.getGOODTYPE_ID());
			dict.setSubDict(this.listAllDict(map));
			dict.setTarget("treeFrame");
		}
		return dictList;
	}
	
	/**排查表检查是否被占用
	 * @param pd
	 * @throws Exception
	 */
	public PageData findFromTbs(PageData pd)throws Exception{
		return (PageData)dao.findForObject("GoodsTypeMapper.findFromTbs", pd);
	}
	
}

