package com.psi.controller.app.system.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.psi.controller.base.BaseController;
import com.psi.entity.system.Menu;
import com.psi.entity.system.Role;
import com.psi.entity.system.User;
import com.psi.service.app.system.user.AppUserManager;
import com.psi.service.basedata.accountset.AccountSetManager;
import com.psi.service.system.buttonrights.ButtonrightsManager;
import com.psi.service.system.fhbutton.FhbuttonManager;
import com.psi.service.system.fhlog.FHlogManager;
import com.psi.service.system.role.RoleManager;
import com.psi.service.system.user.impl.UserService;
import com.psi.util.AppUtil;
import com.psi.util.Const;
import com.psi.util.DateUtil;
import com.psi.util.Jurisdiction;
import com.psi.util.PageData;
import com.psi.util.Tools;

@Controller
@RequestMapping("/appAccount")
public class AppAccountContrller extends BaseController {

	String menuUrl = "user/listUsers.do"; //菜单地址(权限用)
	
	@Resource(name = "accountSetService")
	private AccountSetManager accountSetService;

	@Resource(name = "userService")
	private UserService userService;
	@Resource(name="roleService")
	private RoleManager roleService;
	@Resource(name="fhbuttonService")
	private FhbuttonManager fhbuttonService;
	@Resource(name="buttonrightsService")
	private ButtonrightsManager buttonrightsService;
	@Resource(name = "appUserService")
	private AppUserManager appUserService;

	
	@Resource(name = "fhlogService")
	private FHlogManager FHLOG;

	@RequestMapping(value = "/getaccountlist", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object getaccountlist() throws Exception {

		PageData pd = new PageData();
		pd = this.getPageData();
		// 获取账套列表
		pd.put("varList", getAccountSetList(pd));
		return pd;
	}

	/**
	 * 获取账套列表
	 * 
	 * @param pd
	 * @return varList
	 * @throws Exception
	 */
	public List<PageData> getAccountSetList(PageData pd) throws Exception {
		List<PageData> varOList = accountSetService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		if (varOList == null || varOList.size() == 0)
			return varList;
		pd.put("SOBOOKS_ID", varOList.get(0).getString("SOBOOKS_ID"));
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("SOBOOKS_ID", varOList.get(i).getString("SOBOOKS_ID")); // 1
			vpd.put("ENTERPRISENAME", varOList.get(i).getString("ENTERPRISENAME")); // 2
			varList.add(vpd);
		}
		return varList;
	}

	/**
	 * 请求登录，验证用户
	 * 
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/checkUserLoginInfo")
	@ResponseBody
	public Object login() throws Exception {
		
		PageData pd = new PageData();
		pd = this.getPageData();
		
		Session session = Jurisdiction.getSession();

		String USERNAME = pd.getString("USERNAME"); // 登录过来的用户名
		String PASSWORD = pd.getString("PASSWORD"); // 登录过来的密码ccc

		String passwd = new SimpleHash("SHA-1", USERNAME, PASSWORD).toString(); // 密码加密
		pd.put("PASSWORD", passwd);
		pd.put("PK_SOBOOKS",pd.getString("PK_SOBOOKS"));
		String PK_SOBOOKS=pd.getString("PK_SOBOOKS");
		pd = appUserService.getUserByNameAndPwd(pd); // 根据用户名和密码 账套主键 去读取用户信息
		
		String errorMessage="";
		if (pd != null) {
			this.removeSession(USERNAME);// 请缓存
			pd.put("LAST_LOGIN", DateUtil.getTime().toString());
			userService.updateLastLogin(pd);
			User user = new User();
			user.setUSER_ID(pd.getString("USER_ID"));
			user.setUSERNAME(pd.getString("USERNAME"));
			user.setPASSWORD(pd.getString("PASSWORD"));
			user.setNAME(pd.getString("NAME"));
			user.setRIGHTS(pd.getString("RIGHTS"));
			user.setPK_SOBOOKS(pd.getString("PK_SOBOOKS"));
			session.setAttribute(Const.SESSION_USER, user); // 把用户信息放session中
			session.setAttribute("PK_SOBOOKS", PK_SOBOOKS);
			// shiro加入身份验证
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(USERNAME, PASSWORD);
			AppUtil.returnObject(new PageData(), pd);
			this.login_index();
			pd.put("QX",Jurisdiction.getHC());	//按钮权限
		
		} else {
		/*	logBefore(logger, USERNAME + "登录系统密码或用户名或账套错误");
			FHLOG.save(USERNAME, "登录系统密码或用户名或账套错误");*/
			pd.put("errorMessage", "你输入的信息错误,请重新输入!");
		}
		
		return pd;
	}

	/**
	 * 清理session
	 */
	public void removeSession(String USERNAME) {
		Session session = Jurisdiction.getSession(); // 以下清除session缓存
		session.removeAttribute(Const.SESSION_USER);
		session.removeAttribute(USERNAME + Const.SESSION_ROLE_RIGHTS);
		session.removeAttribute(USERNAME + Const.SESSION_allmenuList);
		session.removeAttribute(USERNAME + Const.SESSION_menuList);
		session.removeAttribute(USERNAME + Const.SESSION_QX);
		session.removeAttribute(Const.SESSION_userpds);
		session.removeAttribute(Const.SESSION_USERNAME);
		session.removeAttribute(Const.SESSION_U_NAME);
		session.removeAttribute(Const.SESSION_USERROL);
		session.removeAttribute(Const.SESSION_PK_SOBOOKS);
		session.removeAttribute("changeMenu");
		session.removeAttribute("DEPARTMENT_IDS");
		session.removeAttribute("DEPARTMENT_ID");
	}

	
	/**访问系统首页
	 * @param changeMenu：切换菜单参数
	 * @return
	 */
	public void login_index(){
		 String changeMenu ="index";
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Session session = Jurisdiction.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);						//读取session中的用户信息(单独用户信息)
			if (user != null) {
				User userr = (User)session.getAttribute(Const.SESSION_USERROL);				//读取session中的用户信息(含角色信息)
				if(null == userr){
					user = userService.getUserAndRoleById(user.getUSER_ID());				//通过用户ID读取用户信息和角色信息
					session.setAttribute(Const.SESSION_USERROL, user);						//存入session	
				}else{
					user = userr;
				}
				String USERNAME = user.getUSERNAME();
				Role role = user.getRole();													//获取用户角色
				String roleRights = role!=null ? role.getRIGHTS() : "";						//角色权限(菜单权限)
				session.setAttribute(USERNAME + Const.SESSION_ROLE_RIGHTS, roleRights); 	//将角色权限存入session
				session.setAttribute(Const.SESSION_USERNAME, USERNAME);						//放入用户名到session
				session.setAttribute(Const.SESSION_U_NAME, user.getNAME());					//放入用户姓名到session
				List<Menu> allmenuList = new ArrayList<Menu>();
			/*	allmenuList = this.getAttributeMenu(session, USERNAME, roleRights);			//菜单缓存
				List<Menu> menuList = new ArrayList<Menu>();
				menuList = this.changeMenuF(allmenuList, session, USERNAME, changeMenu);	//切换菜单
*/				if(null == session.getAttribute(USERNAME + Const.SESSION_QX)){
					session.setAttribute(USERNAME + Const.SESSION_QX, this.getUQX(USERNAME));//按钮权限放到session中
				}
				this.getRemortIP(USERNAME);	//更新登录IP
			
			}else {
				mv.setViewName("system/index/login");//session失效后跳转登录页面
			}
		} catch(Exception e){
			mv.setViewName("system/index/login");
			logger.error(e.getMessage(), e);
		}

	}
	
	
	/**获取用户权限
	 * @param session
	 * @return
	 */
	public Map<String, String> getUQX(String USERNAME){
		PageData pd = new PageData();
		Map<String, String> map = new HashMap<String, String>();
		try {
			pd.put(Const.SESSION_USERNAME, USERNAME);
			pd.put("ROLE_ID", userService.findByUsername(pd).get("ROLE_ID").toString());//获取角色ID
			pd = roleService.findObjectById(pd);										//获取角色信息														
			map.put("adds", pd.getString("ADD_QX"));	//增
			map.put("dels", pd.getString("DEL_QX"));	//删
			map.put("edits", pd.getString("EDIT_QX"));	//改
			map.put("chas", pd.getString("CHA_QX"));	//查
			List<PageData> buttonQXnamelist = new ArrayList<PageData>();
			if("admin".equals(USERNAME)){
				buttonQXnamelist = fhbuttonService.listAll(pd);					//admin用户拥有所有按钮权限
			}else{
				buttonQXnamelist = buttonrightsService.listAllBrAndQxname(pd);	//此角色拥有的按钮权限标识列表
			}
			for(int i=0;i<buttonQXnamelist.size();i++){
				map.put(buttonQXnamelist.get(i).getString("QX_NAME"),"1");		//按钮权限
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}	
		return map;
	}
	
	
	/** 更新登录用户的IP
	 * @param USERNAME
	 * @throws Exception
	 */
	public void getRemortIP(String USERNAME) throws Exception {  
		PageData pd = new PageData();
		HttpServletRequest request = this.getRequest();
		String ip = "";
		if (request.getHeader("x-forwarded-for") == null) {  
			ip = request.getRemoteAddr();  
	    }else{
	    	ip = request.getHeader("x-forwarded-for");  
	    }
		pd.put("USERNAME", USERNAME);
		pd.put("IP", ip);
		userService.saveIP(pd);
	}  
	
}
