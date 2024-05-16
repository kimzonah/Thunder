package com.thunder.model.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.thunder.model.dto.User;

public interface UserService {
	
	// 회원가입
	int registUser(User user, MultipartFile file);
	
}
