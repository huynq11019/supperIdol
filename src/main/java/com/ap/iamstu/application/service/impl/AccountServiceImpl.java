package com.ap.iamstu.application.service.impl;

import com.ap.iamstu.application.dto.request.*;
import com.ap.iamstu.application.dto.response.AuthToken;
import com.ap.iamstu.application.mapper.AutoMapper;
import com.ap.iamstu.application.sercurity.TokenProvider;
import com.ap.iamstu.application.sercurity.UserAuthority;
import com.ap.iamstu.application.sercurity.request.LoginRequest;
import com.ap.iamstu.application.service.AccountService;
import com.ap.iamstu.application.service.AuthorityService;
import com.ap.iamstu.application.service.UserService;
import com.ap.iamstu.domain.User;
import com.ap.iamstu.infrastructure.persistence.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    private static final String TITLE_KEY = "EMAIL_RESET_PASSWORD";
    private static final String TEMPLATE_NAME = "mail/passwordResetEmail";
    private final UserService userService;
    private final UserRepository userRepository;
    private final AutoMapper autoMapper;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthorityService authorityService;

    public AccountServiceImpl(UserService userService, UserRepository userRepository, AutoMapper autoMapper, PasswordEncoder passwordEncoder, TokenProvider tokenProvider, AuthorityService authorityService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.autoMapper = autoMapper;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.authorityService = authorityService;
    }

    @Override
    public AuthToken login(LoginRequest request) {
        return null;
    }

    @Override
    public AuthToken refreshToken(RefreshTokenRequest request) {
        return null;
    }

    @Override
    public User register(UserRegisterRequest request) {
        return null;
    }

    @Override
    public User myProfile() {
        return null;
    }

    @Override
    public User updateProfile(UserUpdateProfileRequest request) {
        return null;
    }

    @Override
    public User changePassword(UserChangePasswordRequest request, HttpServletRequest httpServletRequest) {
        return null;
    }

    @Override
    public String currentUser() {
        return null;
    }

    @Override
    public UserAuthority myAuthorities() {
        return null;
    }

    @Override
    public void logout(LogoutRevokeRequest logoutRevokeRequest) {

    }

    @Override
    public void forgotPassword(EmailForgotPasswordRequest request) throws MessagingException {

    }

    @Override
    public void resetPassword(ForgotPasswordRequest request) {

    }
}
