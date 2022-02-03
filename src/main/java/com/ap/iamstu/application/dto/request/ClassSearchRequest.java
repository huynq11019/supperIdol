package com.ap.iamstu.application.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClassSearchRequest extends PagingRequest {
    private String keyword;
}
