package com.psi.util.enumClass;

public enum AuditState {
	Audited(1),     //已审核
	NotAudited(0);  //未审核
	
	private Integer value;
	AuditState(int num){
		value = num;
	}
}
