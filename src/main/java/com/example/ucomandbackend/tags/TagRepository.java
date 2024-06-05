package com.example.ucomandbackend.tags;

import com.example.ucomandbackend.tags.dto.TagType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findAllByTypeIn(List<TagType> types);
}
