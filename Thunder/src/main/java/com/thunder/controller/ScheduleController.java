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
	
	
	// 번개 검색 및 조회
	@Operation(summary = "번개 조회 및 검색")
	@GetMapping("/search")
	public ResponseEntity<?> searchSchedule(@RequestBody ScheduleSearchCondtion condition){
		
		System.out.println(condition);
		
		List<Schedule> list = scheduleService.getScheduleList(condition.getDateTime(), condition.getCategoty(), condition.getAddressName());
		
		// 검색 결과가 없을 때 204 응답
		if(list.size()==0) {
			return ResponseEntity.noContent().build();
		}
		
		// 정상 응답 200
		return ResponseEntity.ok(list);
		
	}
	
}
