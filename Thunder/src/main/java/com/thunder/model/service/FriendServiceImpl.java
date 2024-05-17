package com.thunder.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thunder.model.dao.FriendDao;
import com.thunder.model.dto.User;

@Service
public class FriendServiceImpl implements FriendService {
	
	private final FriendDao friendDao;
	
	@Autowired
	public FriendServiceImpl (FriendDao friendDao) {
		this.friendDao = friendDao;
	}
	
	// 전체 유저들 중 이름으로 유저 검색
	@Override
	public List<User> searchUser(String searchName) {
		return friendDao.selectUserByName(searchName);
	}

}
