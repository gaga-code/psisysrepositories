package com.psi.service.basedata.goods;

import java.util.List;
import java.util.Map;

import com.psi.entity.Page;
import com.psi.entity.basedata.GoodsType;
import com.psi.util.PageData;

/**
 * 说明： 商品管理
 */
public interface GoodsManager{

	/**
	 * 库存预警
	 * 检查商品的库存是否低于下限
	 * 返回低于下限的商品的列表  包括字段： 名称、编号、当前库存、库存下限
	 */
	public List<PageData> checkGoodsStockDownNum(PageData pd) throws Exception;
	
	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List<GoodsType> listSubDictByParentId(Map<String,String> parentIdAndPK_SOBOOKS) throws Exception;
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<GoodsType> listAllDict(Map<String,String> parentIdAndPK_SOBOOKS) throws Exception;

	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<GoodsType> inOrderListAllDict(Map<String,String> parentIdAndPK_SOBOOKS) throws Exception;

	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<GoodsType> salebillListAllDict(Map<String,String> parentIdAndPK_SOBOOKS) throws Exception;

	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<GoodsType> stockCheckListAllDict(Map<String,String> parentIdAndPK_SOBOOKS) throws Exception;
	
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
	
	/**修改库存
	 * @param pd
	 * @throws Exception
	 */
	public void editKuCun(PageData pd)throws Exception;
	
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
	
	/**通过产品编码
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listByBm(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**通过id获取数据(查看详细信息)
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByIdToCha(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	/**商品入库，增加库存
	 * @param pd
	 * @throws Exception
	 */
	public void editZCOUNT(PageData pd)throws Exception;

	public Object findByGOODCODE(PageData pd) throws Exception;

	public String findByname(PageData pd) throws Exception;

	public void saveUnit(PageData pd) throws Exception;

	public void saveGoods(PageData pd) throws Exception;

	public Object findByCode(PageData pd) throws Exception;

	public List<PageData> listAllDetail(PageData pd) throws Exception;

	public String findPKBYName(PageData pd) throws Exception;

	public void editPic(PageData pd) throws Exception;


	public String findbyTypeId(PageData pd) throws Exception;

	public List<PageData> listAllInorderSale(Page page) throws Exception;

}

