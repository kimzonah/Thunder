package com.thunder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thunder.model.dto.User;
import com.thunder.model.service.UserService;

@RestController
@RequestMapping("/thunder/user")
public class UserController {
	
	private final UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	//회원가입
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody User user){
		int result = userService.registUser(user);
		return null;
	}
	
}
