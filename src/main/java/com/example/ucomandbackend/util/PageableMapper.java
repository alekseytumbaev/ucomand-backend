package com.example.ucomandbackend.util;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@UtilityClass
public class PageableMapper {

    public PageableDto toPageableDto(int page, int size) {
        return new PageableDto(page, size);
    }

    public PageableDto toPageableDto(int page, int size, Collection<String> sortStrings) {
        List<SortDto> sortDtos = sortStrings.stream().map(SortDto::new).toList();
        return new PageableDto(page, size, sortDtos);
    }

    public Pageable toPageable(PageableDto pageableDto) {
        return PageRequest.of(pageableDto.getPage(), pageableDto.getSize());
    }

    public Pageable toPageable(PageableDto pageableDto, Function<Collection<SortDto>, Sort> sorter) {
        return PageRequest.of(pageableDto.getPage(), pageableDto.getSize(), sorter.apply(pageableDto.getSorts()));
    }
}