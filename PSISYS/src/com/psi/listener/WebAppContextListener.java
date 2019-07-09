package com.psi.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.psi.util.Const;

/**
 * 说明：WebAppContextListener
 */
public class WebAppContextListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub
	}

	public void contextInitialized(ServletContextEvent event) {
		// TODO Auto-generated method stub
		Const.WEB_APP_CONTEXT = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		//System.out.println("========获取Spring WebApplicationContext");
	}

}
