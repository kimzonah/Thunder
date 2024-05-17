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
	
	@Override
    public List<Schedule> getScheduleList(String dateTime, String category, String addressName) {
        Map<String, String> condition = new HashMap<>();
        
        condition.put("dateTime", dateTime);
        condition.put("category",category);
        condition.put("addressName", addressName);
        
        return scheduleDao.selectScheduleByCon(condition);
    }
	
	// 번개 상세 조회
	@Override
	public Schedule getOneSchedule(int scheduleId) {
		return scheduleDao.selectOneSchedule(scheduleId);
	}
	
	
	
}
