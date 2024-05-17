package com.thunder.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional
	public List<Board> getAllBoard(int scheduleId) {
		return boardDao.selectAllBoard(scheduleId);
	}

	@Override
	@Transactional
	public Board getBoard(int scheduleId, int boardId) {
		Map<String, Integer> params = new HashMap<>();
		
		params.put("scheduleId", scheduleId);
		params.put("boardId", boardId);
		
		return boardDao.selectBoard(params);
	}

	@Override
	@Transactional
	public boolean registBoard(String userId, int scheduleId, Board board) {
		board.setUserId(userId);
		board.setScheduleId(scheduleId);
		
		return boardDao.insertBoard(board) == 1 ? true : false;
	}

	@Override
	@Transactional
	public boolean updateBoard(int scheduleId, int boardId, Board board) {
		board.setId(boardId);
		board.setScheduleId(scheduleId);
		
		return boardDao.updateBoard(board) == 1 ? true : false;
	}

	@Override
	@Transactional
	public boolean validateRegist(String userId, int scheduleId, int boardId) {
		Map<String, Object> params = new HashMap<>();
		
		params.put("userId", userId);
		params.put("scheduleId", scheduleId);
		params.put("boardId", boardId);
		
		return boardDao.validate(params) == 1 ? true : false; 
	}

	@Override
	@Transactional
	public boolean deleteBoard(int scheduleId, int boardId) {
		Map<String, Integer> params = new HashMap<>();
		
		params.put("scheduleId", scheduleId);
		params.put("boardId", boardId);
		
		return boardDao.deleteBoard(params);
	}

	@Override
	@Transactional
	public void addComment(int boardId) {
		boardDao.increaseCommentCnt(boardId);
	}

	@Override
	@Transactional
	public void deleteComment(int boardId) {
		boardDao.decreaseCommentCnt(boardId);
		
	}
	
}
