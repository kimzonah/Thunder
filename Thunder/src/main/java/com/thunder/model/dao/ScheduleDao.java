package com.thunder.model.dao;

import java.util.List;
import java.util.Map;

import com.thunder.model.dto.Schedule;
import com.thunder.model.dto.ScheduleSearchCondtion;

public interface ScheduleDao {
	
	// 번개 조회 및 검색
    List<Schedule> selectScheduleByCon(Map<String, String> condition);
	
	// 번개 상세 조회
	Schedule selectOneSchedule(int scheduleId);

}
