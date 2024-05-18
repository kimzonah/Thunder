package com.thunder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.thunder.model.dto.Album;
import com.thunder.model.service.AlbumService;
import com.thunder.model.service.UserScheduleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;



@RestController
@RequestMapping("/album")
@Tag(name="AlbumController", description="사진첩 관련 기능")
@CrossOrigin("http://localhost:5173")
public class AlbumController {

	private final AlbumService albumService;
	
	private final UserScheduleService userScheduleService;
	
	@Autowired
	public AlbumController(AlbumService albumService, UserScheduleService userScheduleService) {
		this.albumService = albumService;
		this.userScheduleService = userScheduleService;
	}
	
	// 1. 앨범에 사진 등록
	@Operation(summary = "앨범에 사진 등록")
	@PostMapping(value = "/{scheduleId}", consumes = { "multipart/form-data" })
	public ResponseEntity<Void> registImage(@PathVariable("scheduleId") int scheduleId, @RequestPart(name = "file", required = false) MultipartFile file, HttpSession session) {
		// session 처리
		String userId = (String) session.getAttribute("loginUser");
		
		// 실패 응답 1. 번개에 가입하지 않았다면 접근 거부 응답 반환 (403)
		if (!userScheduleService.validateJoin(userId, scheduleId)) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	    }
		
		// 실패 응답 2. 앨범에 이미지 등록에 실패하면 badRequest 반환
		if (!albumService.registImage(userId, file, scheduleId)) {
			return ResponseEntity.badRequest().build();
		};
		
		return ResponseEntity.ok().build();
	}
	
	// 2. 앨범 사진 전체 조회
	@Operation(summary = "앨범 사진 전체 조회")
	@GetMapping("")
	public ResponseEntity<List<Album>> getAlbumList(HttpSession session) {
		// session 처리
		String userId = (String) session.getAttribute("loginUser");
		
		List<Album> list = albumService.getAllAlbum(userId);
		return ResponseEntity.ok(list);
	}
	
	
}
