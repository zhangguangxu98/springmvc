package com.springmvc.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BasicDB {
    public static Connection cn=null;
	public static PreparedStatement ps=null;
	public static ResultSet rs=null;
	public static void openDB(){
		try{
			String driver="com.mysql.jdbc.Driver";
			String url="jdbc:mysql://localhost:3306/springmvc?characterEncoding=utf8";
			String user="root";
			String password="123456";
			Class.forName(driver);
			cn=DriverManager.getConnection(url,user,password);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void closeDB(){
		if(rs!=null){
			try{
				rs.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		if(ps!=null){
			try{
				ps.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		if(cn!=null){
			try{
				cn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
}

