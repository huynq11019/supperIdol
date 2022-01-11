package com.ap.iamstu.presentation.impl;

import com.ap.iamstu.application.dto.request.*;
import com.ap.iamstu.application.dto.response.AuthToken;
import com.ap.iamstu.application.sercurity.UserAuthority;
import com.ap.iamstu.application.sercurity.request.LoginRequest;
import com.ap.iamstu.application.service.AccountService;
import com.ap.iamstu.domain.User;
import com.ap.iamstu.infrastructure.support.query.response.Response;
import com.ap.iamstu.presentation.AccountResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.RedirectView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccountResourceImpl implements AccountResource {
    @Value("${app.iam.deep-link-reset-password}")
    private String urlResetPassword;

    private final AccountService accountService;

    public AccountResourceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public Response<User> myProfile() {
        return null;
    }

    @Override
    public Response<User> updateProfile(UserUpdateProfileRequest request) {
        return null;
    }

    @Override
    public Response<User> changePassword(UserChangePasswordRequest request, HttpServletRequest httpServletRequest) {
        return null;
    }

    @Override
    public Response<AuthToken> authenticate(LoginRequest request) {
        return null;
    }

    @Override
    public Response<AuthToken> refreshToken(RefreshTokenRequest request) {
        return null;
    }

    @Override
    public Response<User> register(UserRegisterRequest request) {
        return null;
    }

    @Override
    public Response<String> me() {
        return null;
    }

    @Override
    public Response<UserAuthority> myAuthorities() {
        return null;
    }

    @Override
    public Response<Boolean> logout(LogoutRevokeRequest logoutRevokeRequest) {
        return null;
    }

    @Override
    public Response<Boolean> initResetPassword(EmailForgotPasswordRequest request) throws MessagingException {
        return null;
    }

    @Override
    public Response<Boolean> resetPassword(ForgotPasswordRequest request) {
        return null;
    }

    @Override
    public RedirectView redirect(String token, HttpServletResponse response) {
        return null;
    }
}
