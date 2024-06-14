package com.example.ucomandbackend.util;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@UtilityClass
public class SortUtils {

    public Sort sortBy(Collection<SortDto> sortDtos, Map<String, String> sortingMap) {
        if (CollectionUtils.isEmpty(sortDtos) || CollectionUtils.isEmpty(sortingMap)) {
            return Sort.unsorted();
        }

        List<Sort.Order> orders = new ArrayList<>();
        for (SortDto sortDto : sortDtos) {
            if (sortingMap.containsKey(sortDto.getSortBy())) {
                orders.add(new Sort.Order(getSortDirection(sortDto), sortingMap.get(sortDto.getSortBy())));
            }
        }

        return Sort.by(orders);
    }

    public Sort.Direction getSortDirection(SortDto sortDto) {
        return sortDto.isDesc() ? Sort.Direction.DESC : Sort.Direction.ASC;
    }
}
