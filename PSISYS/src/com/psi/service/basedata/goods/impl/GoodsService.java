package com.psi.service.basedata.goods.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.psi.dao.DaoSupport;
import com.psi.entity.Page;
import com.psi.entity.basedata.GoodsType;
import com.psi.service.basedata.goods.GoodsManager;
import com.psi.util.PageData;

/**
 * 说明： 商品管理
 */
@Service("goodsService")
public class GoodsService implements GoodsManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
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
			dict.setTreeurl("goods/list.do?GOODTYPE_ID="+dict.getGOODTYPE_ID());
			//dict.setSubDict(this.listAllDict(dict.getGOODTYPE_ID()));
			map.put("PARENTS", dict.getGOODTYPE_ID());
			dict.setSubDict(this.listAllDict(map));
			dict.setTarget("treeFrame");
		}
		return dictList;
	}
	/**
	 * !!!入库单专用!!!
	 * 
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<GoodsType> inOrderListAllDict(Map<String,String> parentIdAndPK_SOBOOKS) throws Exception {
		List<GoodsType> dictList = this.listSubDictByParentId(parentIdAndPK_SOBOOKS);
		Map<String,String> map = new HashMap<String, String>();
		map.put("PK_SOBOOKS", parentIdAndPK_SOBOOKS.get("PK_SOBOOKS"));
		for(GoodsType dict : dictList){
			dict.setTreeurl("inorder/goodslist.do?GOODTYPE_ID="+dict.getGOODTYPE_ID());
			//dict.setSubDict(this.listAllDict(dict.getGOODTYPE_ID()));
			map.put("PARENTS", dict.getGOODTYPE_ID());
			dict.setSubDict(this.inOrderListAllDict(map));
			dict.setTarget("treeFrame");
		}
		return dictList;
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
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("GoodsMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.update("GoodsMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("GoodsMapper.edit", pd);
	}
	
	/**修改库存
	 * @param pd
	 * @throws Exception
	 */
	public void editKuCun(PageData pd)throws Exception{
		dao.update("GoodsMapper.editKuCun", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("GoodsMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("GoodsMapper.listAll", pd);
	}
	
	/**通过产品编码
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listByBm(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("GoodsMapper.listByBm", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("GoodsMapper.findById", pd);
	}
	
	/**通过id获取数据(查看详细信息)
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByIdToCha(PageData pd)throws Exception{
		return (PageData)dao.findForObject("GoodsMapper.findByIdToCha", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("GoodsMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**商品入库，增加库存
	 * @param pd
	 * @throws Exception
	 */
	public void editZCOUNT(PageData pd)throws Exception{
		dao.update("GoodsMapper.editZCOUNT", pd);
	}
	
}

