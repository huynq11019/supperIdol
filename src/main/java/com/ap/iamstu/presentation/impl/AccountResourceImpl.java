package com.ap.iamstu.presentation.impl;

import com.ap.iamstu.application.dto.request.*;
import com.ap.iamstu.application.dto.response.AuthToken;
import com.ap.iamstu.application.sercurity.UserAuthority;
import com.ap.iamstu.application.sercurity.request.LoginRequest;
import com.ap.iamstu.application.service.AccountService;
import com.ap.iamstu.domain.User;
import com.ap.iamstu.infrastructure.support.query.response.Response;
import com.ap.iamstu.infrastructure.support.util.Const;
import com.ap.iamstu.infrastructure.support.util.HttpUtil;
import com.ap.iamstu.infrastructure.support.util.StringPool;
import com.ap.iamstu.infrastructure.support.util.StringUtil;
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
        return Response.of(accountService.myProfile());
    }

    @Override
    public Response<User> updateProfile(UserUpdateProfileRequest request) {
        return Response.of(
                accountService.meUpdateProfile(request)
        );
    }

    @Override
    public Response<User> changePassword(UserChangePasswordRequest request, HttpServletRequest httpServletRequest) {
        return Response.of(accountService.changePassword(request, httpServletRequest));
    }

    @Override
    public Response<AuthToken> authenticate(LoginRequest request) {
        return Response.of(accountService.login(request));
    }

    @Override
    public Response<AuthToken> refreshToken(RefreshTokenRequest request) {
        return Response.of(accountService.refreshToken(request));
    }

    @Override
    public Response<User> register(UserRegisterRequest request) {
        return Response.of(accountService.register(request));
    }

    @Override
    public Response<String> me() {
        return Response.of(accountService.currentUser());
    }

    @Override
    public Response<UserAuthority> myAuthorities() {
        return Response.of(accountService.myAuthorities());
    }

    @Override
    public Response<Boolean> logout(LogoutRevokeRequest logoutRevokeRequest) {
        accountService.logout(logoutRevokeRequest);
        return Response.of(Boolean.TRUE);
    }

    @Override
    public Response<Boolean> initResetPassword(EmailForgotPasswordRequest request) throws MessagingException {
        accountService.forgotPassword(request);
        return Response.of(Boolean.TRUE);
    }

    @Override
    public Response<Boolean> resetPassword(ForgotPasswordRequest request) {
        accountService.resetPassword(request);
        return Response.of(Boolean.TRUE);
    }

    @Override
    public RedirectView redirect(String token, HttpServletResponse response) {
        String url;
        if (!StringUtil.isBlank(urlResetPassword)) {
            url = String.format(urlResetPassword, token);
        } else {
            url = Const.DEFAULT_LINK_RESET_PASSWORD;
        }
        response.setHeader(StringPool.LOCATION, url);
        response.setStatus(HttpUtil.REDIRECTION_CODE);
        return new RedirectView(url);
    }
}
