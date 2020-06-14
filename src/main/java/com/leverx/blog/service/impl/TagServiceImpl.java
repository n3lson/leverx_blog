package com.leverx.blog.service.impl;

import com.leverx.blog.converter.impl.ArticleConverter;
import com.leverx.blog.converter.impl.TagConverter;
import com.leverx.blog.dto.ArticleDTO;
import com.leverx.blog.dto.TagDTO;
import com.leverx.blog.entity.Tag;
import com.leverx.blog.entity.TagsCloud;
import com.leverx.blog.repo.TagRepository;
import com.leverx.blog.service.TagService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository repo;
    private final ArticleConverter articleConverter;
    private final TagConverter tagConverter;

    public TagServiceImpl(TagRepository repo, ArticleConverter articleConverter, TagConverter tagConverter) {
        this.repo = repo;
        this.articleConverter = articleConverter;
        this.tagConverter = tagConverter;
    }

    @Override
    public List<ArticleDTO> getArticlesByTags(List<String> names) {
        List<ArticleDTO> articles = new ArrayList<>();
        for (String name : names) {
            Optional<Tag> tag = repo.getTagByName(name);
            tag.ifPresent(t -> articles.addAll(articleConverter.entitiesToDTO(t.getArticles())));
        }
        return articles;
    }

    @Override
    public List<TagsCloud> getTagsCloud(List<String> names) {
        List<TagsCloud> tagsCloudList = new ArrayList<>();
        for (String name : names) {
            Optional<Tag> tag = repo.getTagByName(name);
            tag.ifPresent(t -> tagsCloudList.add(new TagsCloud(t.getName(), t.getArticles().size())));
        }
        return tagsCloudList;
    }

    @Override
    public Tag save(TagDTO tag) {
        return repo.save(tagConverter.toEntity(tag));
    }
}
