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
	
//	// 전체 유저 조회
//	@Operation(summary = "전체 유저 조회")
//	@GetMapping("/all")
//	public ResponseEntity<List<User>> getAllUserList(){
//		List<User> list = friendService.getAllUsers();
//		return ResponseEntity.status(list)
//	}

	// 전체 유저 조회 및 검색
	@Operation(summary = "전체 검색")
	@GetMapping("/search")
	public ResponseEntity<List<User>> getUserList(@RequestParam(name = "searchName", required = false) String searchName, HttpSession session) {
		String userId = (String) session.getAttribute("loginUser");
		
		List<User> list;

		// 만약 검색어가 없다면 전체 조회
		if (searchName == null || searchName.isEmpty()) {
			list = friendService.getAllUsers();
		}
		// 검색어가 있다면 검색 조회
		else {
			list = friendService.searchUser(searchName);
		}
		
		System.out.println("Search Name :" + searchName); // 검색어 디버깅
	    System.out.println("User List :" + list); // 결과 리스트 디버깅

		// 조회된 결과가 0개일때 noContent
		if (list.size() == 0) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(list);
	}
	
	// 내 친구 조회 및 검색
	@Operation(summary = "내 친구 조회 및 검색")
	@GetMapping("/my/search")
	public ResponseEntity<List<User>> getMyFreind(@RequestParam(name = "searchName", required = false) String searchName, HttpSession session) {
		
		String userId = (String) session.getAttribute("loginUser");
        
		List<User> list;

		// 만약 검색어가 없다면 전체 조회
		if (searchName == null || searchName.isEmpty()) {
			list = friendService.getAllFriend(userId);
		}
		// 검색어가 있다면 검색 조회
		else {
			list = friendService.searchFriend(userId, searchName);
		}
		
		System.out.println("MySearch :" +searchName);
		System.out.println("MyFriend :"+list);

		// 조회된 결과가 0개일때 noContent
		if (list.size() == 0) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(list);
	}
	
	// 친구 맺기
	@Operation(summary = "친구 맺기 요청")
	@PostMapping("/{friendId}")
	public ResponseEntity<Void> addFriend(@PathVariable("friendId") String friendId, HttpSession session){
		
		String userId = (String) session.getAttribute("loginUser");
		
		// 친구 요청을 이미 보냈는지 검증
		if (friendService.validateRequest(userId, friendId)) {
			return ResponseEntity.badRequest().build(); // 이미 요청을 보낸 유저라면 badRequest (400)
		}
		
		int result = friendService.addFriend(friendId, userId);
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
		
		String userId = (String) session.getAttribute("loginUser");
		
        int result = friendService.deleteFriend(friendId, userId);
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
		
		String userId = (String) session.getAttribute("loginUser");
        
        List<User> list = friendService.getAllRequest(userId);
        
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
        
    	String userId = (String) session.getAttribute("loginUser");
        
        int result = friendService.acceptRequest(friendId, userId);
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
    	String userId = (String) session.getAttribute("loginUser");
        
        int result = friendService.rejectRequest(friendId, userId);
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
    public ResponseEntity<Integer> checkStatus(@PathVariable("friendId") String friendId, HttpSession session){
		
    	String userId = (String) session.getAttribute("loginUser");
//    	System.out.println(userId);
    	// 친구 관계가 없으면 0 반환
    	if(friendService.noRelation(userId, friendId)) {
    		return ResponseEntity.ok(0);
    	}
    	
    	// 친구 상태면 1반환
    	if(friendService.isFriend(userId, friendId)) {
    		return ResponseEntity.ok(1);
    	}
    	
    	// 로그인 유저가 요청을 보내놓은 친구라면 2 반환
    	if(friendService.waitingAccept(userId, friendId)) {
    		return ResponseEntity.ok(2);
    	}
    	
    	// 로그인 유저가 요청을 받은 경우 3 반환
    	return ResponseEntity.ok(3);
    	
    }
}
