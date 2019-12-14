package com.how2java.tmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class PageController {
	
	@RequestMapping("registerPage")
	public String register(){
		return "fore/register";
	}
	
	@RequestMapping("loginPage")
	public String login(){
		return "fore/login";
	}
	
	@RequestMapping("registerSuccessPage")
	public String registerSuccessPage(){
		return "fore/registerSuccess";
	}
	
	@RequestMapping("forealipay")
	public String alipay(){
		return "fore/alipay";
	}
}
