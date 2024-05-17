package com.thunder.model.dao;

import java.util.Map;

public interface UserScheduleDao {
	
	// 번개 참여자 검증
	int validate(Map<String, Object> params);

	// 번개장 번개 참여자로 추가
	void insertManager(Map<String, Object> params);

	// 번개 나가기
	int deleteSchedule(Map<String, Object> params);

	// 번개 참여 인원 조회
	int selectJoinUserCnt(int scheduleId);
}
