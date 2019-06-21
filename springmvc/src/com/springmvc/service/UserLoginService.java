package com.springmvc.service;

import java.util.ArrayList;

import com.springmvc.pojo.UserPojo;

public interface UserLoginService {
	
	//login
	public ArrayList<UserPojo> logIn(String[] paras);
}
