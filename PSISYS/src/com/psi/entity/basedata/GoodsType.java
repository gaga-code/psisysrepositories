package com.psi.entity.basedata;

import java.util.List;

/**
 * 说明：商品分类
 */
public class GoodsType {

	private String TYPENAME;	//名称
	private String TYPECODE;	//编码
	private String USER_ID;		//经手人	
	private String PARENTS;		//上级ID
	private String PK_SOBOOKS;	//账套ID
	private String GOODTYPE_ID;	//主键
	private String target;
	private GoodsType dict;
	private List<GoodsType> subDict;
	private boolean hasDict = false;
	private String treeurl;
	
	public String getTYPENAME() {
		return TYPENAME;
	}
	public void setTYPENAME(String tYPENAME) {
		TYPENAME = tYPENAME;
	}
	public String getTYPECODE() {
		return TYPECODE;
	}
	public void setTYPECODE(String tYPECODE) {
		TYPECODE = tYPECODE;
	}
	public String getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}
	public String getPARENTS() {
		return PARENTS;
	}
	public void setPARENTS(String pARENTS) {
		PARENTS = pARENTS;
	}
	public String getPK_SOBOOKS() {
		return PK_SOBOOKS;
	}
	public void setPK_SOBOOKS(String pK_SOBOOKS) {
		PK_SOBOOKS = pK_SOBOOKS;
	}
	public String getGOODTYPE_ID() {
		return GOODTYPE_ID;
	}
	public void setGOODTYPE_ID(String gOODTYPE_ID) {
		GOODTYPE_ID = gOODTYPE_ID;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public GoodsType getDict() {
		return dict;
	}
	public void setDict(GoodsType dict) {
		this.dict = dict;
	}
	public List<GoodsType> getSubDict() {
		return subDict;
	}
	public void setSubDict(List<GoodsType> subDict) {
		this.subDict = subDict;
	}
	public boolean isHasDict() {
		return hasDict;
	}
	public void setHasDict(boolean hasDict) {
		this.hasDict = hasDict;
	}
	public String getTreeurl() {
		return treeurl;
	}
	public void setTreeurl(String treeurl) {
		this.treeurl = treeurl;
	}
	
}
