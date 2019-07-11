package com.psi.service.basedata.payment.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psi.dao.DaoSupport;
import com.psi.entity.Page;
import com.psi.service.basedata.payment.PaymentManager;
import com.psi.util.PageData;
import com.psi.util.TransIDtoObjectUtil;

/**
 * 说明： 支付方式
 */
@Service("paymentService")
public class PaymentService implements PaymentManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Autowired
	private TransIDtoObjectUtil transIDtoObjectUtil;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("PaymentMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.update("PaymentMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("PaymentMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return ptransUserIdUserName((List<PageData>)dao.findForList("PaymentMapper.datalistPage", page));
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return ptransUserIdUserName((List<PageData>)dao.findForList("PaymentMapper.listAll", pd));
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return transUserIdToUserName((PageData)dao.findForObject("PaymentMapper.findById", pd));
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.update("PaymentMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**
	 * 经手人主键 转 名称
	 * @param pd 
	 * @return
	 */
	private PageData transUserIdToUserName(PageData pd) {
		String USERNAME = transIDtoObjectUtil.transIDtoString("sys_user", "USER_ID", (String) pd.get("USER_ID"), "NAME");
		pd.put("USERNAME", USERNAME);
		return pd;
	}
	/**
	 * 批量操作  经手人主键 转 名称
	 * @param list
	 * @return
	 */
	private List<PageData> ptransUserIdUserName(List<PageData> list){
		List<PageData> newlist = new ArrayList<PageData>();
		for(int i = 0; i < list.size(); i++) {
			newlist.add(transUserIdToUserName(list.get(i)));
		}
		return newlist;
	}
}