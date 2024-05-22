package com.thunder.model.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.thunder.model.dao.ScheduleDao;
import com.thunder.model.dto.Schedule;

@Service
public class ScheduleServiceImpl implements ScheduleService {

	@Value("${file.desktop-thunder-dir}")
	private String desktopDir;
	
	private final ScheduleDao scheduleDao;
	private final ResourceLoader resourceLoader;
	private static final String DEFALT_SCHEDULE_IMAGE = "default_schedule.jpg";

	@Autowired
	public ScheduleServiceImpl(ScheduleDao scheduleDao, ResourceLoader resourceLoader) {
		this.scheduleDao = scheduleDao;
		this.resourceLoader = resourceLoader;
	}

	@Override
	public List<Schedule> getScheduleList(String dateTime, String category, String addressName) {
		Map<String, String> condition = new HashMap<>();

		condition.put("dateTime", dateTime);
		condition.put("category", category);
		condition.put("addressName", addressName);

		return scheduleDao.selectScheduleByCon(condition);
	}

	// 번개 상세 조회
	@Override
	public Schedule getOneSchedule(int scheduleId) {
		return scheduleDao.selectOneSchedule(scheduleId);
	}
	
	// 번개 생성
	@Override
	@Transactional
	public int createSchedule(Schedule schedule, MultipartFile file) {
		// 업로드한 파일 있으면
		if (file != null && file.getSize() > 0) {
			try {
				// 파일 저장
				Path uploadPath = Paths.get(desktopDir);
	
		        // 디렉토리가 존재하지 않으면 생성
		        if (!Files.exists(uploadPath)) {
		            Files.createDirectories(uploadPath);
		        }
	
		        schedule.setOrgImage(file.getOriginalFilename());
		        schedule.setImage( UUID.randomUUID().toString() + "_" + file.getOriginalFilename());
		        Path filePath = uploadPath.resolve(schedule.getImage());
	
		        // 파일 저장
		        Files.copy(file.getInputStream(), filePath);
		        
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
	
		}

		// 업로드한 파일 없으면 기본 이미지 저장
		else {
			schedule.setOrgImage(DEFALT_SCHEDULE_IMAGE);
			// 중복 방지 파일명 저장
			schedule.setImage(UUID.randomUUID().toString() + "_" + DEFALT_SCHEDULE_IMAGE);
		}
		
		return scheduleDao.insertSchedule(schedule);

	}

	@Override
	@Transactional
	public int sendJoin(String loginUserId, int scheduleId) {
		Map<String,Object> newApply = new HashMap<>();
		newApply.put("userId", loginUserId);
		newApply.put("scheduleId", scheduleId);
		
		return scheduleDao.insertUserSchedule(newApply);
	}

	@Override
	public List<Schedule> getRemainSchedule(String userId) {
		return scheduleDao.selectRemainSchedule(userId);
	}

	@Override
	public List<Schedule> getPastSchedule(String userId) {
		return scheduleDao.selectPastSchedule(userId);
	}
	
	// 신청한 적 없는 번개면 true
	@Override
	public boolean noApply(String userId, int scheduleId) {
		Map<String,Object> map = new HashMap<>();
		map.put("userId", userId); 
		map.put("scheduleId", scheduleId);
		
		return (scheduleDao.selectApply(map) == 0 ? true : false);
		
	}
	
	// 전체 조회
	@Override
	public List<Schedule> getAllSchedule() {
		return scheduleDao.selectAllSchedule();
	}

}
