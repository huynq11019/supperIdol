package com.ap.iamstu.application.mapper.util;

import com.ap.iamstu.application.dto.request.PagingRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class PageableMapperUtil {

    public static Pageable toPageable(PagingRequest criteria) {
        List<Sort.Order> orders = new ArrayList<>();
        if (StringUtils.hasLength(criteria.getSortBy())) {
            String sortStr = criteria.getSortBy().trim();
            if (StringUtils.hasLength(sortStr)) {
                if (sortStr.contains(".")) {
                    String[] sortParams = sortStr.split("\\.");
                    if (sortStr.endsWith(PagingRequest.DESC_SYMBOL) && StringUtils.hasLength(sortParams[0])) {
                        orders.add(Sort.Order.desc(sortParams[0]));
                    } else if (sortStr.endsWith(PagingRequest.ASC_SYMBOL) && StringUtils.hasLength(sortParams[0])) {
                        orders.add(Sort.Order.asc(sortParams[0]));
                    }
                } else {
                    orders.add(Sort.Order.asc(sortStr));
                }
            }
        }
        return PageRequest.of(criteria.getPageIndex() - 1, criteria.getPageSize(), Sort.by(orders));
    }
}
