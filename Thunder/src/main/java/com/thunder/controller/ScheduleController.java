package com.thunder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thunder.model.dto.Schedule;
import com.thunder.model.dto.ScheduleSearchCondtion;
import com.thunder.model.service.ScheduleService;

@RestController
@RequestMapping("/thunder")
public class ScheduleController {
	
	private final ScheduleService scheduleService;
	
	@Autowired
	public ScheduleController(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}
	
	
	// 번개 검색 및 조회
	@GetMapping("")
	public ResponseEntity<?> searchSchedule(@RequestBody ScheduleSearchCondtion condition){
		List<Schedule> list = scheduleService.getScheduleList(condition);
		return ResponseEntity.ok(list);
		
	}
	
}
