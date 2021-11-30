package com.green.controller;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.green.service.FreeService;
import com.green.service.LoanService;
import com.green.vo.FreeVO;
import com.green.vo.LoanVO;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import oracle.net.aso.m;

@RestController
@RequestMapping("/register/*")
@Slf4j
public class RegisterController {
	
	@Setter(onMethod_=@Autowired)
	FreeService service;
	
	@Setter(onMethod_=@Autowired)
	LoanService LoanService;
	
	@PostMapping(value="/transaction",
			consumes= "application/json",
			produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> Transactional(@RequestBody FreeVO vo){
		int result = service.depositAndWithdrawal(vo);
		System.out.println("======================================" + vo);
		System.out.println(" ===================== reust ============" +result);
		return result ==1? new ResponseEntity<String>("success", HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(value="/borrow",
			consumes= "application/json",
			produces ={MediaType.APPLICATION_JSON_VALUE,
		MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<List<LoanVO>> borrowMone (@RequestBody LoanVO loanVo){
		System.out.println("---------------------------------===========");
		if( LoanService.getBalanceByName(loanVo.getLoan_name()).getLoan_balance() >= loanVo.getLoan_fee()) {
			LoanService.borrow(loanVo);
			
			System.out.println("======================================" + loanVo);
		
			return new ResponseEntity<List<LoanVO>>(LoanService.getList(loanVo.getLoan_name()), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(LoanService.getList(loanVo.getLoan_name()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	

	@PostMapping(value="/sign_up",
			consumes= "application/json",
			produces = {MediaType.TEXT_PLAIN_VALUE} )
	public ResponseEntity<String> sign_up(@RequestBody FreeVO vo){
		
		
		int result = service.signUp(vo);
		System.out.println("======================================" + vo);
		System.out.println(" ===================== reust ============" +result);
		return result ==1? new ResponseEntity<String>("success", HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
	

}
