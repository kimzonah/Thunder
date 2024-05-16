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
	public ResponseEntity<List<Board>> getBoardList(@PathVariable("scheduleId") int scheduleId) {
		// 전체 게시글 가져와 list에 담기
		List<Board> list = boardService.getAllBoard(scheduleId);
		
		if (list == null) { // list가 null이면 404
			return ResponseEntity.notFound().build();
		} else if (list.size() == 0) { // list가 비어있으면 204
			return ResponseEntity.noContent().build();
		}
		
		// 정상 응답
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/{scheduleId}/{boardId}")
	public ResponseEntity<Board> getBoard(@PathVariable("scheduleId") int scheduleId, @PathVariable int boardId) {
		// schedule id와 board id를 통해 게시글 가져오기
		Board board = boardService.getBoard(scheduleId, boardId);
		
		if (board == null) { // board가 null이면 404
			return ResponseEntity.notFound().build();
		}
		
		// 정상 응답
		return ResponseEntity.ok(board);
	}
	
	
}
