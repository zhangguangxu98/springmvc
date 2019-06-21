package com.springmvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.springmvc.db.BasicDB;
import com.springmvc.pojo.UserPojo;

@Repository("adminShowAllUserDao")
public class AdminShowAllUserDao {
	
	//add
	public void addUser(Object object[]){
		String sql="insert into user(name,password,email,mark,phone) values (?, ?, ?, ?, ?)";
		BasicDB.openDB();
		Connection cn=BasicDB.cn;
		PreparedStatement ps=BasicDB.ps;
		try{
			ps=cn.prepareStatement(sql);
			for(int i=0;i<object.length;i++){
				ps.setObject(i+1, object[i]);
			}
			ps.execute();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			BasicDB.closeDB();
		}
	}
	
	//delete
	public void deleteUser(String paras[]){
		String sql="delete from user where name=?";
		BasicDB.openDB();
		Connection cn=BasicDB.cn;
		PreparedStatement ps=BasicDB.ps;
		try{
			ps=cn.prepareStatement(sql);
			for(int i=0;i<paras.length;i++){
				ps.setString(i+1, paras[i]);
			}
			ps.execute();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			BasicDB.closeDB();
		}
	}
	
	//show
	public ArrayList<UserPojo> showAllUser(){
		String sql = "select * from user";
		BasicDB.openDB();
		Connection cn=BasicDB.cn;
		PreparedStatement ps=BasicDB.ps;
		ResultSet rs=BasicDB.rs;
		ArrayList<UserPojo> al=new ArrayList<UserPojo>();
		try{
			ps=cn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				UserPojo userpojo=new UserPojo();
				userpojo.setName(rs.getString(1));
				userpojo.setPassword(rs.getString(2));
				userpojo.setEmail(rs.getString(3));
				userpojo.setMark(rs.getString(4));
				userpojo.setPhone(rs.getString(5));
				al.add(userpojo);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			BasicDB.closeDB();
		}
		return al;
	}
	public ArrayList<UserPojo> searchUser(String paras[]){
		String sql="select * from user where name=?";
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
				UserPojo userpojo=new UserPojo();
				userpojo.setName(rs.getString(1));
				userpojo.setPassword(rs.getString(2));
				userpojo.setEmail(rs.getString(3));
				userpojo.setMark(rs.getString(4));
				userpojo.setPhone(rs.getString(5));
				al.add(userpojo);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			BasicDB.closeDB();
		}
		return al;
	}
	
	//update
	public ArrayList<UserPojo> beforeUpdateUser(String paras[]){
		String sql="select * from user where name=?";
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
				UserPojo userpojo=new UserPojo();
				userpojo.setName(rs.getString(1));
				userpojo.setPassword(rs.getString(2));
				userpojo.setEmail(rs.getString(3));
				userpojo.setMark(rs.getString(4));
				userpojo.setPhone(rs.getString(5));
				al.add(userpojo);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			BasicDB.closeDB();
		}
		return al;
	}
	public void updateUser(String paras[]){
		String sql="update user set password=?, email=?,mark=?,phone=? where name=?";
		BasicDB.openDB();
		Connection cn=BasicDB.cn;
		PreparedStatement ps=BasicDB.ps;
		try{
			ps=cn.prepareStatement(sql);
			for(int i=0;i<paras.length;i++){
				ps.setObject(i+1, paras[i]);
			}
			ps.execute();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			BasicDB.closeDB();
		}
	}
}
