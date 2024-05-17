package com.thunder.model.service;

import java.util.List;

import com.thunder.model.dto.User;

public interface FriendService {

	List<User> searchUser(String searchName);

}
