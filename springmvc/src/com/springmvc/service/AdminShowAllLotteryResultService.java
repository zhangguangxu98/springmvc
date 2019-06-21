package com.springmvc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.springmvc.page.Pager;
import com.springmvc.pojo.LotteryResultPojo;

public interface AdminShowAllLotteryResultService {
	
	//add
	public String addLotteryResult(Object object[]);
	
	//delete
	public void deleteLotteryResult(String paras[]);
	
	//show
	public ArrayList<LotteryResultPojo> showAllLotteryResult();
	public ArrayList<LotteryResultPojo> searchLotteryResult(String[] paras);
	public Pager<LotteryResultPojo> showPager(LotteryResultPojo lotteryResultPojo, int pageNum,  int pageSize);
	public Pager<LotteryResultPojo> searchPager(LotteryResultPojo lotteryResultPojo, int pageNum,  int pageSize, String[] paras);
	
	//update
	public ArrayList<LotteryResultPojo> beforeUpdateLotteryResult(String paras[]);
	public void updateLotteryResult(String paras[]);
	
	//upload lotteryresult
	public String readExcel(String filePath);
	
	//download lotteryresult
	public ArrayList<LotteryResultPojo> exportAllLotteryResult();
	
	//lotteryresultchart
	public int[] showLotteryResultChart(String[] paras,String type);
}
