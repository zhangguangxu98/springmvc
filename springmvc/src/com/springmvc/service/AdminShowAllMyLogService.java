package com.springmvc.service;

import java.util.ArrayList;

import com.springmvc.page.Pager;
import com.springmvc.pojo.LotteryResultPojo;
import com.springmvc.pojo.MyLogPojo;

public interface AdminShowAllMyLogService {
	
	//添加日志
	public String addMyLog(Object object[]);
	
	//删除日志
	public void deleteMyLog(String paras[]);

	//显示日志
	public ArrayList<MyLogPojo> showAllMyLog(String[] paras);
	public Pager<MyLogPojo> searchPager(MyLogPojo lotteryResultPojo, int pageNum,  int pageSize, String[] paras);
	
	//更新日志
	public ArrayList<MyLogPojo> beforeUpdateMyLog(String paras[]);
	public String updateMyLog(String paras[]);
	
	//上传文件
	public String readExcel(String filePath);
	
	//下载文件
	public ArrayList<MyLogPojo> exportAllMyLog(String[] paras);
	
	//生成图表
	public int[] showMyLogChart(String[] paras);
}
