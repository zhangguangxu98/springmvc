package com.springmvc.service;

import java.util.ArrayList;
import java.util.Map;

import com.springmvc.page.Pager;
import com.springmvc.pojo.MyLogPojo;
import com.springmvc.pojo.MyPlanPojo;

public interface AdminShowAllMyPlanService {
	
	//�����־
	public String addMyPlan(Object object[]);
	
	//ɾ����־
	public void deleteMyPlan(String paras[]);

	//��ʾ��־
	public ArrayList<MyPlanPojo> showAllMyPlan(String[] paras);
	public Pager<MyPlanPojo> searchPager(MyPlanPojo myPlanPojo, int pageNum,  int pageSize, String[] paras);
	
	//������־
	public ArrayList<MyPlanPojo> beforeUpdateMyPlan(String paras[]);
	public String updateMyPlan(String paras[]);
	
	//�ϴ��ļ�
	public String readExcel(String filePath);
	
	//�����ļ�
	public ArrayList<MyPlanPojo> exportAllMyPlan(String[] paras);
	
	//����ͼ��
	public Map<String,Integer> showMyPlanChart(String[] paras);

}
