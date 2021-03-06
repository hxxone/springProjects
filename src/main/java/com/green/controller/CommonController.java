package com.green.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class CommonController {
	@GetMapping("/accessError")
	public void accessDenied(Authentication auth, Model model) {
		System.out.println( " 접근제한 : " + auth);
		model.addAttribute("msg", "Access Denied");
	}
	
	
	@GetMapping("/customLogin")
	public void loginInput(String error, String logout, Model model) {
		log.info("error : " + error);
		log.info("logout : " + logout);
		if(error!=null) model.addAttribute("error" , " 로그인 에러 이므로 계정을 확인하세요");
		if(logout !=null) model.addAttribute("logout", "로그아웃 되었습니다");
	}
	
	@GetMapping("/customLogout")
	public void logoutGet() {
		log.info("custom logout");
	}
}
