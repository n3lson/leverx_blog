package com.leverx.blog.repo;

import com.leverx.blog.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
    List<Article> getArticlesByUserId(Integer id);

    @Query(value =
            "SELECT * FROM articles " +
            "WHERE user_id = ?1 AND status = 0 " +
            "ORDER BY ?4 " +
            "LIMIT ?2, ?3",
            nativeQuery = true)
    List<Article> getArticlesASC(Integer userId,
                                 int skip,
                                 int limit,
                                 String sortBy);
    @Query(value =
            "SELECT * FROM articles " +
            "WHERE user_id = ?1 AND status = 0 " +
            "ORDER BY ?4 " +
            "DESC LIMIT ?2, ?3",
            nativeQuery = true)
    List<Article> getArticlesDESC(Integer userId,
                                 int skip,
                                 int limit,
                                 String sortBy);
}
