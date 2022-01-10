package com.ap.iamstu.application.sercurity;

public interface TokenCacheService {
    String INVALID_TOKEN_CACHE = "invalid-access-token";
    String INVALID_REFRESH_TOKEN_CACHE = "invalid-refresh-token";

    boolean isExisted(String cacheName, String token);

    void invalidToken(String token);

    void invalidRefreshToken(String refreshToken);

    default boolean isInvalidToken(String token) {
        return isExisted(INVALID_TOKEN_CACHE, token);
    }

    default boolean isInvalidRefreshToken(String refreshToken) {
        return isExisted(INVALID_REFRESH_TOKEN_CACHE, refreshToken);
    }
}
