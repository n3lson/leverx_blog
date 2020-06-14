package com.leverx.blog.repo;

import com.leverx.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query(value =
            "SELECT * FROM comments " +
            "WHERE article_id = ?1 AND user_id = ?2 " +
            "ORDER BY ?5 " +
            "LIMIT ?3, ?4",
            nativeQuery = true)
    List<Comment> getCommentsASC(Integer articleId,
                                 Integer userId,
                                 int skip,
                                 int limit,
                                 String sortBy);
    @Query(value =
            "SELECT * FROM comments " +
            "WHERE article_id = ?1 AND user_id = ?2 " +
            "ORDER BY ?5 " +
            "DESC LIMIT ?3, ?4",
            nativeQuery = true)
    List<Comment> getCommentsDESC(Integer articleId,
                                          Integer userId,
                                          int skip,
                                          int limit,
                                          String sortBy);

    Optional<Comment> getCommentByArticleIdAndId(Integer postId, Integer commentId);
}
