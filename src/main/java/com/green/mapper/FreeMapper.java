package com.green.mapper;

import java.util.List;

import com.green.vo.FreeVO;

public interface FreeMapper {
	
	public int depositAndWithdrawal(FreeVO vo);
	public int signUp(FreeVO vo);
	public List<FreeVO> bankBook(String free_name);
	public List<FreeVO> getNameList();
	public void getSerial(FreeVO vo);
	
	public List<FreeVO> selectAll();
	public List<FreeVO> getSerialByName(String free_name);
	public List<FreeVO> getListBySerial(String free_serial);
	
	public FreeVO getBalance(String free_serial);
	
	
//	public void depositAndWithdrawal2(FreeVO vo);
//	public void withdrwal(FreeVO vo);
//	public int updateBalance(FreeVO vo);
	public int loan(FreeVO vo);
	public FreeVO checkAccount(Long free_no);
//	public List<FreeVO> getHistory(String free_serial);
	public Long getLatestNo(String free_serial);

	
	public void test(FreeVO vo);
	
	public List<FreeVO> balancePerSerial(String free_name);
}
