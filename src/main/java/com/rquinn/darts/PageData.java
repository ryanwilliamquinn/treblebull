package com.rquinn.darts;

import org.apache.commons.lang3.StringUtils;

public class PageData {

	public PageData() {}
	
	public String getDomain() {
		String domain = System.getenv("TB_DOMAIN");
		if (StringUtils.isBlank(domain)) {
			domain = "www.treblebull.com";
		}
		return domain;
	}

    public String getHttpsToggle() {
        String protocol = System.getenv("TB_HTTPS");
        if (StringUtils.isBlank(protocol)) {
            protocol = "http";
        }
        return protocol;
    }
	
}
