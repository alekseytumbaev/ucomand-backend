package com.example.ucomandbackend.util;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Pageable;

@UtilityClass
public class PageableMapper {

    public PageableDto toPageableDto(int page, int size) {
        return new PageableDto(page, size);
    }

    public Pageable toPageable(PageableDto pageableDto) {
        return Pageable.ofSize(pageableDto.getSize())
                .withPage(pageableDto.getPage());
    }
}