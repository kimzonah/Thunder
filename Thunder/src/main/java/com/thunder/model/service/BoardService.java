package com.thunder.model.service;

import java.util.List;

import com.thunder.model.dto.Board;

public interface BoardService {

	List<Board> getAllBoard(int scheduleId);

	Board getBoard(int scheduleId, int boardId);

}
