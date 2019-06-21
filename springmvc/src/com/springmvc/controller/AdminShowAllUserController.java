package com.springmvc.controller;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.springmvc.page.Pager;
import com.springmvc.pojo.LotteryResultPojo;
import com.springmvc.pojo.UserPojo;
import com.springmvc.service.AdminShowAllUserService;

@Controller("AdminShowAllUserController")
public class AdminShowAllUserController {
	
	@Resource(name="adminShowAllUserService") 
	private AdminShowAllUserService adminShowAllUserService;
	
	//ÃÌº””√ªß
	@RequestMapping(value="/adminadduser.do", method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView adminAddLotteryResult(HttpServletRequest request,HttpServletResponse response){
		String name = request.getParameter("name");
		String password = request.getParameter("password");
        String email = request.getParameter("email");
        String mark = request.getParameter("mark");
        String phone = request.getParameter("phone");
        
		Object object[]={name,password,email,phone,mark};
		adminShowAllUserService.addUser(object);
		return new ModelAndView("redirect:/adminshowalluser.do?pageNum=1");
	}
	
	//delete
	@RequestMapping(value="/admindeleteuser.do", method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView adminDeleteUser(HttpServletRequest request,HttpServletResponse response){	
		String[] phase = request.getParameterValues("tduCheckBox");

		for(int i=0;i<phase.length;i++){
			String paras[]={phase[i]};
			System.out.println(phase[i]);
			adminShowAllUserService.deleteUser(paras);
		}
		return new ModelAndView("redirect:/adminshowalluser.do?pageNum=1");
	}
	
	//show
	@RequestMapping(value="/adminshowalluser.do", method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView adminShowAllUser(HttpServletRequest request,HttpServletResponse response){
		String pageNumString = request.getParameter("pageNum");
		String phase = request.getParameter("name");
		int pageNum=Integer.valueOf(pageNumString);
		String paras[]={phase};
		final int pageSize=10;
		
		UserPojo userPojo=new UserPojo();
		Pager pager=new Pager();
		
		if(null!=phase&&""!=phase){
			pager = adminShowAllUserService.searchPager(userPojo, pageNum, pageSize,paras);
		}else{
			pager = adminShowAllUserService.showPager(userPojo, pageNum, pageSize);
		}
		return new ModelAndView("/adminuser/adminshowalluser","pager",pager);
	}
	
	//update
	@RequestMapping(value="/adminbeforeupdateuser.do", method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView beforeUpdateLotteryResult(HttpServletRequest request,HttpServletResponse response){
		String phase = request.getParameter("name");
		String paras[]={phase};
		
		ArrayList<UserPojo> adminbeforeupdateuserlist=adminShowAllUserService.beforeUpdateUser(paras);
		return new ModelAndView("/adminuser/adminbeforeupdateuser","adminbeforeupdateuserlist",adminbeforeupdateuserlist);
	}
	@RequestMapping(value="/adminupdateuser.do", method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView adminAddUser(HttpServletRequest request,HttpServletResponse response){
		String name = request.getParameter("name");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String mark = request.getParameter("mark");
        String phone = request.getParameter("phone");

		 String paras[]={password,email,mark,phone,name};
		 adminShowAllUserService.updateUser(paras);
		return new ModelAndView("redirect:/adminshowalluser.do?pageNum=1");
	}
}
