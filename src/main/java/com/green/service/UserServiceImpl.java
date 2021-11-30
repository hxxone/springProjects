package com.green.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.mapper.ProductMapper;
import com.green.mapper.UserMapper;
import com.green.vo.ProductVO;
import com.green.vo.UserVO;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService{
	
	@Setter(onMethod_=@Autowired)
	UserMapper mapper;
	
	@Setter(onMethod_=@Autowired)
	ProductMapper pmapper;
	

	@Override
	public int signUp(UserVO user) {
		
		return mapper.signUp(user);
	}

	@Override
	public List<UserVO> getAllUser() {
		
		return mapper.getAllUser();
	}

	@Override
	public UserVO getUser(Long user_id) {
		
		return mapper.getUser(user_id);
	}

	@Override
	public List<ProductVO> getAllProduct() {
		
		return pmapper.getAllList();
	}

	

}
