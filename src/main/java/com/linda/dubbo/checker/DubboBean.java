package com.linda.dubbo.checker;

public class DubboBean {

	private String service;
	private String version;

	public DubboBean(String service,String version){
		this.service = service;
		this.version = version;
	}
	
	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
