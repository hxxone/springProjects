package com.green.mapper;

import java.util.List;

import com.green.vo.LoanVO;

public interface LoanMapper {
	
	public void getloan(LoanVO vo);
	public void borrow(LoanVO vo);
	public List<LoanVO> getList(String loan_name);
	public LoanVO getBalanceByName(String loan_name);

}
