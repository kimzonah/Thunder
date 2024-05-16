package com.thunder.model.service;

import java.util.List;

import com.thunder.model.dto.Board;

public interface BoardService {

	List<Board> getAllBoard(int scheduleId);

	Board getBoard(int scheduleId, int boardId);

	boolean registBoard(String userId, int scheduleId, Board board);

	boolean updateBoard(int scheduleId, int boardId, Board board);

	boolean validateRegist(String userId, int scheduleId, int boardId);

	boolean deleteBoard(int scheduleId, int boardId);

}
