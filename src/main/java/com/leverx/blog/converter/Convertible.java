package com.leverx.blog.converter;

import java.util.ArrayList;
import java.util.List;

public interface Convertible<Entity, DTO> {
    Entity toEntity(DTO dto);
    DTO toDTO(Entity entity);

    default List<DTO> entitiesToDTO(List<Entity> entities) {
        List<DTO> dtoList = new ArrayList<>(entities.size());
        for (Entity entity : entities) {
            dtoList.add(toDTO(entity));
        }
        return dtoList;
    }
}
