package com.linda.dubbo.checker;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CheckerCommandMain extends AbstractCheckerMain{
	
	private static Options buildCommands(){
		Options options = new Options();
		options.addOption("zk", true, "zk connect string");
		options.addOption("f",true,"dubbo check properties file");
		options.addOption("h",true,"provider host");
		options.addOption("p",true,"provider port");
		options.addOption("t",true,"zk regist provider time diff in second");
		options.addOption("help",false,"print help for command");
		options.addOption("m",true,"dubbo check module,s self,n normal,t time diff");
		options.addOption("rk",true,"dubbo xml replace key");
		options.addOption("rv",true,"dubbo xml replace value");
		return options;
	}
	
	private static CommandLine buildCommandLine(Options options,String [] args){
		String helpLine = "[-zk][-f][-t][-help][-m][-h][-p]";
		HelpFormatter formatter = new HelpFormatter();
		CommandLineParser parser = new DefaultParser();
		try {
			return parser.parse(options, args);
		} catch (ParseException e) {
			e.printStackTrace();
			formatter.printHelp(helpLine, options);
		}
		return null;
	}
	
	public static void main(String[] args) {
		Options options = CheckerCommandMain.buildCommands();
		CommandLine commandLine = CheckerCommandMain.buildCommandLine(options, args);
		String zkConnectString = commandLine.getOptionValue("zk");
		String filename = commandLine.getOptionValue("f");
		String mode = commandLine.getOptionValue("m");
		long diff = 0;
		if(commandLine.hasOption("t")){
			diff = Long.parseLong(commandLine.getOptionValue("t"))*1000;
		}
		String host = null;
		if(commandLine.hasOption("h")){
			host = commandLine.getOptionValue("h");
		}
		int port = 0;
		if(commandLine.hasOption("p")){
			port = Integer.parseInt(commandLine.getOptionValue("p"));
		}
		
		boolean help = false;
		if(commandLine.hasOption("help")){
			help = true;
		}
		
		String replaceKey = null;
		if(commandLine.hasOption("rk")){
			replaceKey = commandLine.getOptionValue("rk");
		}
		
		String replaceValue = null;
		if(commandLine.hasOption("rv")){
			replaceValue = commandLine.getOptionValue("rv");
		}
		boolean check = CheckerCommandMain.checkOptions(zkConnectString, mode, filename,diff, host, port, help);
		AbstractCheckerMain.executeCheck(check, filename, replaceKey, replaceValue, host, port, diff, mode, zkConnectString);
		System.exit(0);
	}

}
