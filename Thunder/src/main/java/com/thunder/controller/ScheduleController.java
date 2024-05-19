package com.thunder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.thunder.model.service.ManageService;
import com.thunder.model.service.ScheduleService;
import com.thunder.model.service.UserScheduleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@CrossOrigin("http://localhost:5173")
@RequestMapping("/thunder")
@Tag(name = "ScheduleController", description = "전체 번개 관련 기능")
public class ScheduleController {
	
	private final ScheduleService scheduleService;
	
	private final UserScheduleService userScheduleService;
	
	private final ManageService manageService;
	
	@Autowired
	public ScheduleController(ScheduleService scheduleService, UserScheduleService userScheduleService, ManageService manageService) {
		this.scheduleService = scheduleService;
		this.userScheduleService = userScheduleService;
		this.manageService = manageService;
	}
	
	// 번개 전체 조회
	@Operation(summary = "번개 전체 조회")
	@GetMapping("")
	public ResponseEntity<List<Schedule>> getAllSchedule(){
		List<Schedule> list = scheduleService.getAllSchedule();
		
		if(list.size()==0) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(list);
	}
	
	// 번개 검색 및 조회
    @Operation(summary = "번개 조회 및 검색")
    @PostMapping("/search")
    public ResponseEntity<?> searchSchedule(@RequestBody ScheduleSearchCondtion condition){
        
        List<Schedule> list = scheduleService.getScheduleList(condition.getDateTime(), condition.getCategory(), condition.getAddressName());
        
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
	
	// 번개 참여인원 조회
	@Operation(summary = "번개 참여인원 조회	")
	@GetMapping("/join/{scheduleId}")
	public ResponseEntity<Integer> getJoinUserCnt(@PathVariable("scheduleId") int scheduleId){
		
		int cnt = userScheduleService.getJoinUserCnt(scheduleId);
		
		// 조회된 번개가 있으면 번개 정보와 200 응답
		return ResponseEntity.ok(cnt);
	}
	
	// 번개 생성
	@Operation(summary = "번개 생성")
	@PostMapping(value = "", consumes = {"multipart/form-data"})
	public ResponseEntity<Void> registSchedule(@ModelAttribute Schedule schedule,
			@RequestPart(name = "file", required = false) MultipartFile file, HttpSession session){
		// session 처리
		String userId = (String) session.getAttribute("loginUser");
		
		// 로그인 유저가 있다면 그 유저의 아이디를 관리자 아이디로 설정
		schedule.setManagerId(userId);
		
		int result = scheduleService.createSchedule(schedule, file);
		
		// 번개 생성 실패시 400
		if(result == 0) {
			return ResponseEntity.badRequest().build();
		}
		
		// 번개가 생성되었다면 번개 참여자에 번개장도 추가
		userScheduleService.addManager(userId, schedule.getId());
		
		// 번개 생성 성공 응답
		return ResponseEntity.ok().build();
	}
	
	// 번개 참여 신청
	@Operation(summary = "번개 참여 신청")
	@PostMapping("/join/{scheduleId}")
	public ResponseEntity<?> joinSchedule(@PathVariable("scheduleId") int scheduleId, HttpSession session){
		// 현재 로그인 한 유저
		String userId = (String) session.getAttribute("loginUser");
		
		// 현재 로그인 유저가 이미 가입한 번개라면 접근 금지 403
		if(userScheduleService.validateJoin(userId, scheduleId)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		//현재 로그인이 접근 가능한 번개라면
		int result = scheduleService.sendJoin(userId, scheduleId);
		
		// 요청 실패시 400 응답
		if(result == 0) {
			return ResponseEntity.badRequest().build();
		}
		
		// 요청 성공
		return ResponseEntity.ok().build();
	}
	
	// 나의 예정 번개 조회
	@Operation(summary = "나의 예정 번개 조회")
	@GetMapping("/my/remain")
	public ResponseEntity<List<Schedule>> getRemainScheduleList(HttpSession session) {
		// session 처리
		String userId = (String) session.getAttribute("loginUser");
		
		// 유저 아이디로 예정된 번개 조회
		List<Schedule> list = scheduleService.getRemainSchedule(userId);
		
		if (list == null) { // 실패 응답 1. list가 null이면 404
			return ResponseEntity.notFound().build();
		} else if (list.size() == 0) { // list가 비어있으면 204
			return ResponseEntity.noContent().build();
		}
		
		// 성공 응답
		return ResponseEntity.ok(list);
	}
	
	// 나의 지난 번개 조회
	@Operation(summary = "나의 지난 번개 조회")
	@GetMapping("/my/past")
	public ResponseEntity<List<Schedule>> getPastScheduleList(HttpSession session) {
		// session 처리
		String userId = (String) session.getAttribute("loginUser");
		
		// 유저 아이디로 예정된 번개 조회
		List<Schedule> list = scheduleService.getPastSchedule(userId);
		
		if (list == null) { // 실패 응답 1. list가 null이면 404
			return ResponseEntity.notFound().build();
		} else if (list.size() == 0) { // list가 비어있으면 204
			return ResponseEntity.noContent().build();
		}
		
		// 성공 응답
		return ResponseEntity.ok(list);
	}
	
	// 번개 나가기
	@Operation(summary = "번개 나가기")
	@DeleteMapping("/my/{scheduleId}")
	public ResponseEntity<Void> deleteMySchedule(@PathVariable("scheduleId") int scheduleId, HttpSession session) {
		// session 처리
		String userId = (String) session.getAttribute("loginUser");
		
		// 로그인 유저가 번개에 가입되어 있는지 검증
		
		// 실패 응답 1. 번개에 가입되어 있지 않거나 번개장이라면 접근 거부 응답 반환 (403)
		if (!userScheduleService.validateJoin(userId, scheduleId) || manageService.validateManager(userId, scheduleId)) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	    }
		
		// 실패 응답 2. 번개 나가기에 실패하면 notFound (404)
		if (!userScheduleService.deleteSchedule(userId, scheduleId)) {
			return ResponseEntity.notFound().build();
		}
		
		// 성공 응답
		return ResponseEntity.ok().build();
	}
	
	// 번개 신청 및 가입 여부 확인
	@Operation(summary = "번개 신청 및 가입 여부 확인 (신청안함:0 / 참여중:1 / 신청후 승인 대기중:2)")
	@GetMapping("/status/{scheduleId}")
	public ResponseEntity<Integer> checkStatus(@PathVariable("scheduleId") int scheduleId, HttpSession session){
		
		String userId = (String) session.getAttribute("loginUser");
		
		// 번개 신청 안한 상태면 0 반환
		if(scheduleService.noApply(userId, scheduleId)) {
			return ResponseEntity.ok(0);
		}
		
		// 번개 승인받았고 참여 중이면 1 반환
		if(userScheduleService.validateJoin(userId, scheduleId)) {
			return ResponseEntity.ok(1);
		}
		
		// 번개 신청 후 승인 대기 중이면 2반환
		return ResponseEntity.ok(2);
		
	}
}
