package com.leverx.blog.service;

import com.leverx.blog.dto.CommentDTO;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    CommentDTO save(Integer articleId, CommentDTO comment);
    void delete(Integer articleId, Integer commentId, Integer userId);
    Optional<CommentDTO> getComment(Integer articleId, Integer commentId);
    List<CommentDTO> getComments(Integer articleId,
                                 Integer userId,
                                 int skip,
                                 int limit,
                                 String sortBy,
                                 Sort.Direction order);
}
