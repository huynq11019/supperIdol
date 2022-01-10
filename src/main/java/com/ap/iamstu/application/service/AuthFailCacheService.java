package com.ap.iamstu.application.service;


import com.ap.iamstu.infrastructure.support.error.BadRequestError;

public interface AuthFailCacheService {
    String LOGIN_FAIL_COUNT_CACHE = "auth-login-fail-count";
    String BLOCKED_USER_LOGIN_CACHE = "auth-blocked-user";

    Integer REACH_MAX_LOGIN_FAIL_COUNT = 5;
    Integer REACH_WARNING_BEFORE_BLOCK_LOGIN_FAIL_COUNT = 3;

    BadRequestError checkLoginFail(String username);

    boolean isBlockedUser(String username);

    void resetLoginFail(String username);
}
