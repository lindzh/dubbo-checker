package com.linda.dubbo.checker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.log4j.Logger;

public class SensorUtils {
	
	private static Logger logger = Logger.getLogger(SensorUtils.class);
	
	public static Properties loadProperties(String file){
		try {
			Properties properties = new Properties();
			properties.load(new FileInputStream(new File(file)));
			return properties;
		} catch (FileNotFoundException e) {
			logger.error("load file:"+file,e);
		} catch (IOException e) {
			logger.error("load file:"+file,e);
		}
		return null;
	}
	
	public static List<DubboBean> readProperties(Properties properties){
		ArrayList<DubboBean> beans = new ArrayList<DubboBean>();
		Set<Object> keys = properties.keySet();
		for(Object key:keys){
			String service = (String)key;
			String version = (String)properties.get(key);
			DubboBean bean = new DubboBean(service, version);
			beans.add(bean);
		}
		return beans;
	}
	
	public static List<String> getLocalV4IPs(){
		List<String> ips = new ArrayList<String>();
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while(interfaces.hasMoreElements()){
				NetworkInterface ni = interfaces.nextElement();
				String name = ni.getDisplayName();
				if(!ni.isLoopback()&&!ni.isVirtual()&&ni.isUp()){
					if(name==null||!name.contains("Loopback")){
						Enumeration<InetAddress> addresses = ni.getInetAddresses();
						while(addresses.hasMoreElements()){
							InetAddress address = addresses.nextElement();
							String ip = address.getHostAddress();
							if(!ip.contains(":")){
								ips.add(ip);
							}
						}
					}
				}
			}
		} catch (SocketException e) {
			logger.error("localips error",e);
		}
		return ips;
	}
	
	public static ServiceProvider convertProvider(String providerUrl){
		try{
			ServiceProvider provider = new ServiceProvider();
			provider.setProviderUrl(providerUrl);
			String url = URLDecoder.decode(providerUrl, "utf-8");
			URIBuilder builder = new URIBuilder(url);
			String host = builder.getHost();
			provider.setHost(host);
			int port = builder.getPort();
			provider.setPort(port);
			String scheme = builder.getScheme();
			provider.setScheme(scheme);
			String path = builder.getPath();
			provider.setPath(path);
			List<NameValuePair> params = builder.getQueryParams();
			Map<String, String> paramMap = SensorUtils.toMap(params);
			provider.setAnyhost(paramMap.get("anyhost"));
			provider.setApplication(paramMap.get("application"));
			provider.setDubboVersion(paramMap.get("dubbo"));
			provider.setIface(paramMap.get("interface"));
			String methods = paramMap.get("methods");
			if(methods!=null){
				String[] split = methods.split(",");
				provider.setMethods(Arrays.asList(split));
			}
			String pid = paramMap.get("pid");
			if(pid!=null){
				provider.setPid(Integer.parseInt(pid));
			}
			provider.setReversion(paramMap.get("revision"));
			String timestamp = paramMap.get("timestamp");
			if(timestamp!=null){
				provider.setTimestamp(Long.parseLong(timestamp));
			}
			provider.setVersion(paramMap.get("version"));
			return provider;
		}catch(Exception e){
			logger.error("parse:"+providerUrl,e);
		}
		return null;
	}
	
	public static Map<String,String> toMap(List<NameValuePair> params){
		HashMap<String,String> map = new HashMap<String,String>();
		if(params!=null){
			for(NameValuePair kv:params){
				map.put(kv.getName(), kv.getValue());
			}
		}
		return map;
	}

}
