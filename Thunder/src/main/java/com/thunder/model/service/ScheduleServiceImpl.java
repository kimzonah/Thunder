package com.thunder.model.service;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.thunder.model.dao.ScheduleDao;
import com.thunder.model.dto.Schedule;
import com.thunder.model.dto.ScheduleSearchCondtion;

@Service
public class ScheduleServiceImpl implements ScheduleService {

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
	@Transactional
	@Override
	public int createSchedule(Schedule schedule, MultipartFile file) {
		// 업로드한 파일 있으면
		if (file != null && file.getSize() > 0) {
			try {
				// 원본 파일명 저장
				schedule.setOrgImage(file.getOriginalFilename());
				// 중복 방지 파일명 저장
				schedule.setImage(UUID.randomUUID().toString() + "_" + file.getOriginalFilename());

				Resource resource = resourceLoader.getResource("classpath:/static/img");
				file.transferTo(new File(resource.getFile(), schedule.getImage()));

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
	public int sendJoin(String loginUserId, int scheduleId) {
		Map<String,Object> newApply = new HashMap<>();
		newApply.put("userId", loginUserId);
		newApply.put("scheduleId", scheduleId);
		
		return scheduleDao.insertUserSchedule(newApply);
	}

}
