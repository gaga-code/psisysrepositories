package com.psi.service.fhim.qgroup;

import java.util.List;

import com.psi.entity.Page;
import com.psi.util.PageData;

/**
 * 说明： 群组
 */
public interface QgroupManager{

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
	
	/**群检索列表
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> searchListAll(PageData pd)throws Exception;
	
	/**我在的全部群列表 
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> mylistAll(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**删除图片
	 * @param pd
	 * @throws Exception
	 */
	public void delTp(PageData pd)throws Exception;
	
}

