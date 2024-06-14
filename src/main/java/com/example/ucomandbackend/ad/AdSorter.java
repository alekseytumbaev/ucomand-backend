package com.example.ucomandbackend.ad;

import com.example.ucomandbackend.ad.dto.AdDto;
import com.example.ucomandbackend.user.User;
import com.example.ucomandbackend.user.dto.UserDto;
import com.example.ucomandbackend.util.SortDto;
import com.example.ucomandbackend.util.SortUtils;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Sort;

import java.util.Collection;
import java.util.Map;

@UtilityClass
public class AdSorter {

    /**
     * Поля по которым можно сортировать через запятую. Нужно для описания в open api.
     * Если использовать констонты, которые генерирует FieldNameConstants, выдает ошибку
     */
    public final String AVAILABLE_SORT_DESCRIPTION = "user.age, creationDate";

    private final Map<String, String> sortingMap = Map.of(
            AdDto.F.user + "." + UserDto.F.age, Ad.F.user + "_" + User.F.age,
            AdDto.F.creationDate, Ad.F.creationDate
    );

    public Sort sortBy(Collection<SortDto> sortDtos) {
        return SortUtils.sortBy(sortDtos, sortingMap);
    }
}
