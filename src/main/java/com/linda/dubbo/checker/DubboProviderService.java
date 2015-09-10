package com.linda.dubbo.checker;

import java.util.List;

public interface DubboProviderService extends Service{
	
	/**
	 * 获取服务列表
	 * @param service
	 * @return
	 */
	public List<ServiceProvider> listServices(String service);
	
}
