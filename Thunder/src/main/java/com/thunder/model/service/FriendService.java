package com.thunder.model.service;

import java.util.List;

import com.thunder.model.dto.Schedule;
import com.thunder.model.dto.User;

public interface FriendService {
	
	// 전체 유저 조회
	List<User> getAllUsers();
	
	// 전체 유저 중 이름으로 검색
	List<User> searchUser(String searchName);
	
	// 친구 맺기
	int addFriend(String friendId, String loginUserId);
	
	// 친구 삭제
	int deleteFriend(String friendId, String loginUserId);
	
	// 받은 친구 요청 조회
	List<User> getAllRequest(String loginUserId);
	
	// 친구 요청 수락
	int acceptRequest(String friendId, String loginUserId);
	
	// 친구 요청 거절
	int rejectRequest(String friendId, String loginUserId);
	
	// 친구가 관리하는 번개 조회
	List<Schedule> getFriendSchedule(String friendId);
	
	// 친구 전체 조회
	List<User> getAllFriend(String loginUserId);
	
	// 검색으로 친구 조회
	List<User> searchFriend(String loginUserId, String searchName);
	


}
