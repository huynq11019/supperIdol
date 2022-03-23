package com.ap.iamstu.application.service.impl;

import com.ap.iamstu.application.service.AuthFailCacheService;
import com.ap.iamstu.infrastructure.support.error.BadRequestError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
@Slf4j
@Service
public class AuthFailCacheServiceImpl implements AuthFailCacheService {
    private final CacheManager cacheManager;

    @Autowired
    public AuthFailCacheServiceImpl(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    private boolean isExisted(String cacheName, String username) {
        try {
            return Optional.ofNullable(cacheManager.getCache(cacheName))
                    .map(cache -> cache.get(username))
                    .filter(valueWrapper -> Objects.nonNull(valueWrapper.get()))
                    .isPresent();
        } catch (Exception ex) {
            log.error("Cache manager error", ex);
            return false;
        }
    }

    @Override
    public BadRequestError checkLoginFail(String username) {
        try {
            // check user đăng nhập sai lần đầu
            log.info("Check login fail for user {}", username);
            if (!isExisted(LOGIN_FAIL_COUNT_CACHE, username)) {
                log.info("User {} is not existed in cache", username);
                Optional.ofNullable(cacheManager.getCache(LOGIN_FAIL_COUNT_CACHE))
                        .ifPresent(cache -> cache.put(username, 1));
            } else {
                Integer loginFailCount = (Integer) cacheManager.getCache(LOGIN_FAIL_COUNT_CACHE).get(username).get();
                log.info("User {} login fail count is {}", username, loginFailCount);
                if (loginFailCount == null) {
                    Optional.ofNullable(cacheManager.getCache(LOGIN_FAIL_COUNT_CACHE))
                            .ifPresent(cache -> cache.put(username, 1));
                    return null;
                }

                if (loginFailCount > REACH_MAX_LOGIN_FAIL_COUNT) {
                    // login fail quá 5 lần thì đưa user vào danh sách block
                    log.info("User {} reach max login fail count", username);
                    Optional.ofNullable(cacheManager.getCache(BLOCKED_USER_LOGIN_CACHE))
                            .ifPresent(cache -> cache.put(username, 1));
                } else {
                    // nếu đăng nhập sai thì tăng số lần đăng nhập sai lên 1
                    final Integer newLoginFailCount = loginFailCount + 1;
                    Optional.ofNullable(cacheManager.getCache(LOGIN_FAIL_COUNT_CACHE))
                            .ifPresent(cache -> cache.put(username, newLoginFailCount));

                    if (Objects.equals(newLoginFailCount, REACH_MAX_LOGIN_FAIL_COUNT)) {
                        Optional.ofNullable(cacheManager.getCache(BLOCKED_USER_LOGIN_CACHE))
                                .ifPresent(cache -> cache.put(username, 1));
                        return BadRequestError.LOGIN_FAIL_BLOCK_ACCOUNT;
                    }
                    if (newLoginFailCount > REACH_WARNING_BEFORE_BLOCK_LOGIN_FAIL_COUNT) {
                        // nếu đăng nhập sai quá 3 lần thì bắt đầu cảnh báo
                        return BadRequestError.LOGIN_FAIL_WARNING_BEFORE_BLOCK;
                    }
                }
            }
        } catch (Exception ex) {
            log.error("Cache manager error", ex);
        }
        return null;
    }

    @Override
    public boolean isBlockedUser(String username) {
        return isExisted(BLOCKED_USER_LOGIN_CACHE, username);
    }

    @Override
    public void resetLoginFail(String username) {
        try {
            if (isExisted(LOGIN_FAIL_COUNT_CACHE, username) || isExisted(BLOCKED_USER_LOGIN_CACHE, username)) {
                Optional.ofNullable(cacheManager.getCache(LOGIN_FAIL_COUNT_CACHE))
                        .ifPresent(cache -> cache.evict(username));
                Optional.ofNullable(cacheManager.getCache(BLOCKED_USER_LOGIN_CACHE))
                        .ifPresent(cache -> cache.evict(username));
            }
        } catch (Exception ex) {
            log.error("Cache manager error", ex);
        }
    }
}
