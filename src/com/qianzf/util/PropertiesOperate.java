package com.qianzf.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class PropertiesOperate {
	
	Properties pos = null;
	File file = null;
	String path = "D:/test.properties";
	
	public PropertiesOperate(){
		pos = new Properties();
		file = new File(path);
	}
	
	
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
		file = new File(path);
	}

	//初始化-检查文件是否存在，没有则创建
	public void initOperate(){

		if(!file.exists() || !file.isFile()){		
			try {
				file.createNewFile();
				createProperty("文件创建时间",new Date().toString());
			} catch (IOException e) {
				e.printStackTrace();
				System.out.print("文件创建失败,请检查权限！\r\n");
			}
		}
	}
	
	//创建一个新的属性
	public void createProperty(String name,String value){
		initOperate();
		pos.setProperty(name, value);
		try(
				FileOutputStream	os = new FileOutputStream(file);
		){
			//保存文件-XML格式UTF-8
			pos.storeToXML(os, "程序作者是：Qianzf", "utf8");
		} catch (Exception e) {
			e.printStackTrace();
			if(file.exists()){
				//删除文件重新创建
				file.delete();
				initOperate();
			}
		}
	}
	
	//获取文件里面的属性
	public String acquireProperty(String name){
		initOperate();
		try(
				FileInputStream fis = new FileInputStream(file);
			){	
			//保存和读取都要是XML文件读写
			pos.loadFromXML(fis);
			String result = pos.getProperty(name);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			if(file.exists()){
				//删除文件重新创建
				file.delete();
				initOperate();
			}
		}
		return null;
	}
	
}
