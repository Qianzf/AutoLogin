package com.qianzf.util;

import java.util.Date;

/**
 * 字幕组自动登陆程序
 * @author Dobby
 */
public class AutoLogin {
	
	private String account = "wodeshijie";
	private String password = "f8456125107";
	private String remember = "0";
	private String url_back = "http://www.zimuzu.tv/";
	private int status = 1;
	
	public void loginNow(){
		//设置一下配置文件路径
		//String path = "autologin.properties";
		//创建一个ur资源获取类
		
		UrlResource ur = new UrlResource();
		/*
		PropertiesOperate po = new PropertiesOperate();
		po.setPath(path);
		String account = po.acquireProperty("account");
		String password = po.acquireProperty("password");
		String remember = po.acquireProperty("remember");
		String url_back = po.acquireProperty("url_back");
		String status  = po.acquireProperty("status");
		
		//这个是开关，设置为0时，停止签到！
		if(status!=null&&status.equals("0")){
			return;
		}
		
		if(account==null||password==null||
		   remember==null||url_back==null||status==null){
			
			po.createProperty("url_back", "http://www.zimuzu.tv/");
			po.createProperty("password","此处键入密码");
			po.createProperty("remember", "1");
			po.createProperty("account", "此处键入用户名");
			po.createProperty("status", "1");
			System.out.print("配置文件不正确或者不存在，已经自动重新在当前目录下创建！请填写用户名和密码\r\n"
					+ "配置文件名称："+path+"\r\n\r\n");
		
		}else{
			*/
			//请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
			String params = "account="+account+"&password="+password+"&remember="+remember+
					"&url_back="+url_back;
			
			//伪造一下跳转
			ur.setReferer("http://www.zimuzu.tv/user/login");
			//发送post登陆请求
			
			try{
			
					String temp = ur.getUrlSource("http://www.zimuzu.tv/User/Login/ajaxLogin","POST",params);
					
					//String cookie = ur.getCookie();
					//检查用户名和密码
					if(temp.charAt(10)=='1'){
						System.out.println(account+"登陆成功!");
					}else{
						System.out.println(account+"登陆失败，请检查账号密码是否正确!");
						status = 0;
						return;
					}
						
					//签到
					temp = ur.getUrlSource("http://www.zimuzu.tv/user/login/getCurUserTopInfo","GET");
					 
					//检查签到状态
					if(temp.charAt(10)=='1'){
						System.out.println(account+"签到成功!");
					}else{
						System.out.println(account+"签到失败!");
						status = 0;
						return;
					}
					
					/*
					ur.setCookie(cookie);
					String temp2 = ur.getUrlSource("http://www.zimuzu.tv/user/sign","GET");
					FileOperate fo = new FileOperate();
					fo.save("zimuzu.html",temp2 );	
					 */
					
					status = 1;
			}catch(Exception e){
					status = -1;
					System.out.println(account+"签到失败！");
					return;
			}
		}
	
	public void autoLogin(){
		int times = 0;
		
		//开始字幕组登陆程序
		loginNow();
		//出错后自动重复三次
		while(status!=1){	
			try {
				Thread.sleep(2*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("--Log--"+new Date().toString()+"--：登陆出错！正在重试第"+(times+1)+"次。");
			loginNow();
			times++;		
			if(times == 3){
				times = 0;
				break;
			}	
		}
	}
	
	public void autoLogin(String account,String password){
		this.account = account;
		this.password = password;
		autoLogin();
	}
}
