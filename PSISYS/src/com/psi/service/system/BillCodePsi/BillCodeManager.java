package com.psi.service.system.BillCodePsi;

import com.psi.entity.PsiBillCode;
import com.psi.util.PageData;

public interface BillCodeManager{
	/**
	 * 插入一条数据
	 * @param PsiBillCode
	 * @throws Exception
	 */
	public void insertBillCode(PsiBillCode psiBillCode) throws Exception;
	
	/**
	 * 根据编号类型返回当前编号的最大编号值MaxNo
	 * @param pd
	 * @return String 
	 * @throws Exception
	 */
	public PageData findMaxNoByCodeType(PageData pd) throws Exception;
	
	/**
	 * 修改最大流水号
	 * @param pd
	 * @throws Exception
	 */
	public void updateMaxNo(PageData pd) throws Exception;
}
