package com.ap.iamstu.infrastructure.persistence.query;

import com.ap.iamstu.infrastructure.support.query.PagingQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class ClassSearchQuery extends PagingQuery {
    private String keyword;
}
