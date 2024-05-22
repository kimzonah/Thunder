package com.thunder.model.dao;

import java.util.List;
import java.util.Map;

import com.thunder.model.dto.Schedule;
import com.thunder.model.dto.User;

public interface ManageDao {

	int validate(Map<String, Object> params);

	List<User> selectAllApplyUser(int scheduleId);

	int update(Map<String, Object> params);

	int delete(Map<String, Object> params);

	int updateSchedule(Schedule schedule);

}