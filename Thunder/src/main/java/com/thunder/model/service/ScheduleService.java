package com.thunder.model.service;

import java.util.List;

import com.thunder.model.dto.Schedule;
import com.thunder.model.dto.ScheduleSearchCondtion;

public interface ScheduleService {
	
	// 스케주
	List<Schedule> getScheduleList(ScheduleSearchCondtion condition);
	
}
