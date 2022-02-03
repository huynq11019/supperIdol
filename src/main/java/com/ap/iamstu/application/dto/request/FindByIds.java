package com.ap.iamstu.application.dto.request;

import com.ap.iamstu.infrastructure.support.query.request.Request;
import com.ap.iamstu.infrastructure.support.validate.ValidateUUID;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class FindByIds extends Request {
    private List<@ValidateUUID String> ids;
}
