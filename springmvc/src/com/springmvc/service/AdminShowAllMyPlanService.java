package com.springmvc.service;

import java.util.ArrayList;
import java.util.Map;

import com.springmvc.page.Pager;
import com.springmvc.pojo.MyLogPojo;
import com.springmvc.pojo.MyPlanPojo;

public interface AdminShowAllMyPlanService {
	
	//添加日志
	public String addMyPlan(Object object[]);
	
	//删除日志
	public void deleteMyPlan(String paras[]);

	//显示日志
	public ArrayList<MyPlanPojo> showAllMyPlan(String[] paras);
	public Pager<MyPlanPojo> searchPager(MyPlanPojo myPlanPojo, int pageNum,  int pageSize, String[] paras);
	
	//更新日志
	public ArrayList<MyPlanPojo> beforeUpdateMyPlan(String paras[]);
	public String updateMyPlan(String paras[]);
	
	//上传文件
	public String readExcel(String filePath);
	
	//下载文件
	public ArrayList<MyPlanPojo> exportAllMyPlan(String[] paras);
	
	//生成图表
	public Map<String,Integer> showMyPlanChart(String[] paras);

}
