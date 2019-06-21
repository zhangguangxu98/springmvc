package com.springmvc.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;

import com.springmvc.pojo.UserPojo;
@Controller("LoginCheckFilter")
public class LoginCheckFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		/**
		 * 1,doFilter方法的第一个参数为ServletRequest对象。此对象给过滤器提供了对进入的信息（包括*
		 * 表单数据、cookie和HTTP请求头）的完全访问。第二个参数为ServletResponse，通常在简单的过*
		 * 滤器中忽略此参数。最后一个参数为FilterChain，此参数用来调用servlet或JSP页。
		 */
		HttpServletRequest request = (HttpServletRequest)arg0;
		/**
		 * 如果处理HTTP请求，并且需要访问诸如getHeader或getCookies等在ServletRequest中*
		 * 无法得到的方法，就要把此request对象构造成HttpServletRequest
		 */
		HttpServletResponse response = (HttpServletResponse)arg1;
		String currentURL = request.getRequestURI();
		// 取得根目录所对应的绝对路径:
		String targetURL = currentURL.substring(currentURL.indexOf("/", 1),
				currentURL.length());
		// 截取到当前文件名用于比较
		HttpSession session = request.getSession(false);
		if (!"/userlogin.jsp".equals(targetURL)) {// 判断当前页是否是重定向以后的登录页面页面，如果是就不做session的判断，防止出现死循环
			if (session == null || session.getAttribute("name") == null) {
				// *用户登录以后需手动添加session
				System.out.println("request.getContextPath()="
						+ request.getContextPath());
				response.sendRedirect(request.getContextPath() + "/userlogin.jsp");
				// 如果session为空表示用户没有登录就重定向到login.jsp页面
				return;
			}
		}
		// 加入filter链继续向下执行
		arg2.doFilter(request, response);
		/**
		 * 调用FilterChain对象的doFilter方法。Filter接口的doFilter方法取一个FilterChain对象作* 为它
		 * 的一个参数。在调用此对象的doFilter方法时，激活下一个相关的过滤器。如果没有另*
		 * 一个过滤器与servlet或JSP页面关联，则servlet或JSP页面被激活。
		 */
		/*HttpServletRequest request=(HttpServletRequest)arg0;
		HttpServletResponse response=(HttpServletResponse)arg1;
		User user=(User)request.getSession().getAttribute("loginUser");
		if(userPojo==null){
			response.sendRedirect(request.getContextPath()+"/userlogin.jsp");
			return;
		}
		arg2.doFilter(arg0, arg1);*/
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
