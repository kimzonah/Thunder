package com.thunder.model.dao;

import java.util.List;
import java.util.Map;

import com.thunder.model.dto.Schedule;
import com.thunder.model.dto.ScheduleSearchCondtion;

public interface ScheduleDao {

	List<Schedule> selectScheduleByCon(Map<String, String> condition);

}
