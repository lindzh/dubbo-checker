package com.linda.dubbo.checker;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class ZkService implements Service{
	
	private CuratorFramework zkclient;
	
	private String namespace = "dubbo";
	
	private String connectString;
	
	private int connectTimeout = 8000;
	
	private int maxRetry = 5;
	
	private int baseSleepTime = 1000;
	
	public String getConnectString() {
		return connectString;
	}

	public void setConnectString(String connectString) {
		this.connectString = connectString;
	}
	
	public CuratorFramework getZkClient(){
		return this.zkclient;
	}
	
	public void startService() {
		ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(baseSleepTime, maxRetry);
		zkclient = CuratorFrameworkFactory.builder().namespace(namespace).connectString(connectString)
		.connectionTimeoutMs(connectTimeout).sessionTimeoutMs(connectTimeout).retryPolicy(retryPolicy).build();
		zkclient.start();
	}

	public void stopService() {
		if(zkclient!=null){
			zkclient.close();
		}
	}

}
