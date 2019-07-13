package com.psi.entity.system;

import java.sql.Timestamp;
import java.util.List;

import org.apache.shiro.session.Session;

import com.psi.util.Const;
import com.psi.util.Jurisdiction;

/**
 * 说明：菜单
 */
public class Menu {
	
	private String MENU_ID;		//菜单ID
	private String MENU_NAME;	//菜单名称
	private String MENU_URL;	//链接
	private String PARENT_ID;	//上级菜单ID
	private String MENU_ORDER;	//排序
	private String MENU_ICON;	//图标
	private String MENU_TYPE;	//类型
	private String MENU_STATE;	//菜单状态
	private String target;
	private Menu parentMenu;
	private List<Menu> subMenu;
	private boolean hasMenu = false;
	private String PK_SOBOOKS;  //帐套主键
	private String DEF1;  		//自定义项1
	private String DEF2;  		//自定义项2
	private String DEF3;  		//自定义项3
	private String DEF4;  		//自定义项4
	private String DEF5;  		//自定义项5
	private Timestamp CREATETIME;//创建时间
	private Timestamp LASTTIME;  //最后操作时间
	private String DR;   		//逻辑删除
	public Menu() {
		Session session = Jurisdiction.getSession();
		if(session.getAttribute(Const.SESSION_USER) != null) {
			this.setPK_SOBOOKS((String)session.getAttribute(Const.SESSION_PK_SOBOOKS)); //帐套主键
		}
	}
	
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
	public String getMENU_ID() {
		return MENU_ID;
	}
	public void setMENU_ID(String mENU_ID) {
		MENU_ID = mENU_ID;
	}
	public String getMENU_NAME() {
		return MENU_NAME;
	}
	public void setMENU_NAME(String mENU_NAME) {
		MENU_NAME = mENU_NAME;
	}
	public String getMENU_URL() {
		return MENU_URL;
	}
	public void setMENU_URL(String mENU_URL) {
		MENU_URL = mENU_URL;
	}
	public String getPARENT_ID() {
		return PARENT_ID;
	}
	public void setPARENT_ID(String pARENT_ID) {
		PARENT_ID = pARENT_ID;
	}
	public String getMENU_ORDER() {
		return MENU_ORDER;
	}
	public void setMENU_ORDER(String mENU_ORDER) {
		MENU_ORDER = mENU_ORDER;
	}
	public Menu getParentMenu() {
		return parentMenu;
	}
	public void setParentMenu(Menu parentMenu) {
		this.parentMenu = parentMenu;
	}
	public List<Menu> getSubMenu() {
		return subMenu;
	}
	public void setSubMenu(List<Menu> subMenu) {
		this.subMenu = subMenu;
	}
	public boolean isHasMenu() {
		return hasMenu;
	}
	public void setHasMenu(boolean hasMenu) {
		this.hasMenu = hasMenu;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getMENU_ICON() {
		return MENU_ICON;
	}
	public void setMENU_ICON(String mENU_ICON) {
		MENU_ICON = mENU_ICON;
	}
	public String getMENU_TYPE() {
		return MENU_TYPE;
	}
	public void setMENU_TYPE(String mENU_TYPE) {
		MENU_TYPE = mENU_TYPE;
	}
	public String getMENU_STATE() {
		return MENU_STATE;
	}
	public void setMENU_STATE(String mENU_STATE) {
		MENU_STATE = mENU_STATE;
	}
}
