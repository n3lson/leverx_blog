package com.leverx.blog.converter.impl;

import com.leverx.blog.converter.Convertible;
import com.leverx.blog.dto.CommentDTO;
import com.leverx.blog.entity.Comment;
import com.leverx.blog.repo.ArticleRepository;
import com.leverx.blog.repo.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter implements Convertible<Comment, CommentDTO> {
    private final ArticleRepository articleRepo;
    private final UserRepository userRepo;

    public CommentConverter(ArticleRepository articleRepo, UserRepository userRepo) {
        this.articleRepo = articleRepo;
        this.userRepo = userRepo;
    }

    @Override
    public Comment toEntity(CommentDTO dto) {
        Comment comment = new Comment(dto);
        articleRepo.findById(dto.getArticleId()).ifPresent(comment::setArticle);
        userRepo.findById(dto.getUserId()).ifPresent(comment::setUser);
        return comment;
    }

    @Override
    public CommentDTO toDTO(Comment entity) {
        return new CommentDTO(entity);
    }
}
