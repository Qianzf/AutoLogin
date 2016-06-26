package com.qianzf.util;

/**
 * ��������ݿ�����Ӵ��룬��Ҫʵ�ֶ�mysql���ݿ�һЩ�в����ķ�װ�ͳ��ò���֧��
 * Author:Qianzf.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBHelper {
	/**
	 * ֧�ֵ����ݿ�Ϊmysql
	 */
	
	private static String driverName = "com.mysql.jdbc.Driver";
	private String dbUser = "root";
	private String dbPassword = "*************";
	private int dbPort = 3306;
	private String dbName = "test";
	private String dbUrl = "";
	private String dbIP = "";
	
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	static {
		//�������ݿ�����
			try {
				Class.forName(driverName);
			} catch (ClassNotFoundException e) {
				System.out.println("�������ݿ������쳣��");
			}
	}
	/******************************���캯��*****************************/
	public DBHelper(String  dbIP,int dbPort,String dbName,String dbUser,String dbPassword){
		this.dbName = dbName;
		this.dbPort = dbPort;
		this.dbIP  = dbIP;
		this.dbUser = dbUser;
		this.dbPassword = dbPassword;
		initDBConnection();
	}
	//loaclhost connection database
	public DBHelper(String dbName,String dbUser,String dbPassword){
		this("localhost",3306,dbName,dbUser,dbPassword);
	}
	
	/*********************************************************************/
	/**
	 * ��ʼ�����ݿ�����
	 */
	private void initDBConnection() {
		//��������
		dbUrl = "jdbc:mysql://"+dbIP+":"+dbPort+"/"+dbName+"?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
		try {
			conn = DriverManager.getConnection(dbUrl,dbUser,dbPassword);
		} catch (SQLException e) {
			System.out.println("�����ݿ����ӳ���");
		}
	}
	
	/**
	 * ���ݿ���²���
	 * @param sql ����sql���
	 * @return ���²���Ӱ�������
	 */
	public int execUpdate(String sql){
		try {
			ps = conn.prepareStatement(sql);
			return ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("���ݿ���²���ʧ�ܣ�");
			return -1;
		}
	}
	/**
	 * �������²���
	 * @param sql Ҫִ�е�����������伯��
	 * @return ���²���Ӱ�������
	 */
	public int execUpdate(String[] sql){
		if(sql==null||sql.length<1){
			System.out.println("����ʧ�ܣ�û���ҵ����ݿ�ִ����䡣");
			return -2;
		}
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql[0]);
			ps.executeUpdate();
			int temp = 0;
			for(int i=1;i<sql.length;++i){
				temp+=ps.executeUpdate(sql[i]);
			}
			conn.commit();
			return temp;
		} catch (SQLException e) {
			try {
				System.out.println("���ݿ�����������ڻع�������");
				conn.rollback();
			} catch (SQLException e1) {
				System.out.println("���ݿ�ع�����ʧ�ܡ�");
			}
			return -1;
		}
	}
	/**
	 * �Գ��õĵ�¼һ��Ĳ���������ϢУ���õĺ���
	 * @param sql Ҫִ�е����ݿ���䣬���б���Ҫ���ʺ�
	 * @param data �ʺŴ�Ҫ��������ַ����������������sql���ʺ�����һ�¡�
	 * @return ���ز�ѯ���ľ�����Ŀ�����������-1
	 */
	public int verInfo(String sql,String[] data){
		try {
			ps = conn.prepareStatement(sql);
			for(int i=0;i<data.length;++i){
				ps.setString(i+1, data[i]);
			}
			rs = ps.executeQuery();
			int temp = 0;
			while(rs.next()){
				temp++;
			}
			return temp;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	/**
	 * ִ��sql��ѯ��䣬��ִ�н���ü����෵�أ�������null
	 * @param sql sql��ѯ���
	 * @return ����һ��list���ϣ������ڲ����ַ������飨����һ�����ݣ����������ϱ������в�ѯ�������
	 */
	public List<String[]> execQuery(String sql){
		
		List<String[]> list = new ArrayList<String[]>();
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData rsMeta = rs.getMetaData();
			int num = rsMeta.getColumnCount();
			while(rs.next()){
				String temp[] = new String[num];
				for(int i=0;i<num;++i){
					temp[i] = rs.getString(i+1);
				}
				list.add(temp);
			}
			return list;
		} catch (SQLException e) {
			return null;
		}
	}
	
//�ر������ù�����Դ
	public void close(){
		
			try {
				if(rs!=null&&!rs.isClosed()){
					rs.close();
				}
				if(ps!=null&&!ps.isClosed()){
					ps.close();
				}
				if(conn!=null&&!conn.isClosed()){
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
