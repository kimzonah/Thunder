package com.thunder.model.dao;

import java.util.List;

import com.thunder.model.dto.User;

public interface FriendDao {

	List<User> selectUserByName(String searchName);

}
