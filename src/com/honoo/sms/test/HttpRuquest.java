package com.honoo.sms.test;
import java.io.BufferedReader;
import java.io.Console;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

//华兴软通，sdk接口调用demo
public class HttpRuquest {

	/**
	 * 
	 * @param strUrl
	 *            请求地址
	 * @param param
	 *            参数字符串
	 * @return 返回字符串
	 * @throws Exception
	 */
	public static String requestGet(String strUrl, String param) {
		System.out.println("GET请求:" + strUrl + "?" + param);
		String returnStr = null; // 返回结果定义
		URL url = null;
		HttpsURLConnection httpsURLConnection = null;
		try {
			url = new URL(strUrl + "?" + param);
			httpsURLConnection = (HttpsURLConnection) url.openConnection();
			//httpsURLConnection.setSSLSocketFactory(HttpRuquest.initSSLSocketFactoryByJKS()); // 设置套接工厂
			httpsURLConnection.setSSLSocketFactory(HttpRuquest.initSSLSocketFactoryByPEM()); // 设置套接工厂
			httpsURLConnection.setRequestProperty("Accept-Charset", "utf-8");
			httpsURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			httpsURLConnection.setDoOutput(true);
			httpsURLConnection.setDoInput(true);
			httpsURLConnection.setRequestMethod("GET"); // get方式
			httpsURLConnection.setUseCaches(false); // 不用缓存
			httpsURLConnection.connect();
			System.out.println("ResponseCode:" + httpsURLConnection.getResponseCode());
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(httpsURLConnection.getInputStream(), "utf-8"));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}

			reader.close();
			returnStr = buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			
			return null;
		} finally {
			if (httpsURLConnection != null) {
				httpsURLConnection.disconnect();
			}
		}
		return returnStr;
	}

	public static String requestPost(String strUrl, String param) {
		System.out.println("POST请求:" + strUrl + "?" + param);
		String returnStr = null; // 返回结果定义
		URL url = null;
		HttpsURLConnection httpsURLConnection = null;
		
		try {
			url = new URL(strUrl);
			httpsURLConnection = (HttpsURLConnection) url.openConnection();
			//httpsURLConnection.setSSLSocketFactory(HttpRuquest.initSSLSocketFactoryByJKS()); // 设置套接工厂
			httpsURLConnection.setSSLSocketFactory(HttpRuquest.initSSLSocketFactoryByPEM()); // 设置套接工厂
			httpsURLConnection.setRequestProperty("Accept-Charset", "utf-8");
			httpsURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			httpsURLConnection.setDoOutput(true);
			httpsURLConnection.setDoInput(true);
			httpsURLConnection.setRequestMethod("POST"); // post方式
			httpsURLConnection.connect();

			//POST方法时使用
			byte[] byteParam = param.getBytes("UTF-8");
			DataOutputStream out = new DataOutputStream(httpsURLConnection.getOutputStream());
			out.write(byteParam);
			out.flush();
			out.close();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(httpsURLConnection.getInputStream(), "utf-8"));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}

			reader.close();
			returnStr = buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (httpsURLConnection != null) {
				httpsURLConnection.disconnect();
			}
		}
		return returnStr;
	}

		
	//使用JSK格式文件生成信任库(不很推荐这个方法，因为jks需要生成)
	public static SSLSocketFactory initSSLSocketFactoryByJKS() throws Exception {
		
		KeyStore keyStore = KeyStore.getInstance("jks"); 
		String fileTruseStore = "F:/cert/WoSign.jks";		//信任库文件，发布项目时打包到resource路径,可以用相对路径
		String pwTruseStore = "hxrt_sms_demo";		//默认密码，和库文件绑定的，不需要改，如果一定要改请和客服联系
		FileInputStream f_trustStore=new FileInputStream(fileTruseStore); 
		keyStore.load(f_trustStore, pwTruseStore.toCharArray()); 
		String alg = TrustManagerFactory.getDefaultAlgorithm(); 
		TrustManagerFactory tmFact = TrustManagerFactory.getInstance(alg); 
		tmFact.init(keyStore);
		TrustManager[] tms = tmFact.getTrustManagers(); 

		//SSLContext sslcontext = SSLContext.getInstance("TLS");		//SSLContext不能限制协议套件和加密算法
		//sslcontext.init(null, tms, new java.security.SecureRandom());
		//SSLSocketFactory returnSSLSocketFactory = sslcontext.getSocketFactory();
		
		SSLSocketFactoryEx sslSocketFactoryEx = new SSLSocketFactoryEx(null, tms, new java.security.SecureRandom());
		return sslSocketFactoryEx;
	}
	
	//使用PEM根证书文件然后用别名的方式设置到KeyStore中去，推荐该方法(PEM根证书能从公开地址下载到)
	public static SSLSocketFactory initSSLSocketFactoryByPEM() throws Exception {
		KeyStore keyStore = KeyStore.getInstance("jks"); 
		keyStore.load(null, null);		//设置一个空密匙库
		String trustCertPath = "C:\\Users\\卢保\\Desktop\\JAVA\\cacert.pem";		//证书文件路径，发布项目时打包到resource路径
		FileInputStream trustCertStream = new FileInputStream(trustCertPath);
		CertificateFactory cerFactory = CertificateFactory.getInstance("X.509");			
		Collection<? extends Certificate> certs = cerFactory.generateCertificates(trustCertStream);		//读取多份证书(如果文件流中存在的话)
		for (Certificate certificate : certs) {
			keyStore.setCertificateEntry("" + ((X509Certificate)certificate).getSubjectDN(), certificate);		//遍历集合把证书添加到keystore里，每个证书都必须用不同的唯一别名，否则会被覆盖
		}
		TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509", "SunJSSE");
		tmf.init(keyStore);
		TrustManager tms[] = tmf.getTrustManagers();
		
		//SSLContext sslcontext = SSLContext.getInstance("TLS");		//SSLContext不能限制协议套件和加密算法
		//sslcontext.init(null, tms, new java.security.SecureRandom());
		//SSLSocketFactory returnSSLSocketFactory = sslcontext.getSocketFactory();
		
		SSLSocketFactoryEx sslSocketFactoryEx = new SSLSocketFactoryEx(null, tms, new java.security.SecureRandom());
		return sslSocketFactoryEx;
	}
	
	/**
	 * 获取余额的方法
	 * @param type
	 *            请求方式,1为get,2为post,推荐post
	 */
	public static void getBalance(int type) {
		String url = "https://www.stongnet.com/sdkhttp/getbalance.aspx";
		//String url = "https://kyfw.12306.cn/otn/login/init";
		String regCode = "101100-WEB-HUAX-111111"; // 华兴软通注册码，请在这里填写您从客服那得到的注册码
		String regPasswod = "11111111"; // 华兴软通注册码对应的密码，请在这里填写您从客服那得到的注册码
		String param = "reg=" + regCode + "&pwd=" + regPasswod;
		
		String returnStr = null;
		if (type == 1) {
			returnStr = requestGet(url, param);
		} else if (type == 2) {
			returnStr = requestPost(url, param);
		}
		System.out.println(returnStr);
	}
	
	/**
	 * 发送短信的方法
	 * @param type
	 *            请求方式,1为get,2为post,推荐post
	 */
	public static void sendSms(int type) {
		String url = "https://www.stongnet.com/sdkhttp/sendsms.aspx";
		String regCode = "101100-WEB-HUAX-111111"; // 华兴软通注册码，请在这里填写您从客服那得到的注册码
		String regPasswod = "11111111"; // 华兴软通注册码对应的密码，请在这里填写您从客服那得到的注册码
		String sourceAdd = null;		//子通道号（最长10位，可为空
		String phone = "18737306083";		//手机号码（最多1000个），多个用英文逗号(,)隔开，不可为空
		Random random = new Random();
		String content = "华兴软通$-_.+!*',^(αβ &@#%)验证码:" +  (1000 + random.nextInt(9000));		//短信内容(含有中文，特殊符号等非ASCII码的内容，用户必须保证其为UTF-8编码格式)
		try {
			content = URLEncoder.encode(content,"UTF-8");		//content中含有空格，换行，中文等非ascii字符时，需要进行url编码，否则无法正确传输到服务器
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return;
		}
		
		String param = "reg=" + regCode + "&pwd=" + regPasswod + "&sourceadd=" + sourceAdd + "&phone=" + phone + "&content=" + content;
		String returnStr = null;
		if (type == 1) {
			returnStr = requestGet(url, param);
		} else if (type == 2) {
			returnStr = requestPost(url, param);
		}
		System.out.println(returnStr);
	}
	
}
