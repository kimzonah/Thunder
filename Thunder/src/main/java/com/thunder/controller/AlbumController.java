package com.thunder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.thunder.model.dto.Album;
import com.thunder.model.dto.User;
import com.thunder.model.service.AlbumService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;



@RestController
@RequestMapping("/album")
@Tag(name="AlbumController", description="사진첩 관련 기능")
@CrossOrigin("http://localhost:5173")
public class AlbumController {

	private final AlbumService albumService;
	
	@Autowired
	public AlbumController(AlbumService albumService) {
		this.albumService = albumService;
	}
	
	// 1. 앨범에 사진 등록
	@PostMapping(value = "/{scheduleId}", consumes = { "multipart/form-data" })
	public ResponseEntity<Void> registImage(@PathVariable("scheduleId") int scheduleId, @RequestPart(name = "file", required = false) MultipartFile file, HttpSession session) {
		// session 처리
		String userId = ((User) session.getAttribute("loginUser")).getId();
		
		// 앨범에 이미지 등록에 실패하면 badRequest 반환
		if (!albumService.registImage(userId, file, scheduleId)) {
			return ResponseEntity.badRequest().build();
		};
		
		return ResponseEntity.ok().build();
	}
	
	// 2. 앨범 사진 전체 조회
	@GetMapping("")
	public ResponseEntity<List<Album>> getAlbumList(HttpSession session) {
		// session 처리
		String userId = ((User) session.getAttribute("loginUser")).getId();
		
		List<Album> list = albumService.getAllAlbum(userId);
		return ResponseEntity.ok(list);
	}
	
	
}
