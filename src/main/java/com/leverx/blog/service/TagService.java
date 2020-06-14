package com.leverx.blog.service;

import com.leverx.blog.dto.ArticleDTO;
import com.leverx.blog.dto.TagDTO;
import com.leverx.blog.entity.Tag;
import com.leverx.blog.entity.TagsCloud;

import java.util.List;

public interface TagService {
    List<ArticleDTO> getArticlesByTags(List<String> names);
    List<TagsCloud> getTagsCloud(List<String> names);
    Tag save(TagDTO article);
}
