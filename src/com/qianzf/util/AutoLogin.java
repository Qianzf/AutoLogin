package com.qianzf.util;

import java.util.Date;

/**
 * ��Ļ���Զ���½����
 * @author Dobby
 */
public class AutoLogin {
	
	private String account = "wodeshijie";
	private String password = "f8456125107";
	private String remember = "0";
	private String url_back = "http://www.zimuzu.tv/";
	private int status = 1;
	
	public void loginNow(){
		//����һ�������ļ�·��
		//String path = "autologin.properties";
		//����һ��ur��Դ��ȡ��
		
		UrlResource ur = new UrlResource();
		/*
		PropertiesOperate po = new PropertiesOperate();
		po.setPath(path);
		String account = po.acquireProperty("account");
		String password = po.acquireProperty("password");
		String remember = po.acquireProperty("remember");
		String url_back = po.acquireProperty("url_back");
		String status  = po.acquireProperty("status");
		
		//����ǿ��أ�����Ϊ0ʱ��ֹͣǩ����
		if(status!=null&&status.equals("0")){
			return;
		}
		
		if(account==null||password==null||
		   remember==null||url_back==null||status==null){
			
			po.createProperty("url_back", "http://www.zimuzu.tv/");
			po.createProperty("password","�˴���������");
			po.createProperty("remember", "1");
			po.createProperty("account", "�˴������û���");
			po.createProperty("status", "1");
			System.out.print("�����ļ�����ȷ���߲����ڣ��Ѿ��Զ������ڵ�ǰĿ¼�´���������д�û���������\r\n"
					+ "�����ļ����ƣ�"+path+"\r\n\r\n");
		
		}else{
			*/
			//����������������Ӧ���� name1=value1&name2=value2 ����ʽ��
			String params = "account="+account+"&password="+password+"&remember="+remember+
					"&url_back="+url_back;
			
			//α��һ����ת
			ur.setReferer("http://www.zimuzu.tv/user/login");
			//����post��½����
			
			try{
			
					String temp = ur.getUrlSource("http://www.zimuzu.tv/User/Login/ajaxLogin","POST",params);
					
					//String cookie = ur.getCookie();
					//����û���������
					if(temp.charAt(10)=='1'){
						System.out.println(account+"��½�ɹ�!");
					}else{
						System.out.println(account+"��½ʧ�ܣ������˺������Ƿ���ȷ!");
						status = 0;
						return;
					}
						
					//ǩ��
					temp = ur.getUrlSource("http://www.zimuzu.tv/user/login/getCurUserTopInfo","GET");
					 
					//���ǩ��״̬
					if(temp.charAt(10)=='1'){
						System.out.println(account+"ǩ���ɹ�!");
					}else{
						System.out.println(account+"ǩ��ʧ��!");
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
					System.out.println(account+"ǩ��ʧ�ܣ�");
					return;
			}
		}
	
	public void autoLogin(){
		int times = 0;
		
		//��ʼ��Ļ���½����
		loginNow();
		//������Զ��ظ�����
		while(status!=1){	
			try {
				Thread.sleep(2*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("--Log--"+new Date().toString()+"--����½�����������Ե�"+(times+1)+"�Ρ�");
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
