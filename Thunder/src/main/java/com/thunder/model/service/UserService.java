package com.thunder.model.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.thunder.model.dto.User;

public interface UserService {
	
	// 회원가입
	int registUser(User user, MultipartFile file);
	
	// 로그인
	User login(String id, String password);
	
	// 유저 아이디로 유저 정보 조회
	User getUserById(String userId);
	
}
