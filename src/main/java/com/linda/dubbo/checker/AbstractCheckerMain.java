package com.linda.dubbo.checker;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public class AbstractCheckerMain {
	
	public static void checkAction(List<DubboBean> beans,String zkConnectString,String mode,long diff,String host,int port){
		SimpleDubboCheckLogger dubboCheckLogger = new SimpleDubboCheckLogger();
		dubboCheckLogger.setZkConnectString(zkConnectString);
		dubboCheckLogger.startService();
		if(mode.equals("s")){
			System.out.println("start check self");
			dubboCheckLogger.checkSeld(beans, diff);
		}else if(mode.equals("t")){
			System.out.println("start check time diff");
			dubboCheckLogger.checkHostAndPort(beans, host, port, diff);
		}else if(mode.equals("n")){
			System.out.println("start check mormal");
			dubboCheckLogger.checkAndLog(beans);
		}
		System.out.println("check finish");
		dubboCheckLogger.stopService();
	}
	
	public static void printHelp(){
		System.out.println("-zk[str] zk connect string");
		System.out.println("-f[str] dubbo check properties file");
		System.out.println("-h[str] dubbo provider host");
		System.out.println("-p[str] dubbo provider port");
		System.out.println("-t[str] check time diff in second");
		System.out.println("-m[str] dubbo check module,s self,n normal,t time diff");
		System.out.println("-rk[str] dubbo xml replace version key");
		System.out.println("-rv[str]  dubbo xml replace version value");
		System.out.println("-help pring help string");
	}
	
	public static void executeCheck(boolean check,String filename,Map<String,String> replaceKeyValues,String host,int port,long diff,String mode,String zkConnectString){
		if(check){
			Properties properties = null;
			//xml dubbo配置文件
			if(filename.endsWith(".xml")){
				properties = XmlPropertyLoader.loadXML(filename, replaceKeyValues);
			}else{
				properties = SensorUtils.loadProperties(filename);
			}
			if(properties!=null){
				List<DubboBean> beans = SensorUtils.readProperties(properties);
				AbstractCheckerMain.checkAction(beans, zkConnectString, mode, diff, host, port);
			}else{
				System.out.println("load properties content null");
			}
		}
	}
	
	public static boolean checkOptions(String zkConnectString,String mode,String file,long diff,String host,int port,boolean help){
		boolean pass = true;
		if(help){
			AbstractCheckerMain.printHelp();
			pass = false;
		}else{
			if(zkConnectString==null||zkConnectString.length()<1){
				System.out.println("please input zk connect string");
				pass = false;
			}
			if(mode==null||mode.length()<1){
				System.out.println("please input check mode,s self,n normal,t time diff");
				pass = false;
			}
			
			if(file==null||file.length()<1){
				System.out.println("please input dubbo check properties file");
				pass = false;
			}
		}
		return pass;
	}

}
