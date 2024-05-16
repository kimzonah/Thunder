package com.thunder.model.dao;

import java.util.List;

import com.thunder.model.dto.Board;

public interface BoardDao {

	List<Board> selectAllBoard(int scheduleId);

}
