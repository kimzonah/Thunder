package com.thunder.model.service;

import java.util.List;

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
	
	@Override
	public List<Schedule> getScheduleList(ScheduleSearchCondtion condition) {
		return scheduleDao.selectScheduleByCon(condition);
	}
	
	
	
}
