package com.psi.entity;

import org.apache.shiro.session.Session;

import com.psi.util.Const;
import com.psi.util.Jurisdiction;

public class PsiBillCode {
	private String Code_ID;
	private String CodeType;
	private String MaxNo;
	private String NOTE;
	private String PK_SOBOOKS;
	
	
	public PsiBillCode() {
		Session session = Jurisdiction.getSession();
		if(session.getAttribute(Const.SESSION_USER) != null) {
			this.setPK_SOBOOKS((String)session.getAttribute(Const.SESSION_PK_SOBOOKS)); //帐套主键
		}
	}
	public PsiBillCode(String code_ID, String codeType) {
		super();
		Code_ID = code_ID;
		CodeType = codeType;
	}
	public String getCode_ID() {
		return Code_ID;
	}
	public void setCode_ID(String code_ID) {
		Code_ID = code_ID;
	}
	public String getCodeType() {
		return CodeType;
	}
	public void setCodeType(String codeType) {
		CodeType = codeType;
	}
	public String getMaxNo() {
		return MaxNo;
	}
	public void setMaxNo(String maxNo) {
		MaxNo = maxNo;
	}
	public String getNOTE() {
		return NOTE;
	}
	public void setNOTE(String nOTE) {
		NOTE = nOTE;
	}
	public String getPK_SOBOOKS() {
		return PK_SOBOOKS;
	}
	public void setPK_SOBOOKS(String pK_SOBOOKS) {
		PK_SOBOOKS = pK_SOBOOKS;
	}
	
}
