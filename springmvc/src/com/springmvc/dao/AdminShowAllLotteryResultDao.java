package com.springmvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.springmvc.db.BasicDB;
import com.springmvc.pojo.LotteryResultPojo;

@Repository("adminShowAllLotteryResultDao")
public class AdminShowAllLotteryResultDao {
	
	//add
	public String addLotteryResult(Object object[]){
		String sucess="添加失败";
		String sql="insert into lotteryresult(phase,date,red1,red2,red3,red4,red5,blue1,blue2) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
		sucess="添加成功";
		return sucess;
	}
	
	//delete
	public void deleteLotteryResult(String paras[]){
		String sql="delete from lotteryresult where phase=?";
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
	public ArrayList<LotteryResultPojo> showAllLotteryResult(){
		String sql = "select * from lotteryresult order by phase DESC";
		BasicDB.openDB();
		Connection cn=BasicDB.cn;
		PreparedStatement ps=BasicDB.ps;
		ResultSet rs=BasicDB.rs;
		ArrayList<LotteryResultPojo> al=new ArrayList<LotteryResultPojo>();
		try{
			ps=cn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				LotteryResultPojo lotteryResultPojo=new LotteryResultPojo();
				lotteryResultPojo.setPhase(rs.getInt("phase"));
				lotteryResultPojo.setDate(rs.getString("date"));
				lotteryResultPojo.setRed1(rs.getInt("red1"));
				lotteryResultPojo.setRed2(rs.getInt("red2"));
				lotteryResultPojo.setRed3(rs.getInt("red3"));
				lotteryResultPojo.setRed4(rs.getInt("red4"));
				lotteryResultPojo.setRed5(rs.getInt("red5"));
				lotteryResultPojo.setBlue1(rs.getInt("blue1"));
				lotteryResultPojo.setBlue2(rs.getInt("blue2"));
				al.add(lotteryResultPojo);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			BasicDB.closeDB();
		}
		return al;
	}
	public ArrayList<LotteryResultPojo> searchLotteryResult(String paras[]){
		String sql="select * from lotteryresult where date>=? and date<=?";
		System.out.println(sql);
		BasicDB.openDB();
		Connection cn=BasicDB.cn;
		PreparedStatement ps=BasicDB.ps;
		ResultSet rs=BasicDB.rs;
		ArrayList<LotteryResultPojo> al=new ArrayList<LotteryResultPojo>();
		try{
			ps=cn.prepareStatement(sql);
			for(int i=0;i<paras.length;i++){
				ps.setString(i+1, paras[i]);
			}
			rs=ps.executeQuery();
			while(rs.next()){
				LotteryResultPojo lotteryResultPojo=new LotteryResultPojo();
				lotteryResultPojo.setPhase(rs.getInt("phase"));
				lotteryResultPojo.setDate(rs.getString("date"));
				lotteryResultPojo.setRed1(rs.getInt("red1"));
				lotteryResultPojo.setRed2(rs.getInt("red2"));
				lotteryResultPojo.setRed3(rs.getInt("red3"));
				lotteryResultPojo.setRed4(rs.getInt("red4"));
				lotteryResultPojo.setRed5(rs.getInt("red5"));
				lotteryResultPojo.setBlue1(rs.getInt("blue1"));
				lotteryResultPojo.setBlue2(rs.getInt("blue2"));
				al.add(lotteryResultPojo);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			BasicDB.closeDB();
		}
		return al;
	}
	
	//update
	public ArrayList<LotteryResultPojo> beforeUpdateLotteryResult(String paras[]){
		String sql="select * from lotteryresult where phase=?";
		BasicDB.openDB();
		Connection cn=BasicDB.cn;
		PreparedStatement ps=BasicDB.ps;
		ResultSet rs=BasicDB.rs;
		ArrayList<LotteryResultPojo> al=new ArrayList<LotteryResultPojo>();
		try{
			ps=cn.prepareStatement(sql);
			for(int i=0;i<paras.length;i++){
				ps.setString(i+1, paras[i]);
			}
			rs=ps.executeQuery();
			while(rs.next()){
				LotteryResultPojo lotteryResultPojo=new LotteryResultPojo();
				lotteryResultPojo.setPhase(rs.getInt("phase"));
				lotteryResultPojo.setDate(rs.getString("date"));
				lotteryResultPojo.setRed1(rs.getInt("red1"));
				lotteryResultPojo.setRed2(rs.getInt("red2"));
				lotteryResultPojo.setRed3(rs.getInt("red3"));
				lotteryResultPojo.setRed4(rs.getInt("red4"));
				lotteryResultPojo.setRed5(rs.getInt("red5"));
				lotteryResultPojo.setBlue1(rs.getInt("blue1"));
				lotteryResultPojo.setBlue2(rs.getInt("blue2"));
				al.add(lotteryResultPojo);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			BasicDB.closeDB();
		}
		return al;
	}
	public void updateLotteryResult(String paras[]){
		String sql="update lotteryresult set date=?, red1=?,red2=?, red3=?,red4=?,red5=?,blue1=?,blue2=? where phase=?";
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
	
	//upload lotteryresult
	public void uploadLotteryResult(String sql, Object object[]){
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
	
	//download lotteryresult
	public ArrayList<LotteryResultPojo> exportAllLotteryResult(String sql){
		BasicDB.openDB();
		Connection cn=BasicDB.cn;
		PreparedStatement ps=BasicDB.ps;
		ResultSet rs=BasicDB.rs;
		ArrayList<LotteryResultPojo> al=new ArrayList<LotteryResultPojo>();
		try{
			ps=cn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				LotteryResultPojo lotteryresultpojo=new LotteryResultPojo();
				lotteryresultpojo.setPhase(rs.getInt(1));
				lotteryresultpojo.setDate(rs.getString(2));
				lotteryresultpojo.setRed1(rs.getInt(3));
				lotteryresultpojo.setRed2(rs.getInt(4));
				lotteryresultpojo.setRed3(rs.getInt(5));
				lotteryresultpojo.setRed4(rs.getInt(6));
				lotteryresultpojo.setRed5(rs.getInt(7));
				lotteryresultpojo.setBlue1(rs.getInt(8));
				lotteryresultpojo.setBlue2(rs.getInt(9));
				al.add(lotteryresultpojo);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			BasicDB.closeDB();
		}
		return al;
	}
	
	//lotteryresultchart
	public int[] showLotteryResultChart(String[] paras,int number){
		String searchsql="select * from lotteryresult where date>=? and date<=?";
		String sql="select * from lotteryresult";
		BasicDB.openDB();
		Connection cn=BasicDB.cn;
		PreparedStatement ps=BasicDB.ps;
		ResultSet rs=BasicDB.rs;
		int redone1=0,redone2=0,redone3=0,redone4=0,redone5=0,redone6=0,redone7=0,redone8=0,redone9=0,redone10=0,redone11=0,redone12=0,redone13=0,redone14=0,redone15=0,redone16=0,redone17=0,redone18=0,redone19=0,redone20=0,redone21=0,redone22=0,redone23=0,redone24=0,redone25=0,redone26=0,redone27=0,redone28=0,redone29=0,redone30=0,redone31=0,redone32=0,redone33=0,redone34=0,redone35=0,redoneothers=0;
		int quantityone[]={redone1,redone2,redone3,redone4,redone5,redone6,redone7,redone8,redone9,redone10,redone11,redone12,redone13,redone14,redone15,redone16,redone17,redone18,redone19,redone20,redone21,redone22,redone23,redone24,redone25,redone26,redone27,redone28,redone29,redone30,redone31,redone32,redone33,redone34,redone35,redoneothers};
		System.out.println(paras[0]+":"+paras[1]);
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
				if(rs.getFloat(number)==1){
					quantityone[0]++;
				}else if(rs.getFloat(number)==2){
					quantityone[1]++;
				}else if(rs.getFloat(number)==3){
					quantityone[2]++;
				}else if(rs.getFloat(number)==4){
					quantityone[3]++;
				}else if(rs.getFloat(number)==5){
					quantityone[4]++;
				}else if(rs.getFloat(number)==6){
					quantityone[5]++;
				}else if(rs.getFloat(number)==7){
					quantityone[6]++;
				}else if(rs.getFloat(number)==8){
					quantityone[7]++;
				}else if(rs.getFloat(number)==9){
					quantityone[8]++;
				}else if(rs.getFloat(number)==10){
					quantityone[9]++;
				}else if(rs.getFloat(number)==11){
					quantityone[10]++;
				}else if(rs.getFloat(number)==12){
					quantityone[11]++;
				}else if(rs.getFloat(number)==13){
					quantityone[12]++;
				}else if(rs.getFloat(number)==14){
					quantityone[13]++;
				}else if(rs.getFloat(number)==15){
					quantityone[14]++;
				}else if(rs.getFloat(number)==16){
					quantityone[15]++;
				}else if(rs.getFloat(number)==17){
					quantityone[16]++;
				}else if(rs.getFloat(number)==18){
					quantityone[17]++;
				}else if(rs.getFloat(number)==19){
					quantityone[18]++;
				}else if(rs.getFloat(number)==20){
					quantityone[19]++;
				}else if(rs.getFloat(number)==21){
					quantityone[20]++;
				}else if(rs.getFloat(number)==22){
					quantityone[21]++;
				}else if(rs.getFloat(number)==23){
					quantityone[22]++;
				}else if(rs.getFloat(number)==24){
					quantityone[23]++;
				}else if(rs.getFloat(number)==25){
					quantityone[24]++;
				}else if(rs.getFloat(number)==26){
					quantityone[25]++;
				}else if(rs.getFloat(number)==27){
					quantityone[26]++;
				}else if(rs.getFloat(number)==28){
					quantityone[27]++;
				}else if(rs.getFloat(number)==29){
					quantityone[28]++;
				}else if(rs.getFloat(number)==30){
					quantityone[29]++;
				}else if(rs.getFloat(number)==31){
					quantityone[30]++;
				}else if(rs.getFloat(number)==32){
					quantityone[31]++;
				}else if(rs.getFloat(number)==33){
					quantityone[32]++;
				}else if(rs.getFloat(number)==34){
					quantityone[33]++;
				}else if(rs.getFloat(number)==35){
					quantityone[34]++;
				}else{
					quantityone[35]++;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			BasicDB.closeDB();
		}
		return quantityone;
	}
}
