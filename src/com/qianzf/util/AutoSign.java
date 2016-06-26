package com.qianzf.util;

import java.util.Date;

public class AutoSign {
	
	private int status = 1;
	
	private String pwpwd = "F8456125107";
	//用户名
	private String pwuser = "ansuanboy";
	private String  verifyhash = "a77d00a0";
	public void sign(){
		//设置一下配置文件路径
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
		
		//这个是开关，设置为0时，停止签到！
		if(status!=null&&status.equals("0")){
			return;
		}

		if(pwuser==null||pwpwd==null||
				hideid==null||lgt==null||verifyhash==null){
					
					po2.createProperty("hideid", "0");
					po2.createProperty("lgt", "0");
					po2.createProperty("pwpwd","此处键入密码");
					po2.createProperty("verifyhash", "请填入校验HASH");
					po2.createProperty("pwuser", "此处键入用户名");
					po2.createProperty("status", "1");
					
					System.out.print("配置文件不正确或者不存在，已经自动重新在当前目录下创建！请填写用户名和密码等相关信息\r\n"
							+ "配置文件名称："+path+"\r\n\r\n");		
				}else{
		*************************************************************/
				//创建一个ur资源获取类
				UrlResource ur = new UrlResource();
				//FileOperate fo = new FileOperate();
				//0-用户名；2-邮箱
				String lgt = "0";
				
				//密码
				
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
					//发送post
					ur.getUrlSource("http://bbs.mydigit.cn/login.php","POST",params);
					//跳转到个人页面
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
		
		//开始字幕组登陆程序
		sign();
		//出错后自动重复三次
		while(status!=1){	
			try {
				Thread.sleep(2*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("--Log--"+new Date().toString()+"--：数码之家登陆出错！正在重试第"+(times+1)+"次。");
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

