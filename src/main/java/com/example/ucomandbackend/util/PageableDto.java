package com.example.ucomandbackend.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageableDto {
    private int page;
    private int size;
}

