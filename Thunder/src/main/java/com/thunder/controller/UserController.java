package com.thunder.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.thunder.model.dto.User;
import com.thunder.model.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin("http://localhost:5173")
@RequestMapping("/thunder/user")
@Tag(name = "UserController", description = "유저 관리 및 조회")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	// 회원가입
	@Operation(summary = "회원가입")
	@PostMapping(value = "/signup")
	public ResponseEntity<Void> doSignup(@ModelAttribute User user,
			@RequestPart(name = "file", required = false) MultipartFile file) {
		int result = userService.registUser(user, file);

		if (result == 1) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}

	// 로그인
	@Operation(summary = "로그인")
	@PostMapping("/login")
	public ResponseEntity<Void> doLogin(@RequestBody User user, HttpSession session) {
		// 로그인 시도
		User loginUser = userService.login(user.getId(), user.getPassword());

		if (loginUser != null) {
			session.setAttribute("loginUser", loginUser);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}

	// 로그아웃
	@Operation(summary = "로그아웃")
	@PostMapping("/logout")
	public ResponseEntity<Void> doLogout(HttpSession session) {
		User logoutUser = (User) session.getAttribute("loginUser");
		if (logoutUser != null) {
			session.invalidate();
			return new ResponseEntity<Void>(HttpStatus.OK);
		}

		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}

	// 로그인 유저 정보 조회
	@Operation(summary = "로그인 유저 정보 조회")
	@GetMapping("/loginUser")
	public ResponseEntity<?> getLoginUser(HttpSession session) {
		User loginUser = (User) session.getAttribute("loginUser");
		if (loginUser != null) {
			return new ResponseEntity<User>(loginUser, HttpStatus.OK);
		}

		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
}
