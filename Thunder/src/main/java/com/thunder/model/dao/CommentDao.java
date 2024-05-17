package com.thunder.model.dao;

import java.util.List;
import java.util.Map;

import com.thunder.model.dto.Comment;

public interface CommentDao {

	List<Comment> selectAllComment(int boardId);

	int insertComment(Comment comment);

	boolean validate(Map<String, Object> params);

	int deleteComment(int commentId);

}
