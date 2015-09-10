package com.linda.dubbo.checker;

import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CheckerFileMain extends AbstractCheckerMain{
	
	private static Options buildCommands(){
		Options options = new Options();
		options.addOption("file", true, "config file");
		return options;
	}
	
	public static void main(String[] args) {
		Options options = CheckerFileMain.buildCommands();
		CommandLineParser parser = new DefaultParser();
		String file = "config.properties";
		try {
			CommandLine commandLine = parser.parse(options, args);
			if(commandLine.hasOption("file")){
				file = commandLine.getOptionValue("file");
			}
		} catch (ParseException e) {
			e.printStackTrace();
			System.exit(0);
		}
		Properties properties = SensorUtils.loadProperties(file);
		
		String zkConnectString = properties.getProperty("zk");
		String filename = properties.getProperty("f");
		String mode = properties.getProperty("m");
		long diff = Long.parseLong(properties.getProperty("t"))*1000;
		String host =  properties.getProperty("h");
		if(host==null||!host.contains(".")){
			host = null;
		}
		int port = Integer.parseInt(properties.getProperty("p"));
		
		HashMap<String, String> kvMap = new HashMap<String,String>();
		String rkvCount = properties.getProperty("rkv.count");
		if(rkvCount!=null){
			int count = Integer.parseInt(rkvCount);
			if(count>0){
				for(int i=1;i<=count;i++){
					String rk = properties.getProperty("rk."+i);
					String rv = properties.getProperty("rv."+i);
					if(rk!=null&&rk.trim().length()>0){
						if(rv!=null){
							rv = rv.trim();
						}
						rk = rk.trim();
						kvMap.put(rk, rv);
					}
				}
			}
		}

		System.out.println("properties:"+JSONUtils.toJSON(properties));
		boolean check = CheckerCommandMain.checkOptions(zkConnectString, mode, filename,diff, host, port, false);
		AbstractCheckerMain.executeCheck(check, filename, kvMap, host, port, diff, mode, zkConnectString);
		System.exit(0);
	}

}
