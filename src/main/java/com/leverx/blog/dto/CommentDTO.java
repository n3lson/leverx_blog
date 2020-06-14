package com.leverx.blog.dto;

import com.leverx.blog.entity.Comment;

import java.util.Date;

public class CommentDTO {
    private Integer id;
    private Integer articleId;
    private Integer userId;
    private String message;
    private Date createdAt;

    public CommentDTO() { }

    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.articleId = comment.getArticle().getId();
        this.userId = comment.getUser().getId();
        this.message = comment.getMessage();
        this.createdAt = comment.getCreatedAt();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
