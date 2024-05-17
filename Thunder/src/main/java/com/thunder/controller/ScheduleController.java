package com.thunder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thunder.model.dto.Schedule;
import com.thunder.model.dto.ScheduleSearchCondtion;
import com.thunder.model.service.ScheduleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/thunder")
@Tag(name = "ScheduleController", description = "번개 CRUD")
public class ScheduleController {
	
	private final ScheduleService scheduleService;
	
	@Autowired
	public ScheduleController(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}
	
	
	// 상세 번개 조회
	@Operation(summary = "상세 번개 조회")
	@GetMapping("/{scheduleId}")
	public ResponseEntity<?> getScheduleDetail(@PathVariable("scheduleId") int scheduleId){
		
		Schedule schedule = scheduleService.getOneSchedule(scheduleId);
		// 조회된 번개가 없으면 not found
		if(schedule == null) {
			return ResponseEntity.notFound().build();
		}
		// 조회된 번개가 있으면 번개 정보 반환
		return ResponseEntity.ok(schedule);
		
	}
	
}
