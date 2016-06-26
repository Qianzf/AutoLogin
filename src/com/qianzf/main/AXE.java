package com.qianzf.main;

import java.util.Date;
import java.util.List;

import com.qianzf.util.ZiMuZuLogin;
import com.qianzf.util.ShuMaLogin;
import com.qianzf.util.DBHelper;
import com.qianzf.util.FengChiLogin;

public class AXE {
	public static void main(String args[]){
		System.out.println("--Log-- 登录程序开始。------------------------");
		DBHelper dh = new DBHelper("auto","root","***********");
		List<String[]> list = dh.execQuery("select uname, upassword,uwebsite from account");
		for(String[] temp : list){
				String username = temp[0];
				String upassword = temp[1];
				String website = temp[2];
				System.out.printf("--Log--"+new Date().toString()+"用户名："+username+" 密码哈希："+upassword.hashCode()+" 登录网站是：");
				switch(website){
					case "zimuzu":
						System.out.println("字幕组");
						new ZiMuZuLogin().autoLogin(username, upassword);
						break;
					case "shuma":
						System.out.println("数码之家");
						new ShuMaLogin(username,upassword);
						break;
					case "fengchi":
						System.out.println("风驰vpn");
						new FengChiLogin(username, upassword);
						break;
					default:
						System.out.println("未指定账号");
				}
		}
		System.out.println("--Log-- 登录程序完成。------------------------\n");
	}
}
