package com.psi.entity.system;

import java.sql.Timestamp;

import org.apache.james.mime4j.field.datetime.DateTime;

import com.psi.entity.Page;

/**
 * 说明：用户
 */
public class User {
	private String USER_ID;		//用户id
	private String USERNAME;	//用户名
	private String PASSWORD; 	//密码
	private String NAME;		//姓名
	private String RIGHTS;		//权限
	private String ROLE_ID;		//角色id
	private String LAST_LOGIN;	//最后登录时间
	private String IP;			//用户登录ip地址
	private String STATUS;		//状态
	private Role role;			//角色对象
	private Page page;			//分页对象
	private String SKIN;		//皮肤
	private String PK_SOBOOKS;  //帐套主键
	private String YICODE;      //拼音编码
	private String BIRTHDATEDATE;  //出生日期
	private String SEX;         //性别
	private String EDUCATION;   //学历
	private String NPLACE;  	//籍贯
	private String HIREDATE;    //入职日期
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
	public String getYICODE() {
		return YICODE;
	}
	public void setYICODE(String yICODE) {
		YICODE = yICODE;
	}
	public String getBIRTHDATEDATE() {
		return BIRTHDATEDATE;
	}
	public void setBIRTHDATEDATE(String bIRTHDATEDATE) {
		BIRTHDATEDATE = bIRTHDATEDATE;
	}
	public String getSEX() {
		return SEX;
	}
	public void setSEX(String sEX) {
		SEX = sEX;
	}
	public String getEDUCATION() {
		return EDUCATION;
	}
	public void setEDUCATION(String eDUCATION) {
		EDUCATION = eDUCATION;
	}
	public String getNPLACE() {
		return NPLACE;
	}
	public void setNPLACE(String nPLACE) {
		NPLACE = nPLACE;
	}
	public String getHIREDATE() {
		return HIREDATE;
	}
	public void setHIREDATE(String hIREDATE) {
		HIREDATE = hIREDATE;
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
	public String getSKIN() {
		return SKIN;
	}
	public void setSKIN(String sKIN) {
		SKIN = sKIN;
	}
	
	public String getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}
	public String getUSERNAME() {
		return USERNAME;
	}
	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}
	public String getPASSWORD() {
		return PASSWORD;
	}
	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getRIGHTS() {
		return RIGHTS;
	}
	public void setRIGHTS(String rIGHTS) {
		RIGHTS = rIGHTS;
	}
	public String getROLE_ID() {
		return ROLE_ID;
	}
	public void setROLE_ID(String rOLE_ID) {
		ROLE_ID = rOLE_ID;
	}
	public String getLAST_LOGIN() {
		return LAST_LOGIN;
	}
	public void setLAST_LOGIN(String lAST_LOGIN) {
		LAST_LOGIN = lAST_LOGIN;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Page getPage() {
		if(page==null)
			page = new Page();
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	
}
