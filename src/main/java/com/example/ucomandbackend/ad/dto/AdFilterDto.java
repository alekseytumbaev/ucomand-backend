package com.example.ucomandbackend.ad.dto;

import com.example.ucomandbackend.tags.dto.TagDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdFilterDto {

    private Set<TagDto> tags;

}
