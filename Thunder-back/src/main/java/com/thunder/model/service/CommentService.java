package com.thunder.model.service;

import java.util.List;

import com.thunder.model.dto.Comment;

public interface CommentService {

	List<Comment> getAllComment(int boardId);

	boolean registComment(int boardId, String userId, Comment comment);

	boolean validateRegist(String userId, int commentId);

	boolean deleteComment(int commentId);

}
