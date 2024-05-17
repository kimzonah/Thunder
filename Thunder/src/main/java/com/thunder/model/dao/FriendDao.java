package com.thunder.model.dao;

import java.util.List;
import java.util.Map;

import com.thunder.model.dto.User;

public interface FriendDao {
	
	// 전체 유저 조회
	List<User> selectAllUsers();
	
	// 전체 유저에서 검색 조회
	List<User> selectUserByName(String searchName);
	
	// 친구 맺기 : 친구 요청 생성
	int insertFriend(Map<String, String> friendReq);
	
	// 친구 끊기 : 친구 삭제
	int deleteFriend(Map<String, String> friendReq);
	
	

}
