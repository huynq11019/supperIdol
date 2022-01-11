package com.ap.iamstu.application.dto.request;

import com.ap.iamstu.infrastructure.support.query.request.Request;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
public class RefreshTokenRequest extends Request {

    @NotBlank(message = "REFRESH_TOKEN_REQUIRED")
    private String refreshToken;
}
