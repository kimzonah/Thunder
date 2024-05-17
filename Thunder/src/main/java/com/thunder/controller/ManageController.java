package com.thunder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thunder.model.dto.Schedule;
import com.thunder.model.dto.User;
import com.thunder.model.service.ManageService;
import com.thunder.model.service.UserScheduleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/thunder/manage")
@Tag(name="ManageController", description="번개장 설정 관련 기능")
@CrossOrigin("http://localhost:5173")
public class ManageController {
	
	private final ManageService manageService;
	
	private final UserScheduleService userScheduleService;
	
	@Autowired
	public ManageController(ManageService manageService, UserScheduleService userScheduleService) {
		this.manageService = manageService;
		this.userScheduleService = userScheduleService;
	}
	
	// 1. 번개 신청 조회
	@Operation(summary = "번개 신청 조회")
	@GetMapping("/{scheduleId}")
	public ResponseEntity<List<User>> getApplyUserList(@PathVariable("scheduleId") int scheduleId, HttpSession session) {
		// session 처리
		String userId = (String) session.getAttribute("loginUser");
		
		// 로그인 유저가 번개에 가입되어 있는지, 번개장이 맞는지 검증
		
		// 실패 응답 1. 번개에 가입되어 있지 않거나 번개장이 아니라면 접근 거부 응답 반환 (403)
		if (!manageService.validateManager(userId, scheduleId)) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	    }
		
		// 가입 요청 보낸 전체 유저 리스트
		List<User> list = manageService.getAllApplyUser(scheduleId);
		
		if (list == null) { // 실패 응답 2. list가 null이면 404
			return ResponseEntity.notFound().build();
		} else if (list.size() == 0) { // list가 비어있으면 204
			return ResponseEntity.noContent().build();
		}
		
		// 성공 응답
		return ResponseEntity.ok(list);
	}
	
	// 2. 번개 신청 수락
	@Operation(summary = "번개 신청 수락")
	@PutMapping("/{scheduleId}/{userId}")
	public ResponseEntity<Void> updateApplyStatus(@PathVariable("scheduleId") int scheduleId, @PathVariable("userId") String userId, HttpSession session) {
		// session 처리
		String managerId = (String) session.getAttribute("loginUser");
		
		// 로그인 유저가 번개에 가입되어 있는지, 번개장이 맞는지 검증
		
		// 실패 응답 1. 번개에 가입되어 있지 않거나 번개장이 아니라면 접근 거부 응답 반환 (403)
		if (!manageService.validateManager(managerId, scheduleId)) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	    }
		
		// 실패 응답 2. 수락할 요청을 찾지 못했다면 404
		if (!manageService.updateStatus(scheduleId, userId)) {
			return ResponseEntity.notFound().build();
		}
		
		// 성공 응답
		return ResponseEntity.ok().build();
	}
	
	// 3. 번개 신청 거절
	@Operation(summary = "번개 신청 거절")
	@DeleteMapping("/{scheduleId}/{userId}")
	public ResponseEntity<Void> refuseApply(@PathVariable("scheduleId") int scheduleId, @PathVariable("userId") String userId, HttpSession session) {
		// session 처리
		String managerId = (String) session.getAttribute("loginUser");
		
		// 로그인 유저가 번개에 가입되어 있는지, 번개장이 맞는지 검증

		// 실패 응답 1. 번개에 가입되어 있지 않거나 번개장이 아니라면 접근 거부 응답 반환 (403)
		if (!manageService.validateManager(managerId, scheduleId)) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	    }
		
		// 실패 응답 2. 수락할 요청을 찾지 못했다면 404
		if (!manageService.deleteApply(scheduleId, userId)) {
			return ResponseEntity.notFound().build();
		}
		
		// 성공 응답
		return ResponseEntity.ok().build();
	}
	
	// 4. 번개 일정 수정
	@Operation(summary = "번개 일정 수정")
	@PutMapping("/{scheduleId}")
	public ResponseEntity<Void> updateSchedule(@PathVariable("scheduleId") int scheduleId, @RequestBody Schedule schedule, HttpSession session) {
		// session 처리
		String managerId = (String) session.getAttribute("loginUser");
		
		// 로그인 유저가 번개에 가입되어 있는지, 번개장이 맞는지 검증

		// 실패 응답 1. 번개에 가입되어 있지 않거나 번개장이 아니라면 접근 거부 응답 반환 (403)
		if (!manageService.validateManager(managerId, scheduleId)) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	    }
		
		// 실패 응답 2. 수정할 일정을 찾을 수 없다면 badRequest 반환
		if (!manageService.updateSchedule(scheduleId, schedule)) {
			return ResponseEntity.badRequest().build();
		}
		
		return ResponseEntity.ok().build();
	}
	
}
