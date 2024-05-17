package com.thunder.model.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thunder.model.dao.ScheduleDao;
import com.thunder.model.dto.Schedule;
import com.thunder.model.dto.ScheduleSearchCondtion;

@Service
public class ScheduleServiceImpl implements ScheduleService {
	
	private final ScheduleDao scheduleDao;
	
	@Autowired
	public ScheduleServiceImpl(ScheduleDao scheduleDao) {
		this.scheduleDao = scheduleDao;
	}
	
	// 번개 상세 조회
	@Override
	public Schedule getOneSchedule(int scheduleId) {
		return scheduleDao.selectOneSchedule(scheduleId);
	}
	
	
	
}
