package com.thunder.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thunder.model.dto.Board;
import com.thunder.model.service.BoardService;

import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/thunder/board")
@Tag(name="BoardController", description="게시판 CRUD")
@CrossOrigin("http://localhost:5173") // 개개인의 메서드에 설정할 때는 value
public class BoardController {
	
	private final BoardService boardService;
	
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	@GetMapping("/{scheduleId}")
	public ResponseEntity<List<Board>> getBoardList(@PathVariable int scheduleId) {
		List<Board> list = boardService.getAllBoard(scheduleId);
		
		if (list == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(list);
	}
	
	
}
