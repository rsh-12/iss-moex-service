package ru.task.iss.service.impl;
/*
 * Date: 4/24/21
 * Time: 3:16 PM
 * */

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.task.iss.entity.History;
import ru.task.iss.entity.Security;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

abstract class AbstractServiceClass {

    // List that contains all Security, History fields for the sorting validation
    final List<Field> SECURITY_FIELDS = Arrays.asList(Security.class.getDeclaredFields());
    final List<Field> HISTORY_FIELDS = Arrays.asList(History.class.getDeclaredFields());

    PageRequest getPageRequest(Integer pageNo, Integer pageSize, String sort, final List<Field> FIELDS) {
        String sortBy = getSortAsString(sort);
        String finalSortBy = sortBy;

        // If there is no such field or the name is incorrect, set the default to secId
        boolean isValid = FIELDS.stream().anyMatch(field -> field.getName().equals(finalSortBy));
        if (!isValid) {
            sortBy = "secId";
        }

        return PageRequest.of(
                pageNo, pageSize, Sort.by(getSortDirection(sort), sortBy));
    }

    /* Define a sort direction */
    Sort.Direction getSortDirection(String sort) {
        if (sort.contains(",asc")) return Sort.Direction.ASC;
        return Sort.Direction.DESC;
    }

    /* Remove 'asc' */
    String getSortAsString(String sort) {
        if (sort.contains(",")) return sort.split(",")[0];
        return sort;
    }
}
