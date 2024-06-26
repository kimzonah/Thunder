package com.thunder.model.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.thunder.model.dto.Schedule;
import com.thunder.model.dto.ScheduleSearchCondtion;

public interface ScheduleService {
	
	// 검색 조건으로 번개 조회
    List<Schedule> getScheduleList(String dateTime, String categoty, String addressName);
	
	// 번개 아이디로 번개 상세 조회
	Schedule getOneSchedule(int scheduleId);
	
	// 번개 생성
	int createSchedule(Schedule schedule, MultipartFile file);
	
	// 번개 참여 요청 보냄
	int sendJoin(String loginUserId, int scheduleId);

	// 유저 아이디로 예정된 번개 조회
	List<Schedule> getRemainSchedule(String userId);

	// 유저 아이디로 지난 번개 조회
	List<Schedule> getPastSchedule(String userId);
	
	// 로그인 유저가 신청한적 없는지 조회
	boolean noApply(String userId, int scheduleId);
	
	// 전체 조회
	List<Schedule> getAllSchedule();

}
