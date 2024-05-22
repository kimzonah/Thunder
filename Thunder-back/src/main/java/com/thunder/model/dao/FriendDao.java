package com.thunder.model.dao;

import java.util.List;
import java.util.Map;

import com.thunder.model.dto.Schedule;
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
	
	// 친구 요청 조회
	List<User> selectAllRequest(String loginUserId);
	
	// 친구 요청 수락
	int updateStatus(Map<String, String> friendReq);
	
	// 친구 요청 삭제. 거절
	int deleteRequest(Map<String, String> friendReq);
	
	// 친구가 관리하는 번개 조회
	List<Schedule> selectFriendSchedule(String friendId);
	
	// 친구 전체 조회
	List<User> selectAllFriend(String loginUserId);
	
	// 검색어로 친구 검색 조회
	List<User> selectFriendByName(Map<String, String> searchCon);
	
	// 친구 테이블에 없는 관계인지 확인
	int selectNoFriend(Map<String, String> map);
	
	// 친구인지 확인
	int selectFriend(Map<String, String> map);
	
	// 로그인 유저가 from 친구가 to status는 0
	int selectWait(Map<String, String> map);

	// 두 유저간 요청이 이미 있거나 이미 친구인 경우
	int validate(Map<String, String> map);

}