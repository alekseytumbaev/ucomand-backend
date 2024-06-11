package com.example.ucomandbackend.tag;

import com.example.ucomandbackend.tag.dto.TagType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findAllByTypeIn(List<TagType> types);
}
