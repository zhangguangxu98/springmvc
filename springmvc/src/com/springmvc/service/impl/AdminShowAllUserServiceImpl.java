package com.springmvc.service.impl;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.springmvc.dao.AdminShowAllUserDao;
import com.springmvc.page.Pager;
import com.springmvc.pojo.UserPojo;
import com.springmvc.service.AdminShowAllUserService;
@Service("adminShowAllUserService")
public class AdminShowAllUserServiceImpl implements AdminShowAllUserService {
	
		@Resource(name="adminShowAllUserDao") 
		private AdminShowAllUserDao adminShowAllUserDao;
	
		//add
		public void addUser(Object object[]){
			adminShowAllUserDao.addUser(object);
		}
		
		//delete
		public void deleteUser(String paras[]){
			adminShowAllUserDao.deleteUser(paras);
		}
		
		//show
		public ArrayList<UserPojo> showAllUser(){
			ArrayList<UserPojo> al=adminShowAllUserDao.showAllUser();
			return al;
		}
		public Pager<UserPojo> showPager(UserPojo userPojo, int pageNum,  int pageSize) {     
	        Pager<UserPojo> pager = new Pager<UserPojo>(pageNum, pageSize, showAllUser());  
	        return pager;  
	    } 
		
		public ArrayList<UserPojo> searchUser(String[] paras){
			ArrayList<UserPojo> al=adminShowAllUserDao.searchUser(paras);
			return al;
		}
		public Pager<UserPojo> searchPager(UserPojo userPojo, int pageNum,  int pageSize, String[] paras) {     
	        Pager<UserPojo> pager = new Pager<UserPojo>(pageNum, pageSize, searchUser(paras));  
	        return pager;  
	    }
		
		//update
		public ArrayList<UserPojo> beforeUpdateUser(String paras[]){
			ArrayList<UserPojo> ul=adminShowAllUserDao.beforeUpdateUser(paras);
			return ul;
		}
		public void updateUser(String paras[]){
			adminShowAllUserDao.updateUser(paras);
		}
}
