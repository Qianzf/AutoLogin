package com.qianzf.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileOperate  {
	String path = null;
	File file = null;
	
	public boolean save(String path,String str){
		if(str!=null && path !=null){
			this.path = path;
			file = new File(path);
			if(file.isFile()){
				file.delete();
			}
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try(
					//自动关闭流
					FileOutputStream fos = new FileOutputStream(file);
					BufferedOutputStream bos = new BufferedOutputStream(fos);
				){
					byte[] buffer = str.getBytes();
					bos.write(buffer);
					return true;
				}catch(Exception e){
					e.printStackTrace();
				}
			
			return false;
		}else
			return false;
	}
	
}
