package com.leverx.blog.dto;

import com.leverx.blog.entity.Article;
import com.leverx.blog.entity.ArticleStatus;
import com.leverx.blog.entity.Tag;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticleDTO {
    private Integer id;
    private Integer userId;
    private String title;
    private String text;
    private ArticleStatus status;
    private List<String> tags;
    private Date createdAt;
    private Date updatedAt;

    public ArticleDTO() { }

    public ArticleDTO(Article article) {
        this.id = article.getId();
        this.userId = article.getUser().getId();
        this.title = article.getTitle();
        this.text = article.getText();
        this.status = article.getStatus();
        this.createdAt = article.getCreatedAt();
        this.updatedAt = article.getUpdatedAt();

        this.tags = new ArrayList<>();
        for (Tag tag : article.getTags()) {
            this.tags.add(tag.getName());
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArticleStatus getStatus() {
        return status;
    }

    public void setStatus(ArticleStatus status) {
        this.status = status;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
