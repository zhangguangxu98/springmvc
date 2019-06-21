package com.springmvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.springmvc.db.BasicDB;
import com.springmvc.pojo.MyLogPojo;
import com.springmvc.pojo.MyPlanPojo;
@Repository("adminShowAllMyPlanDao")
public class AdminShowAllMyPlanDao {
	
	//添加或者导出计划
	public void addOrUploadMyPlan(Object object[],String sql){
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
	
	//删除计划
	public void deleteOrUpdateMyPlan(String paras[],String sql){
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
	
	//显示或者导出计划
	public ArrayList<MyPlanPojo> showAllOrExportMyPlan(String paras[],String sql,String searchsql){
		BasicDB.openDB();
		Connection cn=BasicDB.cn;
		PreparedStatement ps=BasicDB.ps;
		ResultSet rs=BasicDB.rs;
		ArrayList<MyPlanPojo> al=new ArrayList<MyPlanPojo>();
		try{
			if(""!=paras[0]&&null!=paras[0]&&null!=paras[1]&&""!=paras[1]){
				ps=cn.prepareStatement(searchsql);
				for(int i=0;i<paras.length;i++){
					ps.setString(i+1, paras[i]);
				}
			}else{
				ps=cn.prepareStatement(sql);
			}
			rs=ps.executeQuery();
			while(rs.next()){
				MyPlanPojo myPlanPojo=new MyPlanPojo();
				myPlanPojo.setId(rs.getInt("id"));
				myPlanPojo.setStartdate(rs.getString("startdate"));
				myPlanPojo.setEnddate(rs.getString("enddate"));
				myPlanPojo.setPlan(rs.getString("plan"));
				myPlanPojo.setContent(rs.getString("content"));
				myPlanPojo.setAssessment(rs.getString("assessment"));
				al.add(myPlanPojo);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			BasicDB.closeDB();
		}
		return al;
	}
	
	//更新计划
	public ArrayList<MyPlanPojo> beforeUpdateMyPlan(String paras[],String sql){
		BasicDB.openDB();
		Connection cn=BasicDB.cn;
		PreparedStatement ps=BasicDB.ps;
		ResultSet rs=BasicDB.rs;
		ArrayList<MyPlanPojo> al=new ArrayList<MyPlanPojo>();
		try{
			ps=cn.prepareStatement(sql);
			for(int i=0;i<paras.length;i++){
				ps.setString(i+1, paras[i]);
			}
			rs=ps.executeQuery();
			while(rs.next()){
				MyPlanPojo myPlanPojo=new MyPlanPojo();
				myPlanPojo.setId(rs.getInt("id"));
				myPlanPojo.setStartdate(rs.getString("startdate"));
				myPlanPojo.setEnddate(rs.getString("enddate"));
				myPlanPojo.setPlan(rs.getString("plan"));
				myPlanPojo.setContent(rs.getString("content"));
				myPlanPojo.setAssessment(rs.getString("assessment"));
				al.add(myPlanPojo);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			BasicDB.closeDB();
		}
		return al;
	}
	
	//生成图表
	public Map<String,Integer> showMyPlanChart(String paras[],String sql,String searchsql){
		BasicDB.openDB();
		Connection cn=BasicDB.cn;
		PreparedStatement ps=BasicDB.ps;
		ResultSet rs=BasicDB.rs;
		ArrayList<Integer> score=new ArrayList<Integer>();
		ArrayList<String> plandate=new ArrayList<String>();
		Map<String,Integer> planmap = new LinkedHashMap<String,Integer>();
		try{
			if(""!=paras[0]&&null!=paras[0]&&null!=paras[1]&&""!=paras[1]){
				ps=cn.prepareStatement(searchsql);
				for(int i=0;i<paras.length;i++){
					ps.setString(i+1, paras[i]);
				}
			}else{
				ps=cn.prepareStatement(sql);
			}
			rs=ps.executeQuery();
			while(rs.next()){
				switch(rs.getString("assessment")){
					case "优":
						plandate.add(rs.getString("startdate"));
						score.add(4);
						planmap.put(rs.getString("startdate"), 4);
						break;
					case "良":
						plandate.add(rs.getString("startdate"));
						score.add(3);
						planmap.put(rs.getString("startdate"), 3);
						break;
					case "中":
						plandate.add(rs.getString("startdate"));
						score.add(2);
						planmap.put(rs.getString("startdate"), 2);
						break;
					case "差":
						plandate.add(rs.getString("startdate"));
						score.add(1);
						planmap.put(rs.getString("startdate"), 1);
						break;
				}
						
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			BasicDB.closeDB();
		}
		return planmap;
	}
}
