package com.ap.iamstu.application.dto.request;

import com.ap.iamstu.infrastructure.support.query.request.Request;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogoutRevokeRequest extends Request {
    private String deviceId;

    private String deviceToken;

    private String refreshToken;

    private String userId;
}
