package com.leverx.blog.repo;

import com.leverx.blog.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    Optional<Tag> getTagByName(String name);
}
