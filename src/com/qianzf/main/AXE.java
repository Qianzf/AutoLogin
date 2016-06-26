package com.qianzf.main;

import java.util.Date;
import java.util.List;

import com.qianzf.util.ZiMuZuLogin;
import com.qianzf.util.ShuMaLogin;
import com.qianzf.util.DBHelper;
import com.qianzf.util.FengChiLogin;

public class AXE {
	public static void main(String args[]){
		System.out.println("--Log-- ��¼����ʼ��------------------------");
		DBHelper dh = new DBHelper("auto","root","***********");
		List<String[]> list = dh.execQuery("select uname, upassword,uwebsite from account");
		for(String[] temp : list){
				String username = temp[0];
				String upassword = temp[1];
				String website = temp[2];
				System.out.printf("--Log--"+new Date().toString()+"�û�����"+username+" �����ϣ��"+upassword.hashCode()+" ��¼��վ�ǣ�");
				switch(website){
					case "zimuzu":
						System.out.println("��Ļ��");
						new ZiMuZuLogin().autoLogin(username, upassword);
						break;
					case "shuma":
						System.out.println("����֮��");
						new ShuMaLogin(username,upassword);
						break;
					case "fengchi":
						System.out.println("���vpn");
						new FengChiLogin(username, upassword);
						break;
					default:
						System.out.println("δָ���˺�");
				}
		}
		System.out.println("--Log-- ��¼������ɡ�------------------------\n");
	}
}
