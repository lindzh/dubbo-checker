package com.linda.dubbo.checker;

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
		int port = Integer.parseInt(properties.getProperty("p"));
		String rk = properties.getProperty("rk");
		String rv = properties.getProperty("rv");
		if(host==null||!host.contains(".")){
			host = null;
		}
		System.out.println("properties:"+JSONUtils.toJSON(properties));
		boolean check = CheckerCommandMain.checkOptions(zkConnectString, mode, filename,diff, host, port, false);
		AbstractCheckerMain.executeCheck(check, filename, rk, rv, host, port, diff, mode, zkConnectString);
		System.exit(0);
	}

}
