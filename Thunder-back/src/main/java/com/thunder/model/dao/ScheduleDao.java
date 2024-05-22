package com.thunder.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Options;

import com.thunder.model.dto.Schedule;

public interface ScheduleDao {
	
	// 번개 조회 및 검색
    List<Schedule> selectScheduleByCon(Map<String, String> condition);
	
	// 번개 상세 조회
	Schedule selectOneSchedule(int scheduleId);
	
	// 번개 등록
	int insertSchedule(Schedule schedule);
	
	// 번개 참여 신청
	int insertUserSchedule(Map newApply);

	// 유저 아이디로 예정된 번개 조회
	List<Schedule> selectRemainSchedule(String userId);

	// 유저 아이디로 지난 번개 조회
	List<Schedule> selectPastSchedule(String userId);
	
	// 신청한 번개 조회
	int selectApply(Map<String, Object> map);
	
	// 전체 조회
	List<Schedule> selectAllSchedule();

}