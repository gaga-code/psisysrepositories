package com.psi.service.app.inventorymanagement.salebill.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psi.dao.DaoSupport;
import com.psi.entity.PsiBillCode;
import com.psi.service.app.inventorymanagement.salebill.AppSalebillManager;
import com.psi.service.system.BillCodePsi.BillCodeManager;
import com.psi.util.Const;
import com.psi.util.PageData;
import com.psi.util.ProductBillCodeUtil;
import com.psi.util.UuidUtil;

@Service("appSalebillService")
public class AppSalebillService implements AppSalebillManager {

	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	
	@Autowired
	private ProductBillCodeUtil productBillCodeUtil;
	
	
	@Resource(name="billCodeService")
	private BillCodeManager billCodeService;
	
	
	@Override
	public List<PageData> listDataAndNumAndPrice(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("AppSalebillMapper.listDataAndNumAndPrice", pd);
	}

	@Override
	public List<PageData> listSaleInfoByToday(PageData pd) throws Exception {
		
		return (List<PageData>)dao.findForList("AppSalebillMapper.listSaleInfoByToday", pd);
	}

	@Override
	public List<PageData> listSaleInfoDayByMouth(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("AppSalebillMapper.listSaleInfoDayByMouth", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listSaledGoodsBySTT(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("AppSalebillMapper.listSaledGoodsBySTT", pd);
	}

	@Override
	public List<PageData> listsalebill(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("AppSalebillMapper.listsalebill",pd);
	}

	@Override
	public List<PageData> listsalebillBody(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("AppSalebillMapper.listsalebillBody", pd);
	}

	@Override
	public List<PageData> listSalebillByMouth(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("AppSalebillMapper.listSalebillByMouth", pd);
	}

	@Override
	public PageData findPureAmountById(String Id) throws Exception {
		// TODO Auto-generated method stub
		return (PageData)dao.findForObject("AppSalebillMapper.findPureAmountById", Id);
	}


	@Override
	public List<PageData> listSaledByCustomer(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("AppSalebillMapper.listSaledByCustomer", pd);
	}

	@Override
	public List<PageData> listSaledByUser(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return  (List<PageData>)dao.findForList("AppSalebillMapper.listSaledByUser", pd);
	}

	@Override
	public PageData save(PageData pd) throws Exception {
		String[] strs = productBillCodeUtil.getBillCode(Const.BILLCODE_SALEBILL_PFIX); //获取该编号类型的最大编号
		pd.put("BILLCODE", strs[0]);
		//保存商品
		String goodslist = (String) pd.get("salebillbody");
		String[] split = goodslist.split("#");
		//遍历每行数据
		for(int i = 0; i < split.length; i++) {
			String[] agoods = split[i].split(",");
			PageData pageData = new PageData();
			pageData.put("FGROUP_ID", UuidUtil.get32UUID());
			pageData.put("SALEBILL_ID", pd.get("SALEBILL_ID"));
			pageData.put("PK_SOBOOKS", pd.get("PK_SOBOOKS"));
			pageData.put("APPBILLNO", strs[0]);
			
			pageData.put("BARCODE", agoods[1]);
			pageData.put("WAREHOUSE_ID", agoods[2]);
			pageData.put("UNITPRICE_ID", agoods[3]);
			pageData.put("PNUMBER", agoods[4]);
			pageData.put("AMOUNT", agoods[5]);
			pageData.put("ISFREE", agoods[6]);
			pageData.put("NOTE", agoods[7]);
			pageData.put("GOODCODE_ID", agoods[8]);
			
			dao.save("SalebillBodyMapper.save", pageData);
		}
		dao.save("SalebillMapper.save", pd);
		//保存销售单编号
		if(strs[1] == null){ //新增
			PsiBillCode psiBillCode = new PsiBillCode();
			psiBillCode.setCode_ID(UuidUtil.get32UUID());
			psiBillCode.setCodeType(Const.BILLCODE_SALEBILL_PFIX);
			psiBillCode.setMaxNo(strs[0]);
			psiBillCode.setNOTE("销售单编号");
			billCodeService.insertBillCode(psiBillCode);
		}else{//修改
			PageData ppp = new PageData();
			ppp.put("MaxNo",strs[0]);
			ppp.put("Code_ID", strs[1]);
			billCodeService.updateMaxNo(ppp);
		}
		return pd;
	}

	@Override
	public int listSaledGoodsBySTTNum(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (Integer)dao.findForObject("AppSalebillMapper.listSaledGoodsBySTTNum", pd);
	
	}

	@Override
	public int listSaledByCustomerNum(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (Integer)dao.findForObject("AppSalebillMapper.listSaledByCustomerNum", pd);
	}

	@Override
	public int listSaledByUserNum(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (Integer)dao.findForObject("AppSalebillMapper.listSaledByUserNum", pd);
	}

	@Override
	public int listsalebillNum(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (Integer)dao.findForObject("AppSalebillMapper.listsalebillNum", pd);
	}

}
