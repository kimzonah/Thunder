package com.thunder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thunder.model.dto.User;
import com.thunder.model.service.FriendService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;

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
	public ResponseEntity<?> getUserList(@RequestParam(required = false) String searchName) {

		List<User> list;

		// 만약 검색어가 없다면 전체 조회
		if (searchName == null || searchName.isEmpty()) {
			list = friendService.getAllUsers();
		}
		// 검색어가 있다면 검색 조회
		else {
			list = friendService.searchUser(searchName);
		}

		// 조회된 결과가 0개일때 not found
		if (list.size() == 0) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(list);
	}
	
	// 친구 맺기
	@Operation(summary = "친구 맺기 요청")
	@PostMapping("/{friendId}")
	public ResponseEntity<Void> addFriend(@PathVariable("friendId") String friendId, HttpSession session){
		
		User loginUser = (User) session.getAttribute("loginUser");
        String loginUserId = loginUser.getId();
		
		int result = friendService.addFriend(friendId, loginUserId);
		// 친구 맺기 요청 실패시
		if(result==0) {
			return ResponseEntity.badRequest().build();
		}
		// 친구 맺기 요청 성공
		return ResponseEntity.ok().build();
		
	}
	
	// 친구 끊기
	@Operation(summary = "친구 끊기")
	@DeleteMapping("/{friendId}")
	public ResponseEntity<Void> deleteFriend(@PathVariable("friendId") String friendId, HttpSession session){
		
		User loginUser = (User) session.getAttribute("loginUser");
        String loginUserId = loginUser.getId();
		
        int result = friendService.deleteFriend(friendId, loginUserId);
        // 친구 삭제 실패
        if(result==0) {
        	return ResponseEntity.badRequest().build();
        }
        // 친구 삭제 성공
        return ResponseEntity.ok().build();
	}
	
}
