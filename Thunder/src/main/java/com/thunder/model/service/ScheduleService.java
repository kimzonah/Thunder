package com.thunder.model.service;

import java.sql.Timestamp;
import java.util.List;

import com.thunder.model.dto.Schedule;
import com.thunder.model.dto.ScheduleSearchCondtion;

public interface ScheduleService {
	
	// 검색 조건으로 번개 조회
    List<Schedule> getScheduleList(String dateTime, String categoty, String addressName);
	
	// 번개 아이디로 번개 상세 조회
	Schedule getOneSchedule(int scheduleId);
	
}
