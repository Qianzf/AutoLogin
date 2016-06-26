package com.qianzf.util;

public class ShuMaLogin {
	
	//用户名
	private String pwuser = "";
	//密码
	private String pwpwd = "";
	private String  verifyhash = "";
	
	public ShuMaLogin(String username,String password){
		this.pwuser = username;
		this.pwpwd = password;
		sign();
	}
	
	private void sign(){
		//创建一个ur资源获取类
		UrlResource ur = new UrlResource();
		//0-用户名；2-邮箱
		String lgt = "0";
		//是否隐身登陆
		String hideid = "0";
		String cktime = "31536000";
		String jumpurl = "http://bbs.mydigit.cn/";
		String step = "2";
		//请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
		String params = "lgt="+lgt+"&pwpwd="+pwpwd+"&pwuser="+pwuser+
				"&hideid="+hideid+"&cktime="+cktime+"&jumpurl="+jumpurl+"&step="+step;
		//设置一下编码格式
		ur.setEncode("gbk");
		//设置一个初始的cookie
		ur.setCookie("");
		//伪造一下跳转
		ur.setReferer("http://bbs.mydigit.cn/login.php");
		try{
			//发送post登录
			ur.getUrlSource("http://bbs.mydigit.cn/login.php","POST",params);
			//把cookie保存下来
			String cookie = ur.getCookie();
			//去网站主页获取账户校验码
			String verhash = ur.getUrlSource("http://bbs.mydigit.cn/index.php");
			int index = verhash.indexOf("var verifyhash = ");
			int next = verhash.indexOf(";",index);
			verhash = verhash.substring(index+18,next-1);
			//System.out.println(verhash);
			this.verifyhash = verhash;
			//注意！密码错误会在10分钟内不能继续登录
			//把保存的cookie覆盖回去
			ur.setCookie(cookie);
			//跳转到个人签到页面
			String temp2 = ur.getUrlSource("http://bbs.mydigit.cn/jobcenter.php?action=punch&step=2&verify="+verifyhash,"GET");				
			temp2 = temp2.substring(temp2.indexOf("message")+10,temp2.indexOf("\',\"flag"));
			System.out.println(temp2);
		}catch(Exception e){
			System.out.println("签到失败，请检查账号和密码是否正确或者服务器是否可用！");
			return;
		}
	}

}

