package com.springmvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.springmvc.db.BasicDB;
import com.springmvc.pojo.UserPojo;
@Repository("userLoginDao")
public class UserLoginDao{

	//login
	public ArrayList<UserPojo> logIn(String[] paras,String sql) {
		BasicDB.openDB();
		Connection cn=BasicDB.cn;
		PreparedStatement ps=BasicDB.ps;
		ResultSet rs=BasicDB.rs;
		ArrayList<UserPojo> al=new ArrayList<UserPojo>();
		try{
			ps=cn.prepareStatement(sql);
			for(int i=0;i<paras.length;i++){
				ps.setString(i+1, paras[i]);
			}
			rs=ps.executeQuery();
			while(rs.next()){
				UserPojo userbean=new UserPojo();
				userbean.setName(rs.getString(1));
				userbean.setPassword(rs.getString(2));
				userbean.setEmail(rs.getString(3));
				userbean.setMark(rs.getString(4));
				userbean.setPhone(rs.getString(5));
				al.add(userbean);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			BasicDB.closeDB();
		}
		return al;
	}
}
