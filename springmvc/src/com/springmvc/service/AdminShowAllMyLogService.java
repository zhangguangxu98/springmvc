package com.springmvc.service;

import java.util.ArrayList;

import com.springmvc.page.Pager;
import com.springmvc.pojo.LotteryResultPojo;
import com.springmvc.pojo.MyLogPojo;

public interface AdminShowAllMyLogService {
	
	//�����־
	public String addMyLog(Object object[]);
	
	//ɾ����־
	public void deleteMyLog(String paras[]);

	//��ʾ��־
	public ArrayList<MyLogPojo> showAllMyLog(String[] paras);
	public Pager<MyLogPojo> searchPager(MyLogPojo lotteryResultPojo, int pageNum,  int pageSize, String[] paras);
	
	//������־
	public ArrayList<MyLogPojo> beforeUpdateMyLog(String paras[]);
	public String updateMyLog(String paras[]);
	
	//�ϴ��ļ�
	public String readExcel(String filePath);
	
	//�����ļ�
	public ArrayList<MyLogPojo> exportAllMyLog(String[] paras);
	
	//����ͼ��
	public int[] showMyLogChart(String[] paras);
}
