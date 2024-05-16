package com.thunder.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thunder.model.dto.Board;
import com.thunder.model.service.BoardService;
import com.thunder.model.service.UserScheduleService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/thunder/board")
@Tag(name="BoardController", description="게시판 CRUD")
@CrossOrigin("http://localhost:5173") // 개개인의 메서드에 설정할 때는 value
public class BoardController {
	
	private final BoardService boardService;
	
	private final UserScheduleService userScheduleService;
	
	public BoardController(BoardService boardService, UserScheduleService userScheduleService) {
		this.boardService = boardService;
		this.userScheduleService = userScheduleService;
	}
	
	@GetMapping("/{scheduleId}")
	public ResponseEntity<List<Board>> getBoardList(@PathVariable("scheduleId") int scheduleId, HttpSession session) {
		// session 처리
//		String userId = (String) session.getAttribute("loginUser");
		String userId = "seonha";
		
		// 로그인 유저가 번개에 가입되어 있는지 검증
		if (!userScheduleService.validateJoin(userId, scheduleId)) {
			 // 번개에 가입되어 있지 않다면 접근 거부 응답 반환 (403)
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	    }
		
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
	
	@PostMapping("/board/{scheduleId}")
	public ResponseEntity<Void> registBoard(@PathVariable("scheduleId") int scheduleId, @RequestBody Board board, HttpSession session) {
		// session을 통해 login User 가져오기
		String userId = (String) session.getAttribute("loginUser");
		
		// board 등록하기
		return null;
	}
	
	
	
}
