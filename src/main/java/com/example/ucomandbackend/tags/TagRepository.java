package com.example.ucomandbackend.tags;

import com.example.ucomandbackend.tags.dto.TagType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findAllByTypeIn(List<TagType> types);

    List<Tag> findAllByNameIn(List<String> tagNames);

    @Query(value = """
            SELECT *
            FROM tags
            JOIN resumes_tags ON resumes_tags.tag_id = tags.id
            WHERE resumes_tags.resume_id = :resumeId
            """, nativeQuery = true)
    Set<Tag> findAllByResumeId(Long resumeId);

    Optional<Tag> findByName(String name);
}
