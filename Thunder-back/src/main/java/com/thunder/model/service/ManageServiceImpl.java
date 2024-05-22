package com.thunder.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thunder.model.dao.ManageDao;
import com.thunder.model.dto.Schedule;
import com.thunder.model.dto.User;

@Service
public class ManageServiceImpl implements ManageService {

	private final ManageDao manageDao;
	
	@Autowired
	public ManageServiceImpl(ManageDao manageDao) {
		this.manageDao = manageDao;
	}
	
	@Override
	@Transactional
	public boolean validateManager(String userId, int scheduleId) {
		Map<String, Object> params = new HashMap<>();
		
		params.put("userId", userId);
		params.put("scheduleId", scheduleId);
		
		return manageDao.validate(params) >= 1 ? true : false;
	}

	@Override
	@Transactional
	public List<User> getAllApplyUser(int scheduleId) {
		return manageDao.selectAllApplyUser(scheduleId);
	}

	@Override
	@Transactional
	public boolean updateStatus(int scheduleId, String userId) {
		Map<String, Object> params = new HashMap<>();
		
		params.put("userId", userId);
		params.put("scheduleId", scheduleId);
		
		return manageDao.update(params) == 1 ? true : false;
	}

	@Override
	@Transactional
	public boolean deleteApply(int scheduleId, String userId) {
		Map<String, Object> params = new HashMap<>();
		
		params.put("userId", userId);
		params.put("scheduleId", scheduleId);
		
		return manageDao.delete(params) == 1 ? true : false;
	}

	@Override
	@Transactional
	public boolean updateSchedule(int scheduleId, Schedule schedule) {
		schedule.setId(scheduleId);
		
		return manageDao.updateSchedule(schedule) == 1 ? true : false;
	}

}
