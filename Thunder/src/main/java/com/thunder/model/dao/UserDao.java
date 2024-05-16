package com.thunder.model.dao;

import java.util.Map;

import com.thunder.model.dto.User;

public interface UserDao {
		
	//회원가입
	int insertUser(User user);
	
	//로그인
	User login(Map<String, String> user);
	
	// 유저 아이디로 유저 정보 불러오기
	User selectUserById(String userId);	

}
