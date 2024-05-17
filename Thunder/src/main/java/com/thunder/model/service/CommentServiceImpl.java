package com.thunder.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thunder.model.dao.CommentDao;
import com.thunder.model.dto.Comment;

@Service
public class CommentServiceImpl implements CommentService {

	private final CommentDao commentDao;
	
	@Autowired
	public CommentServiceImpl(CommentDao commentDao) {
		this.commentDao = commentDao;
	}
	
	@Override
	@Transactional
	public List<Comment> getAllComment(int boardId) {
		return commentDao.selectAllComment(boardId);
	}

	@Override
	@Transactional
	public boolean registComment(int boardId, String userId, Comment comment) {
		// boardId와 userId 세팅
		comment.setBoardId(boardId);
		comment.setUserId(userId);
		
		return commentDao.insertComment(comment) == 1 ? true : false;
	}

	@Override
	@Transactional
	public boolean validateRegist(String userId, int commentId) {
		// 검증을 위한 userId, boardId를 넘기기
		Map<String, Object> params = new HashMap<>();
		
		params.put("userId", userId);
		params.put("commentId", commentId);
		
		return commentDao.validate(params);
	}

	@Override
	@Transactional
	public boolean deleteComment(int commentId) {
		return commentDao.deleteBoard(commentId) == 1 ? true : false;
	}

}
