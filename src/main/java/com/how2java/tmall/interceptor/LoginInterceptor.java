package com.how2java.tmall.interceptor;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.how2java.tmall.pojo.User;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.OrderItemService;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler) throws IOException{
		
		HttpSession session = request.getSession();
		String contextPath = session.getServletContext().getContextPath();
		System.out.println(contextPath);
		String[] notNeedAuthPage = new String[]{
				"home",
				"checkLogin",
				"register",
				"login",
				"loginAjax",
				"product",
				"category",
				"search"
		};
		String uri = request.getRequestURI();
		System.out.println(uri);
		uri = StringUtils.remove(uri, contextPath);
		if(uri.startsWith("/fore")){
			String method = StringUtils.substringAfterLast(uri, "/fore");
			if(!Arrays.asList(notNeedAuthPage).contains(method)){
				User user = (User) session.getAttribute("user");
				if(user==null){
					response.sendRedirect("loginPage");
					return false;
				}
			}
		}
		return true;
}
	
	public void postHandle(HttpServletRequest request,HttpServletResponse response,Object handler,ModelAndView modelAndView) throws Exception{
		super.postHandle(request,response,handler,modelAndView);
}
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
	}
	
}
