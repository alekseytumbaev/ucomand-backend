package com.example.ucomandbackend.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageableDto {
    private int page;
    private int size;
    private Collection<SortDto> sorts;

    public PageableDto(int page, int size) {
        this(page, size, List.of());
    }
}

