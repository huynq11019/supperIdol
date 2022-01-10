package com.ap.iamstu.application.dto.request;

import com.ap.iamstu.infrastructure.support.query.request.Request;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagingRequest extends Request {

    public static final String ASC_SYMBOL = "asc";
    public static final String DESC_SYMBOL = "desc";

    @Min(value = 1, message = "Page index must be greater than 0")
    @Max(value = 100000, message = "Page index be less than 100000")
    protected int pageIndex = 1;

    @Min(value = 1, message = "Page size must be greater than 0")
    @Max(value = 1000, message = "Page size must be less than or equal to 1000")
    protected int pageSize = 30;

    protected String sortBy;
}
