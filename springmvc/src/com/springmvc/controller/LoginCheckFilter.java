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
		 * 1,doFilter�����ĵ�һ������ΪServletRequest���󡣴˶�����������ṩ�˶Խ������Ϣ������*
		 * �����ݡ�cookie��HTTP����ͷ������ȫ���ʡ��ڶ�������ΪServletResponse��ͨ���ڼ򵥵Ĺ�*
		 * �����к��Դ˲��������һ������ΪFilterChain���˲�����������servlet��JSPҳ��
		 */
		HttpServletRequest request = (HttpServletRequest)arg0;
		/**
		 * �������HTTP���󣬲�����Ҫ��������getHeader��getCookies����ServletRequest��*
		 * �޷��õ��ķ�������Ҫ�Ѵ�request�������HttpServletRequest
		 */
		HttpServletResponse response = (HttpServletResponse)arg1;
		String currentURL = request.getRequestURI();
		// ȡ�ø�Ŀ¼����Ӧ�ľ���·��:
		String targetURL = currentURL.substring(currentURL.indexOf("/", 1),
				currentURL.length());
		// ��ȡ����ǰ�ļ������ڱȽ�
		HttpSession session = request.getSession(false);
		if (!"/userlogin.jsp".equals(targetURL)) {// �жϵ�ǰҳ�Ƿ����ض����Ժ�ĵ�¼ҳ��ҳ�棬����ǾͲ���session���жϣ���ֹ������ѭ��
			if (session == null || session.getAttribute("name") == null) {
				// *�û���¼�Ժ����ֶ����session
				System.out.println("request.getContextPath()="
						+ request.getContextPath());
				response.sendRedirect(request.getContextPath() + "/userlogin.jsp");
				// ���sessionΪ�ձ�ʾ�û�û�е�¼���ض���login.jspҳ��
				return;
			}
		}
		// ����filter����������ִ��
		arg2.doFilter(request, response);
		/**
		 * ����FilterChain�����doFilter������Filter�ӿڵ�doFilter����ȡһ��FilterChain������* Ϊ��
		 * ��һ���������ڵ��ô˶����doFilter����ʱ��������һ����صĹ����������û����*
		 * һ����������servlet��JSPҳ���������servlet��JSPҳ�汻���
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
