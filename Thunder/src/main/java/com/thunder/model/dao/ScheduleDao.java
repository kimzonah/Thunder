package com.thunder.model.dao;

import java.util.List;

import com.thunder.model.dto.Schedule;
import com.thunder.model.dto.ScheduleSearchCondtion;

public interface ScheduleDao {

	List<Schedule> selectScheduleByCon(ScheduleSearchCondtion condition);

}
