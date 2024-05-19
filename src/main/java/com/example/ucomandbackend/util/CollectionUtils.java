package com.example.ucomandbackend.util;

import lombok.experimental.UtilityClass;

import java.util.Collection;

@UtilityClass
public class CollectionUtils {
    public boolean notEmpty(Collection<?> collection) {
        return collection != null && !collection.isEmpty();
    }
}
