package com.green.service;

import java.util.List;

import com.green.vo.ProductVO;
import com.green.vo.UserVO;

import lombok.Getter;

public interface UserService {
	public int signUp(UserVO user);
	public List<UserVO> getAllUser();
	public UserVO getUser(Long user_id);
	public List<ProductVO>getAllProduct();
}
