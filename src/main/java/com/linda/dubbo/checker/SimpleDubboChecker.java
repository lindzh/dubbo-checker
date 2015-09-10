package com.linda.dubbo.checker;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

public class SimpleDubboChecker implements DubboChecker {

	private DubboProviderService providerService;
	
	private Logger logger = Logger.getLogger(SimpleDubboChecker.class);

	public DubboProviderService getProviderService() {
		return providerService;
	}

	public void setProviderService(DubboProviderService providerService) {
		this.providerService = providerService;
	}
	
	private boolean existIterator(List<ServiceProvider> services,ExistCallback callback){
		if(services!=null){
			for(ServiceProvider provider:services){
				if(callback.exist(provider)){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean exist(final String service, final String version) {
		List<ServiceProvider> services = providerService.listServices(service);
		return this.existIterator(services, new ExistCallback(){
			public boolean exist(ServiceProvider provider) {
				return SimpleDubboChecker.this.checkVersion(provider, version);
			}
		});
	}
	
	private boolean checkVersion(ServiceProvider provider,String version){
		if(version!=null){
			if(provider.getVersion().equals(version)){
				return true;
			}else{
				return false;
			}
		}
		return true;
	}
	
	private boolean checkTimeDiff(ServiceProvider provider,long timeDiff){
		long now = System.currentTimeMillis();
		if(timeDiff>0){
			long diff = Math.abs(now-provider.getTimestamp());
			if(timeDiff<diff){
				return false;
			}
		}
		return true;
	}

	public boolean exist(final String service, final String version, final String host, final int port, final long timeDiff) {
		List<ServiceProvider> services = providerService.listServices(service);
		return this.existIterator(services, new ExistCallback(){
			public boolean exist(ServiceProvider provider) {
				if(!SimpleDubboChecker.this.checkVersion(provider, version)){
					return false;
				}
				if(!SimpleDubboChecker.this.checkTimeDiff(provider, timeDiff)){
					logger.info("notExistTimeDiff:service:"+service+" version:"+version+" starttime:"+new Date(provider.getTimestamp())+" content:"+JSONUtils.toJSON(provider));
					return false;
				}
				if(host!=null){
					if(!provider.getHost().equals(host)){
						logger.info("notExistHost:service:"+service+" version:"+version+" host:"+host+" content:"+JSONUtils.toJSON(provider));
						return false;
					}
				}
				if(port>0&&provider.getPort()!=port){
					logger.info("notExistPort:service:"+service+" version:"+version+" host:"+host+" port:"+port+" content:"+JSONUtils.toJSON(provider));
					return false;
				}
				logger.info("existProvider:service:"+service+" version:"+version+" starttime:"+new Date(provider.getTimestamp())+" content:"+JSONUtils.toJSON(provider));
				return true;
			}
		});
	}
	
	private boolean checkSelfIP(ServiceProvider provider){
		List<String> ips = SensorUtils.getLocalV4IPs();
		if(ips!=null){
			return ips.contains(provider.getHost());
		}
		return true;
	}

	public boolean existSelf(final String service, final String version, final long timeDiff) {
		List<ServiceProvider> services = providerService.listServices(service);
		return this.existIterator(services, new ExistCallback(){
			public boolean exist(ServiceProvider provider) {
				if(!SimpleDubboChecker.this.checkVersion(provider, version)){
					return false;
				}
				
				if(!SimpleDubboChecker.this.checkTimeDiff(provider, timeDiff)){
					logger.info("notExistTimeDiff:service:"+service+" version:"+version+" starttime:"+new Date(provider.getTimestamp())+" content:"+JSONUtils.toJSON(provider));
					return false;
				}

				if(!SimpleDubboChecker.this.checkSelfIP(provider)){
					logger.info("notExistSelfIP:service:"+service+" version:"+version+" host:"+provider.getHost()+" content:"+JSONUtils.toJSON(provider));
					return false;
				}
				logger.info("existSelf:service:"+service+" version:"+version+" starttime:"+new Date(provider.getTimestamp())+" content:"+JSONUtils.toJSON(provider));
				return true;
			}
		});
	}

	public void startService() {
		
	}

	public void stopService() {
		
	}
}
