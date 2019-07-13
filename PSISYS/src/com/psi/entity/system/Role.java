package com.psi.entity.system;

import java.sql.Timestamp;

/**
 * 说明：角色
 */
public class Role {
	private String ROLE_ID;
	private String ROLE_NAME;
	private String RIGHTS;
	private String PARENT_ID;
	private String ADD_QX;
	private String DEL_QX;
	private String EDIT_QX;
	private String CHA_QX;
	private String PK_SOBOOKS;  //帐套主键
	private String DEF1;  		//自定义项1
	private String DEF2;  		//自定义项2
	private String DEF3;  		//自定义项3
	private String DEF4;  		//自定义项4
	private String DEF5;  		//自定义项5
	private Timestamp CREATETIME;//创建时间
	private Timestamp LASTTIME;  //最后操作时间
	private String DR;   		//逻辑删除
	
	public String getPK_SOBOOKS() {
		return PK_SOBOOKS;
	}
	public void setPK_SOBOOKS(String pK_SOBOOKS) {
		PK_SOBOOKS = pK_SOBOOKS;
	}
	public String getDEF1() {
		return DEF1;
	}
	public void setDEF1(String dEF1) {
		DEF1 = dEF1;
	}
	public String getDEF2() {
		return DEF2;
	}
	public void setDEF2(String dEF2) {
		DEF2 = dEF2;
	}
	public String getDEF3() {
		return DEF3;
	}
	public void setDEF3(String dEF3) {
		DEF3 = dEF3;
	}
	public String getDEF4() {
		return DEF4;
	}
	public void setDEF4(String dEF4) {
		DEF4 = dEF4;
	}
	public String getDEF5() {
		return DEF5;
	}
	public void setDEF5(String dEF5) {
		DEF5 = dEF5;
	}
	public Timestamp getCREATETIME() {
		return CREATETIME;
	}
	public void setCREATETIME(Timestamp cREATETIME) {
		CREATETIME = cREATETIME;
	}
	public Timestamp getLASTTIME() {
		return LASTTIME;
	}
	public void setLASTTIME(Timestamp lASTTIME) {
		LASTTIME = lASTTIME;
	}
	public String getDR() {
		return DR;
	}
	public void setDR(String dR) {
		DR = dR;
	}
	public String getROLE_ID() {
		return ROLE_ID;
	}
	public void setROLE_ID(String rOLE_ID) {
		ROLE_ID = rOLE_ID;
	}
	public String getROLE_NAME() {
		return ROLE_NAME;
	}
	public void setROLE_NAME(String rOLE_NAME) {
		ROLE_NAME = rOLE_NAME;
	}
	public String getRIGHTS() {
		return RIGHTS;
	}
	public void setRIGHTS(String rIGHTS) {
		RIGHTS = rIGHTS;
	}
	public String getPARENT_ID() {
		return PARENT_ID;
	}
	public void setPARENT_ID(String pARENT_ID) {
		PARENT_ID = pARENT_ID;
	}
	public String getADD_QX() {
		return ADD_QX;
	}
	public void setADD_QX(String aDD_QX) {
		ADD_QX = aDD_QX;
	}
	public String getDEL_QX() {
		return DEL_QX;
	}
	public void setDEL_QX(String dEL_QX) {
		DEL_QX = dEL_QX;
	}
	public String getEDIT_QX() {
		return EDIT_QX;
	}
	public void setEDIT_QX(String eDIT_QX) {
		EDIT_QX = eDIT_QX;
	}
	public String getCHA_QX() {
		return CHA_QX;
	}
	public void setCHA_QX(String cHA_QX) {
		CHA_QX = cHA_QX;
	}
	
	
}
