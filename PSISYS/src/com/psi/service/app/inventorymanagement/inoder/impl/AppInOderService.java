package com.psi.service.app.inventorymanagement.inoder.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psi.dao.DaoSupport;
import com.psi.entity.PsiBillCode;
import com.psi.service.app.inventorymanagement.inoder.AppInOderManager;
import com.psi.service.system.BillCodePsi.BillCodeManager;
import com.psi.util.Const;
import com.psi.util.PageData;
import com.psi.util.ProductBillCodeUtil;
import com.psi.util.UuidUtil;

@Service
public class AppInOderService implements AppInOderManager {
	
	@Resource(name="daoSupport")
	private DaoSupport dao;

	
	@Autowired
	private ProductBillCodeUtil productBillCodeUtil;
	
	
	@Resource(name="billCodeService")
	private BillCodeManager billCodeService;
	
	@SuppressWarnings("unchecked")
	public List<PageData> listInOderByToday(PageData pd) throws Exception {
		
		return (List<PageData>)dao.findForList("AppInOderMapper.listInOderByToday", pd);
	}

	@Override
	public PageData listMountAndNum(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData)dao.findForObject("AppInOderMapper.listMountAndNum",pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listInOderGoods(PageData pd) throws Exception {
		
		return (List<PageData>)dao.findForList("AppInOderMapper.listInOderGoods", pd);
	}

	@Override
	public PageData listMountAndNumByMD(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData)dao.findForObject("AppInOderMapper.listMountAndNumByMD",pd);
	}

	@Override
	public List<PageData> listInOderGoodsByMD(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("AppInOderMapper.listInOderGoodsByMD",pd);
	}

	@Override
	public List<PageData> listInOrder(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("AppInOderMapper.listInOrder", pd);
	}

	@Override
	public List<PageData> listInOrderBody(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return  (List<PageData>)dao.findForList("AppInOderMapper.listInOrderBody", pd);
	}
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		String[] strs = productBillCodeUtil.getBillCode(Const.BILLCODE_INORDER_PFIX); //获取该编号类型的最大编号
		pd.put("BILLCODE", strs[0]);
		//保存商品
		String goodslist = (String) pd.get("goodslist");
		String[] split = goodslist.split("#");
		//遍历每行数据
		for(int i = 0; i < split.length; i++) {
			String[] agoods = split[i].split(",");
			PageData pageData = new PageData();
			pageData.put("INORDERBODY_ID", UuidUtil.get32UUID());
			pageData.put("INORDER_ID", pd.get("INORDER_ID"));
			pageData.put("PK_SOBOOKS", pd.get("PK_SOBOOKS"));
			pageData.put("APPBILLNO", strs[0]);
			
			pageData.put("BARCODE", agoods[1]);
			pageData.put("WAREHOUSE_ID", agoods[2]);
			pageData.put("UNITPRICE_ID", agoods[3]);
			pageData.put("PNUMBER", agoods[4]);
			pageData.put("AMOUNT", agoods[5]);
			pageData.put("NOTE", agoods[6]);
			
			if(agoods.length==8){ //如果BARCODE有值，agoods的长度是9
				pageData.put("GOODCODE_ID", agoods[7]);
			}else{  //否则长度为8
				pageData.put("GOODCODE_ID", null);
			}
			
			dao.save("InOrderBodyMapper.save", pageData);
		}
		dao.save("InOrderMapper.save", pd);
		//保存进货单编号
		if(strs[1] == null){ //新增
			PsiBillCode psiBillCode = new PsiBillCode();
			psiBillCode.setCode_ID(UuidUtil.get32UUID());
			psiBillCode.setCodeType(Const.BILLCODE_INORDER_PFIX);
			psiBillCode.setMaxNo(strs[0]);
			psiBillCode.setNOTE("进货单编号");
			billCodeService.insertBillCode(psiBillCode);
		}else{//修改
			PageData ppp = new PageData();
			ppp.put("MaxNo",strs[0]);
			ppp.put("Code_ID", strs[1]);
			billCodeService.updateMaxNo(ppp);
		}
	}
}
