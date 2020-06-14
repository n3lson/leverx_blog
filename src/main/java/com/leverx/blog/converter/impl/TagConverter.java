package com.leverx.blog.converter.impl;

import com.leverx.blog.converter.Convertible;
import com.leverx.blog.dto.TagDTO;
import com.leverx.blog.entity.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagConverter implements Convertible<Tag, TagDTO> {
    @Override
    public Tag toEntity(TagDTO dto) {
        return new Tag(dto);
    }

    @Override
    public TagDTO toDTO(Tag entity) {
        return new TagDTO(entity);
    }
}
