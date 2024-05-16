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
	
}
