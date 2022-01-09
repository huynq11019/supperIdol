package com.ap.iamstu.infrastructure.support.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class PagingQuery extends Query {
    public static final String ASC_SYMBOL = "asc";
    public static final String DESC_SYMBOL = "desc";

    protected int pageIndex = 1;

    protected int pageSize = 30;

    protected String sortBy;

    private String keyword;
}
