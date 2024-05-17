package com.thunder.model.dao;

import java.util.List;
import java.util.Map;

import com.thunder.model.dto.Board;

public interface BoardDao {

	List<Board> selectAllBoard(int scheduleId);

	Board selectBoard(Map<String, Integer> map);

	int insertBoard(Board board);

	int updateBoard(Board board);

	int validate(Map<String, Object> params);

	boolean deleteBoard(Map<String, Integer> params);

	void increaseCommentCnt(int boardId);

	void decreaseCommentCnt(int boardId);

}
