package com.green.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.green.service.FreeService;
import com.green.service.LoanService;
import com.green.vo.LoanVO;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/loan/*")
@Slf4j
public class LoanController {
	
	@Setter(onMethod_=@Autowired)
	FreeService fservice;
	@Setter(onMethod_=@Autowired)
	LoanService service;
	
	
	@GetMapping("/test")
	public void test( Model model, @RequestParam("name") String free_name) {
		model.addAttribute("list", service.getList(free_name));
		model.addAttribute("test", fservice.balancePerSerial(free_name));
	}
	
	@GetMapping("/getLoan")
	@ResponseBody
	public String getLoan(LoanVO vo){
		
		if( service.getList(vo.getLoan_name()).size() > 0 ) return "already Exist";
		service.getloan(vo);
		return "success";
	}
	

}
