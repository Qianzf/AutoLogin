package com.qianzf.util;

/**
 * 这个是数据库的连接代码，主要实现对mysql数据库一些列操作的封装和常用操作支持
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
	 * 支持的数据库为mysql
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
		//加载数据库驱动
			try {
				Class.forName(driverName);
			} catch (ClassNotFoundException e) {
				System.out.println("加载数据库驱动异常！");
			}
	}
	/******************************构造函数*****************************/
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
	 * 初始化数据库连接
	 */
	private void initDBConnection() {
		//建立连接
		dbUrl = "jdbc:mysql://"+dbIP+":"+dbPort+"/"+dbName+"?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
		try {
			conn = DriverManager.getConnection(dbUrl,dbUser,dbPassword);
		} catch (SQLException e) {
			System.out.println("与数据库连接出错！");
		}
	}
	
	/**
	 * 数据库更新操作
	 * @param sql 更新sql语句
	 * @return 更新操作影响的行数
	 */
	public int execUpdate(String sql){
		try {
			ps = conn.prepareStatement(sql);
			return ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("数据库更新操作失败！");
			return -1;
		}
	}
	/**
	 * 批量更新操作
	 * @param sql 要执行的批量操作语句集合
	 * @return 更新操作影响的行数
	 */
	public int execUpdate(String[] sql){
		if(sql==null||sql.length<1){
			System.out.println("操作失败！没有找到数据库执行语句。");
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
				System.out.println("数据库操作出错，正在回滚操作。");
				conn.rollback();
			} catch (SQLException e1) {
				System.out.println("数据库回滚操作失败。");
			}
			return -1;
		}
	}
	/**
	 * 对常用的登录一类的操作进行信息校验用的函数
	 * @param sql 要执行的数据库语句，其中必须要有问号
	 * @param data 问号处要被代替的字符串，其数量必须和sql中问号数量一致。
	 * @return 返回查询到的具体数目，如果出错返回-1
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
	 * 执行sql查询语句，将执行结果用集合类返回，出错返回null
	 * @param sql sql查询语句
	 * @return 返回一个list集合，集合内部是字符串数组（保存一行数据），整个集合保存所有查询结果数据
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
	
//关闭所有用过的资源
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
