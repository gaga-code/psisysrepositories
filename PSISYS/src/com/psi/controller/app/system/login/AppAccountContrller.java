package com.psi.controller.app.system.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.psi.controller.base.BaseController;
import com.psi.entity.system.User;
import com.psi.service.app.system.user.AppUserManager;
import com.psi.service.basedata.accountset.AccountSetManager;
import com.psi.service.system.fhlog.FHlogManager;
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

	@Resource(name = "accountSetService")
	private AccountSetManager accountSetService;

	@Resource(name = "userService")
	private UserService userService;

	

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
		String PASSWORD = pd.getString("PASSWORD"); // 登录过来的密码

		String passwd = new SimpleHash("SHA-1", USERNAME, PASSWORD).toString(); // 密码加密
		pd.put("PASSWORD", passwd);
		pd.put("PK_SOBOOKS",pd.getString("PK_SOBOOKS"));
		
		pd = appUserService.getUserByNameAndPwd(pd); // 根据用户名和密码 账套主键 去读取用户信息
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
			user.setRIGHTS(pd.getString("PK_SOBOOKS"));
			session.setAttribute(Const.SESSION_USER, user); // 把用户信息放session中
			// shiro加入身份验证
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(USERNAME, PASSWORD);
		
		} else {
			logBefore(logger, USERNAME + "登录系统密码或用户名或账套错误");
			FHLOG.save(USERNAME, "登录系统密码或用户名或账套错误");
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

}
