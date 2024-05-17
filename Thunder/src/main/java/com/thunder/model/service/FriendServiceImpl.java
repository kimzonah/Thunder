package com.thunder.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thunder.model.dao.FriendDao;
import com.thunder.model.dto.Schedule;
import com.thunder.model.dto.User;

@Service
public class FriendServiceImpl implements FriendService {
	
	private final FriendDao friendDao;
	
	@Autowired
	public FriendServiceImpl (FriendDao friendDao) {
		this.friendDao = friendDao;
	}
	
	// 전체 유저 조회
	@Override
	public List<User> getAllUsers() {
		return friendDao.selectAllUsers();
	}
	
	
	// 전체 유저들 중 이름으로 유저 검색
	@Override
	public List<User> searchUser(String searchName) {
		return friendDao.selectUserByName(searchName);
	}
	
	// 친구 맺기
	@Override
	public int addFriend(String friendId, String loginUserId) {
		Map<String,String> friendReq = new HashMap<>();
		friendReq.put("fromUserId", loginUserId); // 친구 요청 보낸 사람(로그인 유저)
		friendReq.put("toUserId", friendId); // 친구 요청 받은 사람
		
		return friendDao.insertFriend(friendReq);
	}
	
	// 친구 삭제
	@Override
	public int deleteFriend(String friendId, String loginUserId) {
		Map<String,String> friendReq = new HashMap<>();
		friendReq.put("loginUserId", loginUserId); 
		friendReq.put("friendId", friendId);
		
		return friendDao.deleteFriend(friendReq);
	}
	
	// 친구 요청 조회
	@Override
	public List<User> getAllRequest(String loginUserId) {
		return friendDao.selectAllRequest(loginUserId);
	}
	
	// 친구 요청 수락
	@Override
	public int acceptRequest(String friendId, String loginUserId) {
		Map<String,String> friendReq = new HashMap<>();
		friendReq.put("loginUserId", loginUserId); 
		friendReq.put("friendId", friendId);
		
		return friendDao.updateStatus(friendReq);
	}
	
	// 친구 요청 거절
	@Override
	public int rejectRequest(String friendId, String loginUserId) {
		Map<String,String> friendReq = new HashMap<>();
		friendReq.put("loginUserId", loginUserId); 
		friendReq.put("friendId", friendId);
		
		return friendDao.deleteRequest(friendReq);
	}
	
	// 친구가 관리하는 번개 조회
	@Override
	public List<Schedule> getFriendSchedule(String friendId) {
		return friendDao.selectFriendSchedule(friendId);
	}
	


}
