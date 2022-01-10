/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ap.iamstu.application.sercurity.handler;

import com.ap.iamstu.application.service.impl.UserLoginService;
import com.ap.iamstu.infrastructure.persistence.entity.UserLoginEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;

/**
 * @author ngodz
 */
@RequiredArgsConstructor
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    private final UserLoginService userLoginService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // Save authentication failure to user login table
        UserLoginEntity loginLog = UserLoginEntity.builder().username(authentication.getName()).ip(request.getRemoteAddr())
                .loginTime(Instant.now()).success(true).description("MESSAGE_LOGIN_SUCCESSFUL")
                .build();

        userLoginService.save(loginLog);

    }
}
