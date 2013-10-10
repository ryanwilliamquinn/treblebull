package com.rquinn.darts.action;

import org.apache.struts2.ServletActionContext;

public class HomeAction extends BaseAction {

	public String load() {
	    initializePageData(ServletActionContext.getRequest());
		return "success";
	}
	
}
