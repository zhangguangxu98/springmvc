package com.springmvc.service.impl;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.springmvc.dao.UserLoginDao;
import com.springmvc.pojo.UserPojo;
import com.springmvc.service.UserLoginService;
@Service("userLoginService")
public class UserLoginServiceImpl implements UserLoginService {
	
	@Resource(name="userLoginDao") 
	private UserLoginDao userLoginDao;
	
	public ArrayList<UserPojo> logIn(String[] paras){
		String sql="select * from user where name=? and password=?";
		return userLoginDao.logIn(paras,sql);
	}
}
