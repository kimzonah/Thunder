package com.thunder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.thunder.model.dto.Schedule;
import com.thunder.model.dto.ScheduleSearchCondtion;
import com.thunder.model.dto.User;
import com.thunder.model.service.ScheduleService;
import com.thunder.model.service.UserScheduleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin("http://localhost:5173")
@RequestMapping("/thunder")
@Tag(name = "ScheduleController", description = "전체 번개 관련 기능")
public class ScheduleController {
	
	private final ScheduleService scheduleService;
	private final UserScheduleService userScheduleService;
	
	@Autowired
	public ScheduleController(ScheduleService scheduleService, UserScheduleService userScheduleService) {
		this.scheduleService = scheduleService;
		this.userScheduleService = userScheduleService;
	}
	
	// 번개 검색 및 조회
    @Operation(summary = "번개 조회 및 검색")
    @PostMapping("/search")
    public ResponseEntity<?> searchSchedule(@RequestBody ScheduleSearchCondtion condition){
        
        List<Schedule> list = scheduleService.getScheduleList(condition.getDateTime(), condition.getCategoty(), condition.getAddressName());
        
        // 검색 결과가 없을 때 204 응답
        if(list.size()==0) {
            return ResponseEntity.noContent().build();
        }
        
        // 정상 응답 200
        return ResponseEntity.ok(list);
        
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
		// 조회된 번개가 있으면 번개 정보와 200 응답
		return ResponseEntity.ok(schedule);
	}
	
	// 번개 생성
	@Operation(summary = "번개 생성")
	@PostMapping(value = "", consumes = {"multipart/form-data"})
	public ResponseEntity<Void> registSchedule(@ModelAttribute Schedule schedule,
			@RequestPart(name = "file", required = false) MultipartFile file, HttpSession session){
		
		User loginUser = (User) session.getAttribute("loginUser");
		
		// 로그인 유저가 있다면 그 유저의 아이디를 관리자 아이디로 설정
		schedule.setManagerId(loginUser.getId());
		
		int result = scheduleService.createSchedule(schedule, file);
		
		// 번개 생성 실패시 400
		if(result == 0) {
			return ResponseEntity.badRequest().build();
		}
		
		// 번개 생성 성공 응답
		return ResponseEntity.ok().build();
	}
	
	// 번개 참여 신청
	@Operation(summary = "번개 참여 신청")
	@PostMapping("/join/{scheduleId}")
	public ResponseEntity<?> joinSchedule(@PathVariable("scheduleId") int scheduleId, HttpSession session){
		// 현재 로그인 한 유저
		User loginUser = (User) session.getAttribute("loginUser");
		String loginUserId = loginUser.getId();
		
		// 현재 로그인 유저가 이미 가입한 번개라면 접근 금지 403
		if(userScheduleService.validateJoin(loginUserId, scheduleId)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		//현재 로그인이 접근 가능한 번개라면
		int result = scheduleService.sendJoin(loginUserId, scheduleId);
		
		
		
		return ResponseEntity.ok().build();
	}
	
}
