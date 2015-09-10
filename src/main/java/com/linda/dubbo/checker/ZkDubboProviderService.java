package com.linda.dubbo.checker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

public class ZkDubboProviderService extends ZkService implements DubboProviderService{
	
	private Logger logger = Logger.getLogger(ZkDubboProviderService.class);
	
	private ConcurrentHashMap<String, List<ServiceProvider>> cache = new ConcurrentHashMap<String, List<ServiceProvider>>();
	
	private String buildServiceProviderPath(String service){
		return "/"+service+"/providers";
	}
	
	public List<ServiceProvider> listServices(String service) {
		List<ServiceProvider> result = cache.get(service);
		if(result!=null){
			return result;
		}
		ArrayList<ServiceProvider> list = new ArrayList<ServiceProvider>();
		String providerPath = this.buildServiceProviderPath(service);
		try {
			List<String> providers = this.getZkClient().getChildren().forPath(providerPath);
			if(providers!=null){
				for(String provider:providers){
					ServiceProvider serviceProvider = SensorUtils.convertProvider(provider);
					list.add(serviceProvider);
				}
			}
			cache.put(service, list);
			logger.info("load service "+service+" providers:"+JSONUtils.toJSON(list));
		} catch (Exception e) {
			logger.error("listServices "+service,e);
		}
		return list;
	}

}
