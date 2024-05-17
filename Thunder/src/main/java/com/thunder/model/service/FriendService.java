package com.thunder.model.service;

import java.util.List;

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
	


}
