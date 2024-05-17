package com.thunder.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thunder.model.dao.UserScheduleDao;

@Service
public class UserScheduleServiceImpl implements UserScheduleService {

	private final UserScheduleDao userScheduleDao;
	
	@Autowired
	public UserScheduleServiceImpl(UserScheduleDao userScheduleDao) {
		this.userScheduleDao = userScheduleDao;
	}
	
	@Override
	@Transactional
	public boolean validateJoin(String userId, int scheduleId) {
		Map<String, Object> params = new HashMap<>();
		
		params.put("userId", userId);
		params.put("scheduleId", scheduleId);
		
		return (userScheduleDao.validate(params) >= 1 ? true : false);
	}

	@Override
	@Transactional
	public void addManager(String userId, int scheduleId) {
		Map<String, Object> params = new HashMap<>();
		
		params.put("userId", userId);
		params.put("scheduleId", scheduleId);
		
		userScheduleDao.insertManager(params);
	}

}
