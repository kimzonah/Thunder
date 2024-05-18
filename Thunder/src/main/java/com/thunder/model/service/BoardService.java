package com.thunder.model.service;

import java.util.List;

import com.thunder.model.dto.Board;

public interface BoardService {

	// 모든 게시글 조회
	List<Board> getAllBoard(int scheduleId);

	// 게시글 상세 조회
	Board getBoard(int scheduleId, int boardId);

	// 게시글 등록
	boolean registBoard(String userId, int scheduleId, Board board);

	// 게시글 수정
	boolean updateBoard(int scheduleId, int boardId, Board board);

	// 게시글 등록자인지 검증
	boolean validateRegist(String userId, int scheduleId, int boardId);

	// 게시글 삭제
	boolean deleteBoard(int scheduleId, int boardId);

	// 댓글 추가
	void addComment(int boardId);

	// 댓글 삭제
	void deleteComment(int boardId);

	// 게시글 존재 여부 검증
	boolean isExist(int boardId);

}
