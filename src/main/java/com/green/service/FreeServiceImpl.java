package com.green.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.mapper.FreeMapper;
import com.green.vo.FreeVO;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import oracle.net.aso.j;

@Service
@Slf4j
public class FreeServiceImpl implements FreeService {
	
	@Setter(onMethod_=@Autowired)
	FreeMapper mapper;

	@Override
	public int depositAndWithdrawal(FreeVO vo) {
		
		if(mapper.getListBySerial(vo.getFree_serial()).size()>1) {
			float balance = mapper.getBalance(vo.getFree_serial()).getFree_balance();
			vo.setFree_balance((balance + vo.getFree_deposit() -vo.getFree_withdrawal()));
		}else {
			vo.setFree_balance(vo.getFree_deposit()-vo.getFree_withdrawal());
		}
		int result = mapper.depositAndWithdrawal(vo);
		mapper.loan(vo);
		return result;
		
	}

	@Override
	public void login(FreeVO vo) {
		
	}


  
	@Override
	public List<FreeVO> getNameList() {
		// TODO Auto-generated method stub
		return mapper.getNameList();
	}

	@Override
	public void getSerial(FreeVO vo) {
		String serial ="";
		do {
			for(int i =0; i<10; i++) {
			if(serial.length()==5) serial+="-";
			serial += (int)(Math.random()*10);
			}
		}while(mapper.getListBySerial(serial).size()==0);
		
		vo.setFree_serial(serial);
		
	}

	@Override
	public List<FreeVO> bankBook(String free_name) {
		// TODO Auto-generated method stub
		return mapper.bankBook(free_name);
	}

	@Override
	public List<FreeVO> selectAll() {
		// TODO Auto-generated method stub
		return mapper.selectAll();
	}

	@Override
	public List<FreeVO> getSerialByName(String free_name) {
		
		return mapper.getSerialByName(free_name);
	}

	@Override
	public List<FreeVO> getListBySerial(String free_serial) {
		
		return mapper.getListBySerial(free_serial);
	}

	@Override
	public List<FreeVO> balancePerSerial(String free_name) {
		// TODO Auto-generated method stub
		return mapper.balancePerSerial(free_name);
	}

	@Override
	public int signUp(FreeVO vo) {
		String serial ="";
		do {
			for(int i =0; i<10; i++) {
			if(serial.length()==5) serial+="-";
			serial += (int)(Math.random()*10);
			}
		}while(mapper.getListBySerial(serial).size()>0);
		vo.setFree_serial(serial);
		return mapper.signUp(vo);
		
	}
		 
	

	

	
}
