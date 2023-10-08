package com.example.demo.service;

import com.example.demo.model.dto.CommentDTO;
import com.example.demo.model.entity.Comment;
import com.example.demo.model.entity.User;
import com.example.demo.model.vo.CommentVO;

import java.util.List;

public interface CommentService {
    /**
     *
     *
     * @param topicid
     * @return {@link Comment}
     */
    List<CommentVO> getCommentsByTopicID(String topicid);

    Comment create(CommentDTO dto, User principal);

    void deleteCommentByPostId(String id);

    void delete(String userId, String commentId);
}
