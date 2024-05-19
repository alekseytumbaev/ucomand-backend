package com.example.ucomandbackend.tags;

import com.example.ucomandbackend.tags.dto.TagAvailabilityStatus;
import com.example.ucomandbackend.tags.dto.TagType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> getTagById(Long id);

    List<Tag> getAllByAvailabilityStatusInAndTypeIn(Pageable pageable,
                                                    List<TagAvailabilityStatus> statuses,
                                                    List<TagType> types);

    List<Tag> getAllByNameIn(List<String> tagNames);

    @Query(value = """
            SELECT *
            FROM tags
            JOIN resumes_tags ON resumes_tags.tag_id = tags.id
            WHERE resumes_tags.resume_id = :resumeId
            """, nativeQuery = true)
    Set<Tag> getTagsByResumeId(Long resumeId);

    Optional<Tag> getByName(String name);
}