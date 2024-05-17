package com.thunder.model.service;

public interface UserScheduleService {

	// 번개 참여자 검증
	boolean validateJoin(String userId, int scheduleId);

	// 번개장 번개 참여자로 추가
	void addManager(String userId, int scheduleId);

	// 번개 나가기
	boolean deleteSchedule(String userId, int scheduleId);

	// 번개 참여 인원 조회
	int getJoinUserCnt(int scheduleId);

}
