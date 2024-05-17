package com.thunder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thunder.model.dto.Comment;
import com.thunder.model.dto.User;
import com.thunder.model.service.BoardService;
import com.thunder.model.service.CommentService;
import com.thunder.model.service.UserScheduleService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/thunder/comment")
@Tag(name="CommentController", description="댓글 관련 기능")
@CrossOrigin("http://localhost:5173")
public class CommentController {
	
	private final CommentService commentService;
	
	private final UserScheduleService userScheduleService;
	
	private final BoardService boardService;
	
	@Autowired
	public CommentController(CommentService commentService, UserScheduleService userScheduleService, BoardService boardService) {
		this.commentService = commentService;
		this.userScheduleService = userScheduleService;
		this.boardService = boardService;
	}

	// 1. 번개 게시글 댓글 전체 조회
	@GetMapping("/{scheduleId}/{boardId}")
	public ResponseEntity<List<Comment>> getCommentList(@PathVariable("scheduleId") int scheduleId, @PathVariable("boardId") int boardId, HttpSession session) {
		// session 처리
		String userId = ((User) session.getAttribute("loginUser")).getId();
		
		// 로그인 유저가 번개에 가입되어 있는지 검증
		
		// 실패 응답 1. 번개에 가입되어 있지 않다면 접근 거부 응답 반환 (403)
		if (!userScheduleService.validateJoin(userId, scheduleId)) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	    }
		
		// 전체 댓글 가져오기
		List<Comment> list = commentService.getAllComment(boardId);
		
		if (list == null) { // 실패 응답 2. list가 null이면 404
			return ResponseEntity.notFound().build();
		} else if (list.size() == 0) { // list가 비어있으면 204
			return ResponseEntity.noContent().build();
		}
		
		// 성공 응답
		return ResponseEntity.ok(list);
	}
	
	
	// 2. 번개 게시글 댓글 작성
	@PostMapping("/{scheduleId}/{boardId}")
	public ResponseEntity<Void> registComment(@PathVariable("scheduleId") int scheduleId, @PathVariable("boardId") int boardId, HttpSession session, @RequestBody Comment comment) {
		// session 처리
		String userId = ((User) session.getAttribute("loginUser")).getId();
		
		// 로그인 유저가 번개에 가입되어 있는지 검증
		
		// 실패 응답 1. 번개에 가입되어 있지 않다면 접근 거부 응답 반환 (403)
		if (!userScheduleService.validateJoin(userId, scheduleId)) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	    }
		
		// 실패 응답 2. 번개 등록에 실패하면 badRequest
		if (!commentService.registComment(boardId, userId, comment)) {
			return ResponseEntity.badRequest().build();
		}
		
		// 번개 등록에 성공하면 board의 comment_cnt 증가
		boardService.addComment(boardId);
		
		// 성공 응답
		return ResponseEntity.ok().build();
	}
	
	// 3. 번개 게시글 댓글 삭제
	@DeleteMapping("/{scheduleId}/{boardId}/{commentId}")
	public ResponseEntity<Void> deleteComment(@PathVariable("scheduleId") int scheduleId, @PathVariable("boardId") int boardId, 
			@PathVariable("commentId") int commentId, HttpSession session) {
		// session 처리
		String userId = ((User) session.getAttribute("loginUser")).getId();
		
		// 로그인 유저가 번개에 가입되어 있는지, 게시글을 작성한 유저가 맞는지 검증
		
		// 실패 응답 1. 번개에 가입되어 있지 않거나 게시글을 작성하지 않았다면 접근 거부 응답 반환 (403)
		if (!userScheduleService.validateJoin(userId, scheduleId) || !commentService.validateRegist(userId, commentId)) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	    }
		
		// 실패 응답 2. 댓글 삭제를 실패하면 badRequest
		if(!commentService.deleteComment(commentId)) {
			return ResponseEntity.badRequest().build();
		}
		
		// 번개 삭제 시 board의 comment_cnt 감소
		boardService.deleteComment(boardId);
				
		// 성공 응답
		return ResponseEntity.ok().build();
	}
}
