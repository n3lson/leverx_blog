package com.leverx.blog.converter.impl;

import com.leverx.blog.converter.Convertible;
import com.leverx.blog.dto.ArticleDTO;
import com.leverx.blog.entity.Article;
import com.leverx.blog.repo.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class ArticleConverter implements Convertible<Article, ArticleDTO> {
    private final UserRepository userRepo;

    public ArticleConverter(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public Article toEntity(ArticleDTO dto) {
        Article article = new Article(dto);
        userRepo.findById(dto.getUserId()).ifPresent(article::setUser);
        return article;
    }

    @Override
    public ArticleDTO toDTO(Article entity) {
        return new ArticleDTO(entity);
    }
}
