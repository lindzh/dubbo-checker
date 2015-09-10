package com.linda.dubbo.checker;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest{
	
	public static void test(){
		String url = "dubbo://10.0.30.226:5443/com.linda.dubbo.UserService?anyhost=true&application=dubbo&dubbo=2.5.3&interface=com.linda.dubbo.UserService&methods=testToken2,getUserByArray,testObject,testRiskmy,testMm2,testRisk22my,sessionTest,testObjectToken,testNull,testObjAa,loginBytes,reg,getById,login,testTokenAndMobileType,getUsers&pid=7080&revision=1.2_ljtest&side=provider&timestamp=1435820150428&version=1.2_ljtest";
		try {
			URIBuilder builder = new URIBuilder(url);
			String host = builder.getHost();
			int port = builder.getPort();
			String scheme = builder.getScheme();
			String path = builder.getPath();
			List<NameValuePair> params = builder.getQueryParams();
			System.out.println("scheme:"+scheme);
			System.out.println("host:"+host);
			System.out.println("port:"+port);
			System.out.println("path:"+path);
			for(NameValuePair param:params){
				System.out.println(param.getName()+":"+param.getValue());
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		File file = new File("a.txt");
		String path = file.getAbsolutePath();
		System.out.println(path);
	}
}
