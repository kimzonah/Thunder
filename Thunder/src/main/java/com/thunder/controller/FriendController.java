package com.thunder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thunder.model.dto.Schedule;
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
	public ResponseEntity<List<User>> getUserList(@RequestParam(required = false) String searchName) {

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
	
	// 내 친구 조회 및 검색
	@Operation(summary = "내 친구 조회 및 검색")
	@GetMapping("/my/search")
	public ResponseEntity<List<User>> getMyFreind(@RequestParam(required = false) String searchName, HttpSession session) {
		
		User loginUser = (User) session.getAttribute("loginUser");
        String loginUserId = loginUser.getId();
        
		List<User> list;

		// 만약 검색어가 없다면 전체 조회
		if (searchName == null || searchName.isEmpty()) {
			list = friendService.getAllFriend(loginUserId);
		}
		// 검색어가 있다면 검색 조회
		else {
			list = friendService.searchFriend(loginUserId, searchName);
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
	
	// 친구 요청 조회
	@Operation(summary = "친구 요청 조회")
	@GetMapping("/request")
	public ResponseEntity<List<User>> getRequestList(HttpSession session){
		
		User loginUser = (User) session.getAttribute("loginUser");
        String loginUserId = loginUser.getId();
        
        List<User> list = friendService.getAllRequest(loginUserId);
        
        //친구 요청이 없을 떄
        if(list.size()==0) {
        	return ResponseEntity.noContent().build();
        }
        // 친구 요청이 있을 때
        return ResponseEntity.ok(list);
		
	}
	
	// 친구 요청 수락하기
    @Operation(summary = "친구 수락하기")
    @PutMapping("/request/{friendId}")
    public ResponseEntity<Void> acceptRequest(@PathVariable("friendId") String friendId, HttpSession session){
        
        User loginUser = (User) session.getAttribute("loginUser");
        String loginUserId = loginUser.getId();
        
        int result = friendService.acceptRequest(friendId, loginUserId);
        // 친구 수락 실패
        if(result==0) {
            return ResponseEntity.badRequest().build();
        }
        // 친구 수락 성공
        return ResponseEntity.ok().build();
    }
    
    // 친구 요청 거절하기
    @Operation(summary = "친구 거절하기, 요청 삭제")
    @DeleteMapping("/request/{friendId}")
    public ResponseEntity<Void> rejectRequest(@PathVariable("friendId") String friendId, HttpSession session){
    	User loginUser = (User) session.getAttribute("loginUser");
        String loginUserId = loginUser.getId();
        
        int result = friendService.rejectRequest(friendId, loginUserId);
        // 친구 거절 실패
        if(result==0) {
            return ResponseEntity.badRequest().build();
        }
        // 친구 거절 성공
        return ResponseEntity.ok().build();
    }
    
    //친구 번개 조회
    @Operation(summary = "친구가 관리하는 번개 조회")
    @GetMapping("/{friendId}")
    public ResponseEntity<List<Schedule>> getFriendSchedule(@PathVariable("friendId") String friendId){
    	List<Schedule> list = friendService.getFriendSchedule(friendId);
    	// 조회 결과가 없다면
    	if(list.size()==0) {
    		return ResponseEntity.noContent().build();
    	}
    	// 조회 결과가 있다면
    	return ResponseEntity.ok(list);
    }
	
    // 친구 관계 현재 상태
    @Operation(summary = "현재 친구 관계 상태 반환(친구 아니고 요청없음:0 / 친구:1 / 로그인유저가 요청 보내고 승인대기중:2 / 로그인유저가 요청 받음:3)")
    @GetMapping("/request/status/{friendId}")
    public ResponseEntity<Integer> getStatus(@PathVariable("friendId") String friendId, HttpSession session){
		
    	User loginUser = (User) session.getAttribute("loginUser");
        String loginUserId = loginUser.getId();
    	
    	// 0 상태인지
    	if(friendService.haveRelation(loginUser, friendId)) {
    		
    	}
    	
    	return null;
    	
    }
}
