package com.ap.iamstu.application.sercurity.impl;

import com.ap.iamstu.application.sercurity.TokenCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class TokenCacheServiceImpl implements TokenCacheService {
    private final CacheManager cacheManager;

    @Autowired
    public TokenCacheServiceImpl(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public boolean isExisted(String cacheName, String token) {
        try {
            return Optional.ofNullable(cacheManager.getCache(cacheName))
                    .map(cache -> cache.get(token))
                    .filter(valueWrapper -> Objects.nonNull(valueWrapper.get()))
                    .isPresent();
        } catch (Exception ex) {
            log.error("Cache manager error", ex);
            return false;
        }
    }

    @Override
    public void invalidToken(String token) {
        try {
            Optional.ofNullable(cacheManager.getCache(INVALID_TOKEN_CACHE))
                    .ifPresent(cache -> cache.put(token, 1L));
        } catch (Exception ex) {
            log.error("Cache manager error", ex);
        }
    }

    @Override
    public void invalidRefreshToken(String refreshToken) {
        try {
            Optional.ofNullable(cacheManager.getCache(INVALID_REFRESH_TOKEN_CACHE))
                    .ifPresent(cache -> cache.put(refreshToken, 1L));
        } catch (Exception ex) {
            log.error("Cache manager error", ex);
        }
    }
}
