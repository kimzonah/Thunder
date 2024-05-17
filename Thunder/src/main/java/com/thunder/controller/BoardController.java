package com.thunder.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import org.springframework.web.bind.annotation.PutMapping;




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
	
	// 1. 번개 게시글 전체 조회
	@GetMapping("/{scheduleId}")
	public ResponseEntity<List<Board>> getBoardList(@PathVariable("scheduleId") int scheduleId, HttpSession session) {
		// session 처리
		String userId = (String) session.getAttribute("loginUser");
		
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
		
		// 성공 응답
		return ResponseEntity.ok(list);
	}
	
	// 2. 번개 게시글 상세 조회
	@GetMapping("/{scheduleId}/{boardId}")
	public ResponseEntity<Board> getBoard(@PathVariable("scheduleId") int scheduleId, @PathVariable int boardId, HttpSession session) {
		// session 처리
		String userId = (String) session.getAttribute("loginUser");
		
		// 로그인 유저가 번개에 가입되어 있는지, 게시글을 작성한 유저가 맞는지 검증
		
		// 실패 응답 1. 번개에 가입되어 있지 않다면 접근 거부 응답 반환 (403)
		if (!userScheduleService.validateJoin(userId, scheduleId)) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	    }
				
		// schedule id와 board id를 통해 게시글 가져오기
		Board board = boardService.getBoard(scheduleId, boardId);
		
		// 실패 응답 2. board가 null이면 404
		if (board == null) {
			return ResponseEntity.notFound().build();
		}
		
		// 성공 응답
		return ResponseEntity.ok(board);
	}
	
	// 3. 번개 게시글 작성
	@PostMapping("/{scheduleId}")
	public ResponseEntity<Void> registBoard(@PathVariable("scheduleId") int scheduleId, @RequestBody Board board, HttpSession session) {
		// session 처리
		String userId = (String) session.getAttribute("loginUser");
		
		// 로그인 유저가 번개에 가입되어 있는지, 게시글을 작성한 유저가 맞는지 검증
		
		// 실패 응답 1. 번개에 가입되어 있지 않다면 접근 거부 응답 반환 (403)
		if (!userScheduleService.validateJoin(userId, scheduleId)) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	    }
		
		// board 등록하기
		
		// 실패 응답 2. 등록 중 실패했다면 badRequest (400) 
		if (!boardService.registBoard(userId, scheduleId, board)) {
			return ResponseEntity.badRequest().build();
		};
		
		// 성공 응답
		return ResponseEntity.ok().build();
	}

	// 4. 번개 게시글 수정
	@PutMapping("/{scheduleId}/{boardId}")
	public ResponseEntity<Void> updateBoard(@PathVariable("scheduleId") int scheduleId, @PathVariable int boardId, @RequestBody Board board, HttpSession session) {
		// session 처리
		String userId = (String) session.getAttribute("loginUser");
		
		// 로그인 유저가 번개에 가입되어 있는지, 게시글을 작성한 유저가 맞는지 검증
		
		// 실패 응답 1. 번개에 가입되어 있지 않거나 게시글을 작성하지 않았다면 접근 거부 응답 반환 (403)
		if (!userScheduleService.validateJoin(userId, scheduleId) || !boardService.validateRegist(userId, scheduleId, boardId)) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	    }
		
		// board 수정하기
		
		// 실패 응답 2. 제대로 수행되지 않으면 badRequest
		if (!boardService.updateBoard(scheduleId, boardId, board)) {
			return ResponseEntity.badRequest().build();
		};
		
		// 성공 응답
		return ResponseEntity.ok().build();
	}
	
	// 5. 번개 게시글 삭제
	@DeleteMapping("{scheduleId}/{boardId}")
	public ResponseEntity<Void> deleteBoard(@PathVariable("scheduleId") int scheduleId, @PathVariable int boardId, HttpSession session) {
		// session 처리
		String userId = (String) session.getAttribute("loginUser");
		
		// 로그인 유저가 번개에 가입되어 있는지, 게시글을 작성한 유저가 맞는지 검증
		
		// 실패 응답 1. 번개에 가입되어 있지 않거나 게시글을 작성하지 않았다면 접근 거부 응답 반환 (403)
		if (!userScheduleService.validateJoin(userId, scheduleId) || !boardService.validateRegist(userId, scheduleId, boardId)) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	    }
		
		// board 삭제하기
		
		// 실패 응답 2. 삭제할 board에 접근하지 못하면 notFound
		if (!boardService.deleteBoard(scheduleId, boardId)) {
			return ResponseEntity.notFound().build();
		}
		
		// 성공 응답
		return ResponseEntity.ok().build();
	}

}
