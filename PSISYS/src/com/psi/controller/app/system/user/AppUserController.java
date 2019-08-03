package com.psi.controller.app.system.user;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.psi.controller.base.BaseController;
import com.psi.service.app.system.user.AppUserManager;
import com.psi.util.PageData;

@Controller
@RequestMapping("/appUser")
public class AppUserController extends BaseController {
	
	@Resource(name="appUserService")
	private AppUserManager appUserService;
	
	@RequestMapping("/getUsersList")
	@ResponseBody
	public List<PageData> getUsersList() throws Exception{
		PageData pd=new PageData();
		pd=this.getPageData();
		List<PageData> list=appUserService.listUsers(pd);
		return list;
	}

}
