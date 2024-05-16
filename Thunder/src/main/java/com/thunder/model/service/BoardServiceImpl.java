package com.thunder.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thunder.model.dao.BoardDao;
import com.thunder.model.dto.Board;

@Service
public class BoardServiceImpl implements BoardService {

	private final BoardDao boardDao;

	@Autowired
	public BoardServiceImpl(BoardDao boardDao) {
		this.boardDao = boardDao;
	}
	
	@Override
	public List<Board> getAllBoard(int scheduleId) {
		return boardDao.selectAllBoard(scheduleId);
	}

	@Override
	public Board getBoard(int scheduleId, int boardId) {
		Map<String, Integer> params = new HashMap<>();
		
		params.put("scheduleId", scheduleId);
		params.put("boardId", boardId);
		
		return boardDao.selectBoard(params);
	}

	@Override
	public boolean registBoard(String userId, int scheduleId, Board board) {
		board.setUserId(userId);
		board.setScheduleId(scheduleId);
		
		return boardDao.insertBoard(board) == 1 ? true : false;
	}

	@Override
	public boolean updateBoard(int scheduleId, int boardId, Board board) {
		board.setId(boardId);
		board.setScheduleId(scheduleId);
		
		return boardDao.updateBoard(board) == 1 ? true : false;
	}

	@Override
	public boolean validateRegist(String userId, int scheduleId, int boardId) {
		Map<String, Object> params = new HashMap<>();
		
		params.put("userId", userId);
		params.put("scheduleId", scheduleId);
		params.put("boardId", boardId);
		
		return boardDao.validate(params) == 1 ? true : false; 
	}

	@Override
	public boolean deleteBoard(int scheduleId, int boardId) {
		Map<String, Integer> params = new HashMap<>();
		
		params.put("scheduleId", scheduleId);
		params.put("boardId", boardId);
		
		return boardDao.deleteBoard(params);
	}
	
}
