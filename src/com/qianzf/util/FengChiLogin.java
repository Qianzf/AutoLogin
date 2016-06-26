package com.qianzf.util;

public class FengChiLogin {
	public FengChiLogin(String userName,String userPassword){
		UrlResource ur = new UrlResource();
		String params = "user.nick="+userName+"&user.password="+userPassword;
		try {
			String str = ur.getUrlSource("http://fengchinet2.com/login!login.htm", "POST", params);
			str = str.substring(str.indexOf("380px\">")+7, str.indexOf("¡£</div>"));
			System.out.println(str);
			String cookies = ur.getCookie();
			ur.setCookie(cookies);
			str = ur.getUrlSource("http://fengchinet2.com/my.htm","GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
