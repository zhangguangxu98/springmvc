package com.springmvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.springmvc.db.BasicDB;
import com.springmvc.pojo.LotteryResultPojo;
import com.springmvc.pojo.MyLogPojo;
@Repository("adminShowAllMyLogDao")
public class AdminShowAllMyLogDao {
	
	//添加日志
	public void addMyLog(Object object[]){
		String sql="insert into mylog (id,date,home,clothes,meal,room,trip,lifeuse,play,insurance,idroutine,dateroutine,homeroutine,clothesroutine,mealroutine,roomroutine,triproutine,useroutine,playroutine,insuranceroutine,selfcontrol,diligence,goodorder,clean,frugality,honest,integrity,modest,friendly,tolerant,diary) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
	
	//删除日志
	public void deleteLotteryResult(String paras[]){
		String sql="delete from mylog where id=?";
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
	
	//显示日志
	public ArrayList<MyLogPojo> showAllMyLog(String paras[]){
		String sql = "select * from mylog order by id DESC";
		BasicDB.openDB();
		Connection cn=BasicDB.cn;
		PreparedStatement ps=BasicDB.ps;
		ResultSet rs=BasicDB.rs;
		ArrayList<MyLogPojo> al=new ArrayList<MyLogPojo>();
		try{
			ps=cn.prepareStatement(sql);
			if(""!=paras[0]&&null!=paras[0]&&null!=paras[1]&&""!=paras[1]){
				String searchsql="select * from mylog where date>=? and date<=? order by id DESC";
				ps=cn.prepareStatement(searchsql);
				for(int i=0;i<paras.length;i++){
					ps.setString(i+1, paras[i]);
				}
			}
			System.out.println(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				MyLogPojo myLogPojo=new MyLogPojo();
				myLogPojo.setId(rs.getInt("id"));
				myLogPojo.setDate(rs.getString("date"));
				myLogPojo.setHome(rs.getInt("home"));
				myLogPojo.setClothes(rs.getInt("clothes"));
				myLogPojo.setMeal(rs.getInt("meal"));
				myLogPojo.setRoom(rs.getInt("room"));
				myLogPojo.setTrip(rs.getInt("trip"));
				myLogPojo.setLifeuse(rs.getInt("lifeuse"));
				myLogPojo.setPlay(rs.getInt("play"));
				myLogPojo.setInsurance(rs.getInt("insurance"));
				myLogPojo.setIdroutine(rs.getString("idroutine"));
				myLogPojo.setDateroutine(rs.getString("dateroutine"));
				myLogPojo.setHomeroutine(rs.getString("homeroutine"));
				myLogPojo.setClothesroutine(rs.getString("clothesroutine"));
				myLogPojo.setMealroutine(rs.getString("mealroutine"));
				myLogPojo.setRoomroutine(rs.getString("roomroutine"));
				myLogPojo.setTriproutine(rs.getString("triproutine"));
				myLogPojo.setUseroutine(rs.getString("useroutine"));
				myLogPojo.setPlayroutine(rs.getString("playroutine"));
				myLogPojo.setInsuranceroutine(rs.getString("insuranceroutine"));
				myLogPojo.setSelfcontrol(rs.getString("selfcontrol"));
				myLogPojo.setDiligence(rs.getString("diligence"));
				myLogPojo.setGoodorder(rs.getString("goodorder"));
				myLogPojo.setClean(rs.getString("clean"));
				myLogPojo.setFrugality(rs.getString("frugality"));
				myLogPojo.setHonest(rs.getString("honest"));
				myLogPojo.setIntegrity(rs.getString("integrity"));
				myLogPojo.setModest(rs.getString("modest"));
				myLogPojo.setFriendly(rs.getString("friendly"));
				myLogPojo.setTolerant(rs.getString("tolerant"));
				myLogPojo.setDiary(rs.getString("diary"));
				al.add(myLogPojo);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			BasicDB.closeDB();
		}
		return al;
	}
	
	//更新日志
	public ArrayList<MyLogPojo> beforeUpdateMyLog(String paras[]){
		String sql="select * from mylog where id=?";
		BasicDB.openDB();
		Connection cn=BasicDB.cn;
		PreparedStatement ps=BasicDB.ps;
		ResultSet rs=BasicDB.rs;
		ArrayList<MyLogPojo> al=new ArrayList<MyLogPojo>();
		try{
			ps=cn.prepareStatement(sql);
			for(int i=0;i<paras.length;i++){
				ps.setString(i+1, paras[i]);
			}
			rs=ps.executeQuery();
			while(rs.next()){
				MyLogPojo myLogPojo=new MyLogPojo();
				myLogPojo.setId(rs.getInt("id"));
				myLogPojo.setDate(rs.getString("date"));
				myLogPojo.setHome(rs.getInt("home"));
				myLogPojo.setClothes(rs.getInt("clothes"));
				myLogPojo.setMeal(rs.getInt("meal"));
				myLogPojo.setRoom(rs.getInt("room"));
				myLogPojo.setTrip(rs.getInt("trip"));
				myLogPojo.setLifeuse(rs.getInt("lifeuse"));
				myLogPojo.setPlay(rs.getInt("play"));
				myLogPojo.setInsurance(rs.getInt("insurance"));
				myLogPojo.setIdroutine(rs.getString("idroutine"));
				myLogPojo.setDateroutine(rs.getString("dateroutine"));
				myLogPojo.setHomeroutine(rs.getString("homeroutine"));
				myLogPojo.setClothesroutine(rs.getString("clothesroutine"));
				myLogPojo.setMealroutine(rs.getString("mealroutine"));
				myLogPojo.setRoomroutine(rs.getString("roomroutine"));
				myLogPojo.setTriproutine(rs.getString("triproutine"));
				myLogPojo.setUseroutine(rs.getString("useroutine"));
				myLogPojo.setPlayroutine(rs.getString("playroutine"));
				myLogPojo.setInsuranceroutine(rs.getString("insuranceroutine"));
				myLogPojo.setSelfcontrol(rs.getString("selfcontrol"));
				myLogPojo.setDiligence(rs.getString("diligence"));
				myLogPojo.setGoodorder(rs.getString("goodorder"));
				myLogPojo.setClean(rs.getString("clean"));
				myLogPojo.setFrugality(rs.getString("frugality"));
				myLogPojo.setHonest(rs.getString("honest"));
				myLogPojo.setIntegrity(rs.getString("integrity"));
				myLogPojo.setModest(rs.getString("modest"));
				myLogPojo.setFriendly(rs.getString("friendly"));
				myLogPojo.setTolerant(rs.getString("tolerant"));
				myLogPojo.setDiary(rs.getString("diary"));
				al.add(myLogPojo);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			BasicDB.closeDB();
		}
		return al;
	}
	public void updateMyLog(String paras[]){
		String sql="update mylog set date=?,home=?,clothes=?,meal=?,room=?,trip=?,lifeuse=?,play=?,insurance=?,idroutine=?,dateroutine=?,homeroutine=?,clothesroutine=?,mealroutine=?,roomroutine=?,triproutine=?,useroutine=?,playroutine=?,insuranceroutine=?,selfcontrol=?,diligence=?,goodorder=?,clean=?,frugality=?,honest=?,integrity=?,modest=?,friendly=?,tolerant=?,diary=? where id=?";
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
	
	//上传文件
	public void uploadMyLog(Object object[]){
		String sql="insert into mylog (id,date,home,clothes,meal,room,trip,lifeuse,play,insurance,"
				+ "idroutine,dateroutine,homeroutine,clothesroutine,mealroutine,roomroutine,triproutine,useroutine,playroutine,insuranceroutine,"
				+ "selfcontrol,diligence,goodorder,clean,frugality,honest,integrity,modest,friendly,tolerant,diary) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
	
	//下载文件
	public ArrayList<MyLogPojo> exportAllMyLog(String paras[]){
		String sql = "select * from mylog order by id ASC";
		BasicDB.openDB();
		Connection cn=BasicDB.cn;
		PreparedStatement ps=BasicDB.ps;
		ResultSet rs=BasicDB.rs;
		ArrayList<MyLogPojo> al=new ArrayList<MyLogPojo>();
		try{
			ps=cn.prepareStatement(sql);
			if(""!=paras[0]&&""!=paras[1]){
				String searchsql="select * from mylog where date>=? and date<=? order by id ASC";
				ps=cn.prepareStatement(searchsql);
				for(int i=0;i<paras.length;i++){
					ps.setString(i+1, paras[i]);
				}
			}
			System.out.println(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				MyLogPojo myLogPojo=new MyLogPojo();
				myLogPojo.setId(rs.getInt("id"));
				myLogPojo.setDate(rs.getString("date"));
				myLogPojo.setHome(rs.getInt("home"));
				myLogPojo.setClothes(rs.getInt("clothes"));
				myLogPojo.setMeal(rs.getInt("meal"));
				myLogPojo.setRoom(rs.getInt("room"));
				myLogPojo.setTrip(rs.getInt("trip"));
				myLogPojo.setLifeuse(rs.getInt("lifeuse"));
				myLogPojo.setPlay(rs.getInt("play"));
				myLogPojo.setInsurance(rs.getInt("insurance"));
				myLogPojo.setIdroutine(rs.getString("idroutine"));
				myLogPojo.setDateroutine(rs.getString("dateroutine"));
				myLogPojo.setHomeroutine(rs.getString("homeroutine"));
				myLogPojo.setClothesroutine(rs.getString("clothesroutine"));
				myLogPojo.setMealroutine(rs.getString("mealroutine"));
				myLogPojo.setRoomroutine(rs.getString("roomroutine"));
				myLogPojo.setTriproutine(rs.getString("triproutine"));
				myLogPojo.setUseroutine(rs.getString("useroutine"));
				myLogPojo.setPlayroutine(rs.getString("playroutine"));
				myLogPojo.setInsuranceroutine(rs.getString("insuranceroutine"));
				myLogPojo.setSelfcontrol(rs.getString("selfcontrol"));
				myLogPojo.setDiligence(rs.getString("diligence"));
				myLogPojo.setGoodorder(rs.getString("goodorder"));
				myLogPojo.setClean(rs.getString("clean"));
				myLogPojo.setFrugality(rs.getString("frugality"));
				myLogPojo.setHonest(rs.getString("honest"));
				myLogPojo.setIntegrity(rs.getString("integrity"));
				myLogPojo.setModest(rs.getString("modest"));
				myLogPojo.setFriendly(rs.getString("friendly"));
				myLogPojo.setTolerant(rs.getString("tolerant"));
				myLogPojo.setDiary(rs.getString("diary"));
				al.add(myLogPojo);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			BasicDB.closeDB();
		}
		return al;
	}
	
	//生成图表
	public int[] showMyLogChart(String[] paras){
		String sql = "select * from mylog order by id ASC";
		BasicDB.openDB();
		Connection cn=BasicDB.cn;
		PreparedStatement ps=BasicDB.ps;
		ResultSet rs=BasicDB.rs;
		int money[]=new int[9];
		try{
			ps=cn.prepareStatement(sql);
			if(""!=paras[0]&&null!=paras[0]&&null!=paras[1]&&""!=paras[1]){
				String searchsql="select * from mylog where date>=? and date<=? order by id ASC";
				ps=cn.prepareStatement(searchsql);
				for(int i=0;i<paras.length;i++){
					ps.setString(i+1, paras[i]);
				}
			}
			rs=ps.executeQuery();
			while(rs.next()){
				for(int i=0;i<money.length-1;i++){
					money[i]+=rs.getInt(i+3);
				}		
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			BasicDB.closeDB();
		}
		return money;
	}
}
