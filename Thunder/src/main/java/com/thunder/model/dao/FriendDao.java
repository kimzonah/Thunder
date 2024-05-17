package com.thunder.model.dao;

import java.util.List;

import com.thunder.model.dto.User;

public interface FriendDao {
	
	// 전체 유저 조회
	List<User> selectAllUsers();
	
	// 전체 유저에서 검색 조회
	List<User> selectUserByName(String searchName);


}
