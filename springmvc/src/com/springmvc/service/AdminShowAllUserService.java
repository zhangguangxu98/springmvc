package com.springmvc.service;

import java.util.ArrayList;

import com.springmvc.page.Pager;
import com.springmvc.pojo.UserPojo;

public interface AdminShowAllUserService {
	
		//ÃÌº””√ªß
	    public void addUser(Object object[]);

	    //delete
  		public void deleteUser(String paras[]);
  		
		//show
		public ArrayList<UserPojo> showAllUser();
		public ArrayList<UserPojo> searchUser(String[] paras);
		public Pager<UserPojo> showPager(UserPojo userPojo, int pageNum,  int pageSize);
		public Pager<UserPojo> searchPager(UserPojo userPojo, int pageNum,  int pageSize, String[] paras);

		//update
		public ArrayList<UserPojo> beforeUpdateUser(String paras[]);
		public void updateUser(String paras[]);
}
