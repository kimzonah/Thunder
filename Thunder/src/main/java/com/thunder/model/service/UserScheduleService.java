package com.thunder.model.service;

public interface UserScheduleService {

	boolean validateJoin(String userId, int scheduleId);

	void addManager(String userId, int scheduleId);

	boolean deleteSchedule(String userId, int scheduleId);

}
