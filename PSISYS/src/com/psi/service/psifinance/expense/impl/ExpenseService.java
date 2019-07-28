package com.psi.service.psifinance.expense.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.psi.dao.DaoSupport;
import com.psi.entity.Page;
import com.psi.entity.PsiBillCode;
import com.psi.service.psifinance.expense.ExpenseManager;
import com.psi.service.system.BillCodePsi.BillCodeManager;
import com.psi.util.Const;
import com.psi.util.JdbcTempUtil;
import com.psi.util.Jurisdiction;
import com.psi.util.PageData;
import com.psi.util.ProductBillCodeUtil;
import com.psi.util.UuidUtil;

/**
 * 说明： 费用开支单管理
 */
@Service("expenseService")
public class ExpenseService implements ExpenseManager{
	
	//用于批量删除
	@Autowired
	private JdbcTempUtil jdbcTempUtil;
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Autowired
	private ProductBillCodeUtil productBillCodeUtil;
	
	@Resource(name="billCodeService")
	private BillCodeManager billCodeService;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		String[] strs = productBillCodeUtil.getBillCode(Const.BILLCODE_EXPENSE_PFIX); //获取该编号类型的最大编号
		pd.put("BILLCODE", strs[0]);
		//保存费用开支明细
		String expensebodylist = (String) pd.get("expensebodylist");
		String[] split = expensebodylist.split("#");
		//遍历每行数据
		for(int i = 0; i < split.length; i++) {
			String[] aexpen = split[i].split(",");
			PageData pageData = new PageData();
			pageData.put("EXPENSEBODY_ID", UuidUtil.get32UUID());
			pageData.put("EXPENSE_ID", pd.get("EXPENSE_ID"));
			pageData.put("INOUTCOMETYPE_ID", aexpen[1]);
			pageData.put("AMOUNT", aexpen[2]);
			pageData.put("NOTE", aexpen[3]);
			dao.save("ExpenseBodyMapper.save", pageData);
		}
		dao.save("ExpenseMapper.save", pd);
		//保存费用开支单编号
		if(strs[1] == null){ //新增
			PsiBillCode psiBillCode = new PsiBillCode();
			psiBillCode.setCode_ID(UuidUtil.get32UUID());
			psiBillCode.setCodeType(Const.BILLCODE_EXPENSE_PFIX);
			psiBillCode.setMaxNo(strs[0]);
			psiBillCode.setNOTE("费用开支单编号");
			billCodeService.insertBillCode(psiBillCode);
		}else{//修改
			PageData ppp = new PageData();
			ppp.put("MaxNo",strs[0]);
			ppp.put("Code_ID", strs[1]);
			billCodeService.updateMaxNo(ppp);
		}
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.update("ExpenseMapper.delete", pd);
		dao.update("ExpenseBodyMapper.deleteByHeadId", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		//----------------编辑商品-------
		//保存费用开支明细
		String expensebodylist = (String) pd.get("expensebodylist");
		String[] split = expensebodylist.split("#");
		//遍历每行数据
		for(int i = 0; i < split.length; i++) {
			String[] aexpen = split[i].split(",");
			PageData pageData = new PageData();
			pageData.put("EXPENSEBODY_ID", aexpen[0]);
			pageData = (PageData) dao.findForObject("ExpenseBodyMapper.findById", pageData);
			pageData.put("INOUTCOMETYPE_ID", aexpen[2]);
			pageData.put("AMOUNT", aexpen[4]);
			pageData.put("NOTE", aexpen[5]);
			dao.update("ExpenseBodyMapper.edit", pageData);
		}
		dao.update("ExpenseMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ExpenseMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ExpenseMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		PageData result =  (PageData)dao.findForObject("ExpenseMapper.findById", pd);
		result.put("expensebodylist", (List<PageData>)dao.findForList("ExpenseBodyMapper.findByHeadId", pd));
		return result;
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
//	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
//		dao.update("ExpenseMapper.deleteAll", ArrayDATA_IDS);
//	}
	
	

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
		jdbcTempUtil.deleteAll(idstr.toString().substring(0,idstr.toString().length()-1), (String)Jurisdiction.getSession().getAttribute(Const.SESSION_PK_SOBOOKS), "psi_inorder", "INORDER_ID");
		jdbcTempUtil.deleteAll(idstr.toString().substring(0,idstr.toString().length()-1), (String)Jurisdiction.getSession().getAttribute(Const.SESSION_PK_SOBOOKS), "psi_inorder_body", "INORDER_ID");
	}





	
}

