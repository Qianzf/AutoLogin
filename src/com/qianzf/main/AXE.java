package com.qianzf.main;

import java.util.Date;
import java.util.List;

import com.qianzf.util.AutoLogin;
import com.qianzf.util.AutoSign;
import com.qianzf.util.DBHelper;
import com.qianzf.util.FengChiLogin;

public class AXE {
	public static void main(String args[]){
		
		DBHelper dh = new DBHelper("auto","root","f8456125107");
		List<String[]> list = dh.execQuery("select uname, upassword,uwebsite from account");
		for(String[] temp : list){
				String username = temp[0];
				String upassword = temp[1];
				String website = temp[2];
				System.out.println("--Log--"+new Date().toString()+"用户名："+username+" 密码："+"**************"+" 登录网站是："+website);
				switch(website){
					case "zimuzu":
						new AutoLogin().autoLogin(username, upassword);
						break;
					case "shuma":
						new AutoSign().autoSign();
						break;
					case "fengchi":
						new FengChiLogin(username, upassword);
						break;
					default:
						System.out.println("未指定账号");
				}
		}
	}
}
