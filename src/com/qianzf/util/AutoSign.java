package com.qianzf.util;

import java.util.Date;

public class AutoSign {
	
	private int status = 1;
	
	private String pwpwd = "F8456125107";
	//�û���
	private String pwuser = "ansuanboy";
	private String  verifyhash = "a77d00a0";
	public void sign(){
		//����һ�������ļ�·��
		/*
		String path = "shuma.properties";
		PropertiesOperate po2 = new PropertiesOperate();
		po2.setPath(path);
		String pwuser = po2.acquireProperty("pwuser");
		String pwpwd = po2.acquireProperty("pwpwd");
		String hideid = po2.acquireProperty("hideid");
		String lgt = po2.acquireProperty("lgt");
		String verifyhash = po2.acquireProperty("verifyhash");
		String status  = po2.acquireProperty("status");
		
		//����ǿ��أ�����Ϊ0ʱ��ֹͣǩ����
		if(status!=null&&status.equals("0")){
			return;
		}

		if(pwuser==null||pwpwd==null||
				hideid==null||lgt==null||verifyhash==null){
					
					po2.createProperty("hideid", "0");
					po2.createProperty("lgt", "0");
					po2.createProperty("pwpwd","�˴���������");
					po2.createProperty("verifyhash", "������У��HASH");
					po2.createProperty("pwuser", "�˴������û���");
					po2.createProperty("status", "1");
					
					System.out.print("�����ļ�����ȷ���߲����ڣ��Ѿ��Զ������ڵ�ǰĿ¼�´���������д�û���������������Ϣ\r\n"
							+ "�����ļ����ƣ�"+path+"\r\n\r\n");		
				}else{
		*************************************************************/
				//����һ��ur��Դ��ȡ��
				UrlResource ur = new UrlResource();
				//FileOperate fo = new FileOperate();
				//0-�û�����2-����
				String lgt = "0";
				
				//����
				
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
					//����post
					ur.getUrlSource("http://bbs.mydigit.cn/login.php","POST",params);
					//��ת������ҳ��
					String temp2 = ur.getUrlSource("http://bbs.mydigit.cn/jobcenter.php?action=punch&step=2&verify="+verifyhash,"GET");				
					//fo.save("shumazhijia.html",temp2 );
					temp2 = temp2.substring(temp2.indexOf("message")+10,temp2.indexOf("\',\"flag"));
					System.out.println(temp2);
					status = 1;
				}catch(Exception e){
					status = -1;
					return;
				}
			}
	//}
	public void autoSign(){
		int times = 0;
		
		//��ʼ��Ļ���½����
		sign();
		//������Զ��ظ�����
		while(status!=1){	
			try {
				Thread.sleep(2*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("--Log--"+new Date().toString()+"--������֮�ҵ�½�����������Ե�"+(times+1)+"�Ρ�");
			sign();
			times++;		
			if(times == 3){
				break;
			}	
		}
		
	}
	public void autoSign(String username,String password,String verifyHash){
		this.pwuser = username;
		this.pwpwd = password;
		this.verifyhash = verifyHash;
		autoSign();
	}
}

