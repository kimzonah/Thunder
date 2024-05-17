package com.thunder.model.service;

import java.util.List;

import com.thunder.model.dto.Schedule;
import com.thunder.model.dto.ScheduleSearchCondtion;

public interface ScheduleService {
	
	// 검색 조건으로 번개 조회
	List<Schedule> getScheduleList(ScheduleSearchCondtion condition);
	
}
