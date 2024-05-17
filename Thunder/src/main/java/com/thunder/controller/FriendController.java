package com.thunder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thunder.model.dto.User;
import com.thunder.model.service.FriendService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin("http://localhost:5173")
@RequestMapping("/friend")
@Tag(name = "FriendController", description = "친구 관리 CRUD")
public class FriendController {
	
	private final FriendService friendService;
	
	@Autowired
	public FriendController(FriendService friendService) {
		this.friendService = friendService;
	}
	
	// 전체 유저 조회 및 검색
	@Operation(summary = "전체 유저 조회 및 검색")
	@GetMapping("/search")
	public ResponseEntity<?> searchUser(String searchName){
		List<User> list = friendService.searchUser(searchName);
		return ResponseEntity.ok(list);
	}
	
}
