package com.green.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.mapper.LoanMapper;
import com.green.vo.LoanVO;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class LoanServiceImpl implements LoanService{

	@Setter(onMethod_=@Autowired)
	LoanMapper mapper;
	
	@Override
	public void getloan(LoanVO vo) {
		vo.setLoan_balance(vo.getLoan_total());
		vo.setLoan_fee(0f);
		mapper.getloan(vo);

	}

	@Override
	public List<LoanVO> getList(String loan_name) {
		
		return mapper.getList(loan_name);
	}

	@Override
	public void borrow(LoanVO vo) {
		LoanVO loan = mapper.getBalanceByName(vo.getLoan_name());
		
		Float balance = loan.getLoan_balance()-vo.getLoan_fee();
		vo.setLoan_balance(balance);
		vo.setLoan_total(loan.getLoan_total());
		log.info("=================================================="+vo);
		mapper.borrow(vo);

	}

	@Override
	public LoanVO getBalanceByName(String loan_name) {
		
		return mapper.getBalanceByName(loan_name);
	}

}
