package com.green.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.green.vo.LoanVO;


public interface LoanService {
	public void getloan(LoanVO vo);
	public List<LoanVO> getList(String loan_name);
	public void borrow(LoanVO vo);
	public LoanVO getBalanceByName(String loan_name);
	
}
