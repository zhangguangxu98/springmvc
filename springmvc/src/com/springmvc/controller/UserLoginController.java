package com.springmvc.controller;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.springmvc.pojo.UserPojo;
import com.springmvc.service.UserLoginService;

@Controller("UserLoginController")
public class UserLoginController{
	private static final Logger logger=Logger.getLogger(UserLoginController.class);
	
	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	//login
	@RequestMapping(value="/userlogin.do", method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView userLogin(HttpServletRequest request,HttpServletResponse response){
		logger.info("登录成功：");
		
		String name = request.getParameter("name");
        String password = request.getParameter("password");
    	String paras[]={name,password};
        String error="没有该账户或者密码错误";
        String markerror="没有该角色";
	
		ArrayList<UserPojo> al=userLoginService.logIn(paras);
		if(al.size()==0){
/*			return "userlogin";*/
			return new ModelAndView("userlogin","error",error);
		}
		request.getSession().setAttribute("name", password);
		UserPojo userPojo=al.get(0);
		if((userPojo.getMark()).equals("admin")){
/*			return "/adminhome/adminhome";*/
			return new ModelAndView("/adminhome/adminhome");
		}else if((userPojo.getMark()).equals("other")){
/*			return "othershome";*/
			return new ModelAndView("/otherhome/otherhome");
		}else{
/*			return "userlogin";*/
			return new ModelAndView("userlogin","error",markerror);
		}    
	}
}
