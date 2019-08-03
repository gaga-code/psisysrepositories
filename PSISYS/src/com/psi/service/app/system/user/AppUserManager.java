package com.psi.service.app.system.user;

import java.util.List;

import com.psi.util.PageData;

public interface AppUserManager {

	PageData getUserByNameAndPwd(PageData pd) throws Exception;

	List<PageData> listUsers(PageData pd) throws Exception;

}
