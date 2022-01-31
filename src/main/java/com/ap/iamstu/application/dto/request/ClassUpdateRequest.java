package com.ap.iamstu.application.dto.request;

import com.ap.iamstu.infrastructure.support.query.request.Request;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ClassUpdateRequest extends Request {
    private String code;
    private String name;
    private String description;
    private String fileId;
}
