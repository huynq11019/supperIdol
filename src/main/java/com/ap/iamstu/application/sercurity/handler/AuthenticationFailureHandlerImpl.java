/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ap.iamstu.application.sercurity.handler;

import com.ap.iamstu.application.service.UserLoginService;
import com.ap.iamstu.infrastructure.persistence.entity.UserLoginEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;

/**
 * @author huynq1808
 */
@RequiredArgsConstructor
@Component
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

    private final UserLoginService userLoginService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        // Save authentication failure to user login table
        UserLoginEntity loginLog = UserLoginEntity.builder().username(request.getUserPrincipal().getName())
                .ip(request.getRemoteAddr()).loginTime(Instant.now()).success(false).description(exception.getMessage())
                .build();

        userLoginService.save(loginLog);

    }

}
