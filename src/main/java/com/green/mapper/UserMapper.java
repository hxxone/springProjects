package com.green.mapper;

import java.util.List;

import com.green.vo.UserVO;

public interface UserMapper {
	
	public int signUp(UserVO user);
	public List<UserVO> getAllUser();
	public UserVO getUser(Long user_Id);
}
