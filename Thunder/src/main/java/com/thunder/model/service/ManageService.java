package com.thunder.model.service;

import java.util.List;

import com.thunder.model.dto.Schedule;
import com.thunder.model.dto.User;

public interface ManageService {

	boolean validateManager(String userId, int scheduleId);

	List<User> getAllApplyUser(int scheduleId);

	boolean updateStatus(int scheduleId, String userId);

	boolean deleteApply(int scheduleId, String userId);

	boolean updateSchedule(int scheduleId, Schedule schedule);

}
