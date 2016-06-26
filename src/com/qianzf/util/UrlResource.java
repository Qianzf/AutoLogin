package com.qianzf.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UrlResource {
	
	private InputStream in = null;
	private PrintWriter out = null;
	private String method = "GET";
	private String params = "";
	private String cookie = "";
	private String referer = "";
	//设置默认编码格式为utf8
    private String encode = "utf8";
    
	private HttpURLConnection conn = null;
	
	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public String getUrlSource(String url,String method)throws Exception{
		this.method = method;
		return getUrlSource(url);
	}
	
	public String getUrlSource(String url,String method,String params)throws Exception{
		this.method = method;
		this.params = params;
		return getUrlSource(url);
	}
	
	public String getUrlSource(String url) throws Exception{

		try {
			//设置连接相关
			URI uri = new URI(url);
			URL website = uri.toURL();
			HttpURLConnection.setFollowRedirects(true);
			
			conn =(HttpURLConnection) website.openConnection();
			conn.setRequestMethod(method);		
			conn.setConnectTimeout(5*1000);
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0");
			conn.setRequestProperty("Referer", referer);  
			conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");  
			conn.setRequestProperty("Connection", "keep-alive");  	
			conn.setRequestProperty("Cache-Control", "max-age=0");
			
			if(!cookie.equals("")){	
				conn.setRequestProperty("Cookie",cookie); 		
				//System.out.println("发送cookie:"+cookie);	
			}
			if(method.equals("POST")){
				// 发送POST请求必须设置如下两行
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            // 获取URLConnection对象对应的输出流
	            out = new PrintWriter(conn.getOutputStream());
	            // 发送请求参数
	            out.print(params);
	            // flush输出流的缓冲
	            out.flush();       
			}
			
			 /**处理网页cookie请求**/
			//获取回应头
			Map<String, List<String>> maps = conn.getHeaderFields(); 
			
		    List<String> coolist = maps.get("Set-Cookie");  
		    
		    if(coolist !=null){
		    	Iterator<String> it = coolist.iterator();
		    	StringBuffer sbu = new StringBuffer();
			    //sbu.append("eos_style_cookie=default; ");
			    while(it.hasNext()){  
			        sbu.append(it.next()+" ;");  
			    }
				//System.out.println("保存cookie:"+sbu);
				
				this.cookie = sbu.toString();
		    }
		   
			InputStream in = conn.getInputStream();
			byte buffer[] = new byte[1024];
			StringBuffer source = new StringBuffer();
			
			while((in.read(buffer, 0, 1024))!=-1){
				String temp = new String(buffer,encode);
				source.append(temp);
			}
			
			return source.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}finally{
				try {
					if(in!=null)
						in.close();
					if(conn!=null)
						conn.disconnect();
				} catch (IOException e) {
					e.printStackTrace();

				}
		}
	}

}



