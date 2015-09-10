package com.linda.dubbo.checker;

public interface DubboChecker extends Service{
	
	/**
	 * 检测服务是否存在
	 * @param service
	 * @param version
	 * @return
	 */
	public boolean exist(String service,String version);
	
	/**
	 * 检测某机上是否部署了某服务
	 * @param service
	 * @param version
	 * @param host
	 * @param port
	 * @param timeDiff
	 * @return
	 */
	public boolean exist(String service,String version,String host,int port,long timeDiff);
	
	/**
	 * 检测本机是否部署了该服务
	 * @param service
	 * @param version
	 * @param timediff
	 * @return
	 */
	public boolean existSelf(String service,String version,long timediff);

}
