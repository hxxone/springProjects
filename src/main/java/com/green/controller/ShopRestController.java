package com.green.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.green.service.InvoiceService;
import com.green.service.UserService;
import com.green.vo.InvoiceVO;
import com.green.vo.ProductVO;
import com.green.vo.UserVO;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/shop/*")
@Slf4j
public class ShopRestController {
	
	@Setter(onMethod_=@Autowired)
	UserService service;
	
	@Setter(onMethod_=@Autowired)
	InvoiceService invoiceService;

	
	@RequestMapping("/study")
	public String study01() {
		return " 이게 된다고? ";
	}
	
	@PostMapping(value = "/signUp",
			consumes= "application/json",
			produces = {MediaType.TEXT_PLAIN_VALUE}
			)
	public ResponseEntity<String> register(@RequestBody UserVO user){
		int result = service.signUp(user);
		
		System.out.println(" 회원가입 컨트롤러 " + user);
		return result==1? new ResponseEntity<String>("success", HttpStatus.OK)
				:new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
	@GetMapping(value="/getAll", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)	
	public ResponseEntity<List<UserVO>> signUpcheck (){

		return new ResponseEntity<>(service.getAllUser(), HttpStatus.OK);
	}
	
	@GetMapping(value="/getProduct", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)	
	public ResponseEntity<List<ProductVO>> price (){

		return new ResponseEntity<>(service.getAllProduct(), HttpStatus.OK);
	}
	
	@PostMapping(value = "/purchace",
			consumes= "application/json",
			produces = {MediaType.TEXT_PLAIN_VALUE}
			)
	public ResponseEntity<String> register(@RequestBody InvoiceVO vo){
		int result = invoiceService.purchace(vo);
		return result==1? new ResponseEntity<String>("success", HttpStatus.OK)
				:new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	

	
	@GetMapping(value="/getCart", produces=MediaType.APPLICATION_JSON_UTF8_VALUE) 
	public ResponseEntity<List<InvoiceVO>> showInvoice(@RequestParam("id") Long id) { 
		 List<InvoiceVO> list = invoiceService.getInvoiceById(id);
		 return new ResponseEntity<>(list, HttpStatus.OK);
	 }
	
	
	@DeleteMapping("/remove/{invoice}")
    public int delete(@PathVariable("invoice") String invoice) {
        return invoiceService.remove(invoice);
    }
	
	
	@PostMapping("/")
	public String test1(ProductVO vo) {
		
		return vo.toString();
	}
	
	@PostMapping(value = "/json")
	public Object json(@RequestBody ProductVO vo) {
		List<ProductVO> vos = new ArrayList<ProductVO>();
		vos.add(vo);
		return vos;
	}
	


}
