package com.thunder.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
	@PostMapping(value = "/signup", consumes = { "multipart/form-data" })
	public ResponseEntity<Void> doSignup(@ModelAttribute User user,
			@RequestPart(name = "file", required = false) MultipartFile file) {

		// 이미 존재하는 아이디라면 400 응답
		if (userService.getUserById(user.getId()) != null) {
			System.out.println("이미 존재하는 아이디입니다.");
			return ResponseEntity.badRequest().build();
		}

		int result = userService.registUser(user, file);

		// 유저 등록이 되지 않았을 때 회원가입 실패(400)
		if (result == 0) {
			return ResponseEntity.badRequest().build(); // 회원가입 실패 (400)
		}

		return ResponseEntity.ok().build(); // 회원가입 성공
	}

	// 로그인
	@Operation(summary = "로그인")
	@PostMapping("/login")
	public ResponseEntity<Void> doLogin(@RequestBody User user, HttpSession session) {
		// 로그인 시도
		User loginUser = userService.login(user.getId(), user.getPassword());
		
		// 아이디와 비밀번호가 일치하는 유저가 없다면 로그인 실패 400
		if (loginUser == null) {
			return ResponseEntity.badRequest().build();
		}
		
		// 정상 요청일시 로그인 성공
		session.setAttribute("loginUser", loginUser);
		return ResponseEntity.ok().build();
	}

	// 로그아웃
	@Operation(summary = "로그아웃")
	@PostMapping("/logout")
	public ResponseEntity<Void> doLogout(HttpSession session) {
		User logoutUser = (User) session.getAttribute("loginUser");
		
		// 세션에 로그아웃할 유저가 없으면 404응답
		if (logoutUser == null) {
			return ResponseEntity.badRequest().build();
		}
		
		// 정상 요청일시 세션 만료 후 로그아웃 성공
		session.invalidate();
		return ResponseEntity.ok().build();
	}

	// 로그인 유저 정보 조회
	@Operation(summary = "로그인 유저 정보 조회")
	@GetMapping("/loginUser")
	public ResponseEntity<?> getLoginUser(HttpSession session) {
		User loginUser = (User) session.getAttribute("loginUser");
		
		// 로그인 한 유저가 없으면 404응답
		if (loginUser == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(loginUser);
	}

	// 유저 아이디로 유저 조회
	@Operation(summary = "유저 아이디로 유저 정보 조회")
	@GetMapping("/{userId}")
	public ResponseEntity<?> getBoardUserId(@PathVariable("userId") String userId) {
		User user = userService.getUserById(userId);
		
		// 해당 아이디를 갖는 유저가 없으면 404 응답
		if (user == null)
			return ResponseEntity.notFound().build();
			
		return ResponseEntity.ok(user);
	}
}
