package com.green.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.green.service.UserService;
import com.green.vo.UserVO;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/shop/*")
@Slf4j
public class ShopController {
	
	@Setter(onMethod_=@Autowired)
	UserService service;
	
	@GetMapping("/main")
	public void main() {
		log.info(" 여기는 메인 페이지 " );
	}
	
	
	@GetMapping("/product")
	public void produc(@RequestParam("id") Long user_id, Model model ) {
		model.addAttribute("user" , service.getUser(user_id));
		model.addAttribute("item", service.getAllProduct());
		
	}
	
	@GetMapping("/rest")
	public void rest() {
		
	}

	

}
