package com.example.ucomandbackend.util;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SortDto {
    @NotBlank
    private String sortBy;

    @Schema(defaultValue = "false")
    private boolean desc;

    /**
     * @param sortString напирмер: "user.age_desc" или "user.age", по умолчанию сортировка asc
     */
    public SortDto(String sortString) {
        String[] pathAndDirection = sortString.strip().split("_");
        this.sortBy = pathAndDirection[0];
        this.desc = pathAndDirection.length > 1 && pathAndDirection[1].equalsIgnoreCase("desc");
    }
}
