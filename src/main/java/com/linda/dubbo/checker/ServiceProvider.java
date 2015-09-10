package com.linda.dubbo.checker;

import java.util.List;

public class ServiceProvider {

	private String providerUrl;

	private String host;
	private int port;
	private String scheme;
	private String path;
	
	private long timestamp;
	private String version;
	private int pid;
	private String reversion;
	private List<String> methods;
	private String iface;
	private String dubboVersion;
	private String application;
	private String anyhost;

	public String getProviderUrl() {
		return providerUrl;
	}

	public void setProviderUrl(String providerUrl) {
		this.providerUrl = providerUrl;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getReversion() {
		return reversion;
	}

	public void setReversion(String reversion) {
		this.reversion = reversion;
	}

	public List<String> getMethods() {
		return methods;
	}

	public void setMethods(List<String> methods) {
		this.methods = methods;
	}

	public String getIface() {
		return iface;
	}

	public void setIface(String iface) {
		this.iface = iface;
	}

	public String getDubboVersion() {
		return dubboVersion;
	}

	public void setDubboVersion(String dubboVersion) {
		this.dubboVersion = dubboVersion;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getAnyhost() {
		return anyhost;
	}

	public void setAnyhost(String anyhost) {
		this.anyhost = anyhost;
	}

}
