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

	//��ʼ��-����ļ��Ƿ���ڣ�û���򴴽�
	public void initOperate(){

		if(!file.exists() || !file.isFile()){		
			try {
				file.createNewFile();
				createProperty("�ļ�����ʱ��",new Date().toString());
			} catch (IOException e) {
				e.printStackTrace();
				System.out.print("�ļ�����ʧ��,����Ȩ�ޣ�\r\n");
			}
		}
	}
	
	//����һ���µ�����
	public void createProperty(String name,String value){
		initOperate();
		pos.setProperty(name, value);
		try(
				FileOutputStream	os = new FileOutputStream(file);
		){
			//�����ļ�-XML��ʽUTF-8
			pos.storeToXML(os, "���������ǣ�Qianzf", "utf8");
		} catch (Exception e) {
			e.printStackTrace();
			if(file.exists()){
				//ɾ���ļ����´���
				file.delete();
				initOperate();
			}
		}
	}
	
	//��ȡ�ļ����������
	public String acquireProperty(String name){
		initOperate();
		try(
				FileInputStream fis = new FileInputStream(file);
			){	
			//����Ͷ�ȡ��Ҫ��XML�ļ���д
			pos.loadFromXML(fis);
			String result = pos.getProperty(name);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			if(file.exists()){
				//ɾ���ļ����´���
				file.delete();
				initOperate();
			}
		}
		return null;
	}
	
}
