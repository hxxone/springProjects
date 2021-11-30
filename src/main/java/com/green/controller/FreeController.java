package com.green.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.io.ResolverUtil.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.green.service.FreeService;
import com.green.vo.FreeVO;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/free/*")
@Slf4j
public class FreeController {
	@Setter(onMethod_=@Autowired)
	FreeService service;
	
	@GetMapping("/login")
	public void login() {
		
	}
	
	@PostMapping("/login")
	public String main(Model model, FreeVO vo) {
		log.info("main");
		
		if(service.selectAll().stream()
				.filter(i -> i.getFree_name().equals(vo.getFree_name()) && i.getFree_password()
						.equals(vo.getFree_password()))
				.collect(Collectors.toList())
				.size()>0){
			model.addAttribute("name", vo.getFree_name());
			model.addAttribute("serial", service.balancePerSerial(vo.getFree_name()));
			model.addAttribute("list", service.selectAll());
			return "/free/main";
		}
		model.addAttribute("error" , " 로그인 정보가 없습니다 ");
		return "/free/login";
	}
	
	@GetMapping("/register")
	public void register() {
		
	}
	
	
//	@PostMapping(value ="/test")
//	public String registerDeal(FreeVO vo, Model model) {
//		
//		service.depositAndWithdrawal(vo);
//
//		return "redirect:/free/main";
//	}
	
	
	
//	@GetMapping("/test")
//	public void Test(Model model) {
//		model.addAttribute("test", service.selectAll());
//		
//	}
//	@PostMapping("/login")
//	public String loginPost(FreeVO vo, RedirectAttributes rttr) {
//		
//	}
	
	
	@GetMapping("/account")
	public void account(@RequestParam("name") String free_name, Model model) {
		model.addAttribute("name", free_name);
		model.addAttribute("psd", service.bankBook(free_name).get(0).getFree_password());
		model.addAttribute("list", service.bankBook(free_name));
	}
	
	
	
	

	 @GetMapping(value="/getAccountBySerial", produces=MediaType.APPLICATION_JSON_UTF8_VALUE) 
	 @ResponseBody
	 public ResponseEntity<List<FreeVO>> getAccountBySerial(@RequestParam("free_serial") String free_serial) { 
		 
		 System.out.println(free_serial);
		 List<FreeVO> list = service.getListBySerial(free_serial);
		 list.forEach(i-> System.out.println(i));
		 
		 return new ResponseEntity<List<FreeVO>>(list, HttpStatus.OK);
	 }
	 
	@GetMapping(value="/getInfo", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	 public ResponseEntity<List<FreeVO>> gerInfo() {
		 List<FreeVO> list = service.selectAll();
		 return new ResponseEntity<List<FreeVO>>(list, HttpStatus.OK);
	 }
	
	
	
	
	
	

	
	
	
	
	
	
	/*
	 * @PostMapping("/login") public void getLogin(FreeVO vo, RedirectAttributes
	  //rttr) { System.out.println(" ====================== 로그인"); }
	 */
	
//	
//	@PostMapping(value="/getSerial", produces =MediaType.APPLICATION_JSON_UTF8_VALUE)
//	@ResponseBody
//	public void getSerial(FreeVO vo){
//		service.getSerial(vo);
//	}
	

}
