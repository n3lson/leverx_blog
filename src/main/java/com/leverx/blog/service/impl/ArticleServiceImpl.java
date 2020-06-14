package com.leverx.blog.service.impl;

import com.leverx.blog.converter.impl.ArticleConverter;
import com.leverx.blog.converter.impl.TagConverter;
import com.leverx.blog.dto.ArticleDTO;
import com.leverx.blog.dto.TagDTO;
import com.leverx.blog.entity.Article;
import com.leverx.blog.entity.Tag;
import com.leverx.blog.repo.ArticleRepository;
import com.leverx.blog.repo.TagRepository;
import com.leverx.blog.service.ArticleService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepo;
    private final TagRepository tagRepo;
    private final ArticleConverter articleConverter;
    private final TagConverter tagConverter;

    public ArticleServiceImpl(ArticleRepository articleRepo,
                              TagRepository tagRepo,
                              ArticleConverter articleConverter,
                              TagConverter tagConverter) {
        this.articleRepo = articleRepo;
        this.tagRepo = tagRepo;
        this.articleConverter = articleConverter;
        this.tagConverter = tagConverter;
    }

    private void setTags(List<String> tagNames, Article article) {
        List<Tag> tags = new ArrayList<>();
        for (String tagName : tagNames) {
            Optional<Tag> tag = tagRepo.getTagByName(tagName);
            if (tag.isPresent()) {
                tags.add(tag.get());
            } else {
                tags.add(tagConverter.toEntity(new TagDTO(null, tagName)));
            }
        }
        article.setTags(tags);

    }

    @Override
    public Optional<ArticleDTO> editArticle(Integer id, ArticleDTO article, Integer userId) {
        Optional<Article> edited = articleRepo.findById(id);
        if (edited.isPresent()) {
            if (edited.get().getUser().getId().equals(userId)) {
                edited.get().setStatus(article.getStatus());
                edited.get().setTitle(article.getTitle());
                edited.get().setText(article.getText());
                setTags(article.getTags(), edited.get());
                articleRepo.save(edited.get());
                return Optional.of(articleConverter.toDTO(edited.get()));
            }
        }
        return Optional.empty();
    }

    @Override
    public ArticleDTO save(ArticleDTO articleDTO) {
        Article article = articleConverter.toEntity(articleDTO);
        setTags(articleDTO.getTags(), article);
        return articleConverter.toDTO(articleRepo.save(article));
    }

    @Override
    public List<ArticleDTO> getPublicArticles(Integer userId,
                                              int skip,
                                              int limit,
                                              String sortBy,
                                              Sort.Direction order) {
        if (order == Sort.Direction.DESC) {
            return articleConverter.entitiesToDTO(articleRepo.getArticlesDESC(userId, skip, limit, sortBy));
        } else {
            return articleConverter.entitiesToDTO(articleRepo.getArticlesASC(userId, skip, limit, sortBy));
        }
    }

    @Override
    public List<ArticleDTO> getMyArticles(Integer id) {
        return articleConverter.entitiesToDTO(articleRepo.getArticlesByUserId(id));
    }

    @Override
    public void delete(Integer id) {
        articleRepo.deleteById(id);
    }
}
