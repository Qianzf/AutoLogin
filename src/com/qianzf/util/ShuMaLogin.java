package com.qianzf.util;

public class ShuMaLogin {
	
	//�û���
	private String pwuser = "";
	//����
	private String pwpwd = "";
	private String  verifyhash = "";
	
	public ShuMaLogin(String username,String password){
		this.pwuser = username;
		this.pwpwd = password;
		sign();
	}
	
	private void sign(){
		//����һ��ur��Դ��ȡ��
		UrlResource ur = new UrlResource();
		//0-�û�����2-����
		String lgt = "0";
		//�Ƿ������½
		String hideid = "0";
		String cktime = "31536000";
		String jumpurl = "http://bbs.mydigit.cn/";
		String step = "2";
		//����������������Ӧ���� name1=value1&name2=value2 ����ʽ��
		String params = "lgt="+lgt+"&pwpwd="+pwpwd+"&pwuser="+pwuser+
				"&hideid="+hideid+"&cktime="+cktime+"&jumpurl="+jumpurl+"&step="+step;
		//����һ�±����ʽ
		ur.setEncode("gbk");
		//����һ����ʼ��cookie
		ur.setCookie("");
		//α��һ����ת
		ur.setReferer("http://bbs.mydigit.cn/login.php");
		try{
			//����post��¼
			ur.getUrlSource("http://bbs.mydigit.cn/login.php","POST",params);
			//��cookie��������
			String cookie = ur.getCookie();
			//ȥ��վ��ҳ��ȡ�˻�У����
			String verhash = ur.getUrlSource("http://bbs.mydigit.cn/index.php");
			int index = verhash.indexOf("var verifyhash = ");
			int next = verhash.indexOf(";",index);
			verhash = verhash.substring(index+18,next-1);
			//System.out.println(verhash);
			this.verifyhash = verhash;
			//ע�⣡����������10�����ڲ��ܼ�����¼
			//�ѱ����cookie���ǻ�ȥ
			ur.setCookie(cookie);
			//��ת������ǩ��ҳ��
			String temp2 = ur.getUrlSource("http://bbs.mydigit.cn/jobcenter.php?action=punch&step=2&verify="+verifyhash,"GET");				
			temp2 = temp2.substring(temp2.indexOf("message")+10,temp2.indexOf("\',\"flag"));
			System.out.println(temp2);
		}catch(Exception e){
			System.out.println("ǩ��ʧ�ܣ������˺ź������Ƿ���ȷ���߷������Ƿ���ã�");
			return;
		}
	}

}

