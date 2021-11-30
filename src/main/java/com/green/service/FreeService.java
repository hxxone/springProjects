package com.green.service;

import java.util.List;

import com.green.vo.FreeVO;

public interface FreeService {
	public int depositAndWithdrawal(FreeVO vo);
	public int signUp(FreeVO vo);
	public void login(FreeVO vo);
	public List<FreeVO> bankBook(String free_name);
	public List<FreeVO> getNameList();
	public void getSerial(FreeVO vo);
	public List<FreeVO> balancePerSerial(String free_name);
	public List<FreeVO> selectAll();
	public List<FreeVO> getSerialByName(String free_name);
	public List<FreeVO> getListBySerial(String free_serial);
}
