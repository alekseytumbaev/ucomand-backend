package com.example.ucomandbackend.ad;

import com.example.ucomandbackend.util.SortDto;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@UtilityClass
public class AdSorter {

    /**
     * Поля по которым можно сортировать через запятую. Нужно для описания в open api
     */
    public final String AVAILABLE_SORT_DESCRIPTION = "user.age, creationDate";

    private final Map<String, String> sortingMap = Map.of(
            "user.age", "user_age",
            "creationDate", "creationDate"
    );

    public Sort sortBy(Collection<SortDto> sortDtos) {
        List<Sort.Order> orders = new ArrayList<>();
        for (SortDto sortDto : sortDtos) {
            if (sortingMap.containsKey(sortDto.getSortBy())) {
                orders.add(new Sort.Order(getDirection(sortDto), sortingMap.get(sortDto.getSortBy())));
            }
        }

        return Sort.by(orders);
    }

    private Sort.Direction getDirection(SortDto sortDto) {
        return sortDto.isDesc() ? Sort.Direction.DESC : Sort.Direction.ASC;
    }
}
