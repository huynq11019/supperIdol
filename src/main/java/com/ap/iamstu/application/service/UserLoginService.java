/*
 * UserLoginService.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved.
 * This software is the confidential and proprietary information of Evotek
 */
package com.ap.iamstu.application.service;


import com.ap.iamstu.infrastructure.persistence.entity.UserLoginEntity;

/**
 * 23/06/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
public interface UserLoginService {

    /**
     * @param loginLog
     */
    UserLoginEntity save(UserLoginEntity loginLog);
    
    void saveUserLogin(String username, String ip, boolean success, String description);
}
