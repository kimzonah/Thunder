package com.thunder.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thunder.model.dto.Board;

public interface BoardDao {

	List<Board> selectAllBoard(int scheduleId);

}
