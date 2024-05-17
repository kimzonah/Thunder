package com.thunder.model.dao;

import java.util.Map;

public interface UserScheduleDao {

	int validate(Map<String, Object> params);

	void insertManager(Map<String, Object> params);

}
