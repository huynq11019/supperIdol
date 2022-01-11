/*
 * UserLoginServiceImpl.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved.
 * This software is the confidential and proprietary information of Evotek
 */
package com.ap.iamstu.application.service.impl;

import com.ap.iamstu.application.service.UserLoginService;
import com.ap.iamstu.infrastructure.persistence.entity.UserLoginEntity;
import com.ap.iamstu.infrastructure.persistence.repository.UserLoginRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * 23/06/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserLoginServiceImpl implements UserLoginService {

    private final UserLoginRepository userLoginRepository;

    @Override
    public UserLoginEntity save(UserLoginEntity loginLog) {
        return userLoginRepository.save(loginLog);
    }

    @Override
    public void saveUserLogin(String username, String ip, boolean success, String description) {
        UserLoginEntity loginLog = UserLoginEntity.builder()//
                .username(username).ip(ip).loginTime(Instant.now()).success(success).description(description).build();

        userLoginRepository.save(loginLog);

        //
    }
}
