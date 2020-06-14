package com.leverx.blog.service;

import com.leverx.blog.dto.ArticleDTO;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    ArticleDTO save(ArticleDTO article);
    void delete(Integer id);
    Optional<ArticleDTO> editArticle(Integer id, ArticleDTO article, Integer userId);
    List<ArticleDTO> getMyArticles(Integer id);
    List<ArticleDTO> getPublicArticles(Integer userId,
                                       int skip,
                                       int limit,
                                       String sortBy,
                                       Sort.Direction order);
}
