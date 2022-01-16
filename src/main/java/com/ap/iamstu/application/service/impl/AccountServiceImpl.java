package com.ap.iamstu.application.service.impl;

import com.ap.iamstu.application.dto.request.*;
import com.ap.iamstu.application.dto.response.AuthToken;
import com.ap.iamstu.application.mapper.AutoMapper;
import com.ap.iamstu.application.mapper.UserMapper;
import com.ap.iamstu.application.sercurity.AuthenticationProperties;
import com.ap.iamstu.application.sercurity.TokenProvider;
import com.ap.iamstu.application.sercurity.UserAuthentication;
import com.ap.iamstu.application.sercurity.UserAuthority;
import com.ap.iamstu.application.sercurity.request.LoginRequest;
import com.ap.iamstu.application.service.AccountService;
import com.ap.iamstu.application.service.AuthFailCacheService;
import com.ap.iamstu.application.service.AuthorityService;
import com.ap.iamstu.application.service.UserService;
import com.ap.iamstu.domain.User;
import com.ap.iamstu.domain.command.UserRegisterCmd;
import com.ap.iamstu.domain.command.UserUpdateCmd;
import com.ap.iamstu.infrastructure.persistence.entity.UserEntity;
import com.ap.iamstu.infrastructure.persistence.repository.UserRepository;
import com.ap.iamstu.infrastructure.support.enums.UserStatus;
import com.ap.iamstu.infrastructure.support.error.BadRequestError;
import com.ap.iamstu.infrastructure.support.error.NotFoundError;
import com.ap.iamstu.infrastructure.support.exeption.ResponseException;
import com.ap.iamstu.infrastructure.support.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Optional;

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
    private final AuthenticationProperties authenticationProperties;
    private final AuthorityService authorityService;
    private final AuthFailCacheService authFailCacheService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    public AccountServiceImpl(UserService userService, UserRepository userRepository, AutoMapper autoMapper, PasswordEncoder passwordEncoder, TokenProvider tokenProvider, AuthenticationProperties authenticationProperties, AuthorityService authorityService, AuthFailCacheService authFailCacheService,
                              AuthenticationManager authenticationManager, UserMapper userMapper) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.autoMapper = autoMapper;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.authenticationProperties = authenticationProperties;
        this.authorityService = authorityService;
        this.authFailCacheService = authFailCacheService;
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
    }


    @Override
    public AuthToken login(LoginRequest request) {
        // check account was locked
        if (authFailCacheService.isBlockedUser(request.getUsername())) {
            throw new ResponseException(BadRequestError.LOGIN_FAIL_BLOCK_ACCOUNT.getMessage(), BadRequestError.LOGIN_FAIL_BLOCK_ACCOUNT);
        }
        // check user
        Optional<UserEntity> optionalUserEntity = this.userRepository.findByUserName(request.getUsername());
        if (optionalUserEntity.isEmpty()) {
            BadRequestError error = authFailCacheService.checkLoginFail(request.getUsername());
            if (error == null) {
                throw new BadCredentialsException("Bad credential!"); // login sai 3 lần đầu tiên
            } else {
                throw new ResponseException(error.getMessage(), error); // login với cảnh báo
            }
        }
        UserEntity userEntity = optionalUserEntity.get();
        // check user bị inactive
        if (!UserStatus.ACTIVE.equals(userEntity.getStatus())) {
            authFailCacheService.checkLoginFail(userEntity.getUserName());
            throw new ResponseException(BadRequestError.LOGIN_FAIL_BLOCK_ACCOUNT.getMessage(), BadRequestError.LOGIN_FAIL_BLOCK_ACCOUNT);
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(request.getUsername().toLowerCase(),
                request.getPassword(), new ArrayList<>());
        authentication = authenticationManager.authenticate(authentication);
        String accessToken = this.tokenProvider.createToken(authentication, userEntity.getId());
        long expiresIn = this.authenticationProperties.getAccessTokenExpiresIn().toSeconds();
        String refreshToken = null;
        if (request.getRememberMe()) {
            refreshToken = this.tokenProvider.createRefreshToken(userEntity.getId());
        }
        authFailCacheService.resetLoginFail(request.getUsername());
        return AuthToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType(AuthToken.TOKEN_TYPE_BEARER)
                .expiresIn(expiresIn)
                .build();

    }

    @Override
    public AuthToken refreshToken(RefreshTokenRequest request) {
        return null;
    }

    @Override
    public User register(UserRegisterRequest request) {
        Optional<UserEntity> optionalUserEntity = this.userRepository.findByUserName(request.getUsername());
        if (optionalUserEntity.isPresent()) {
            throw new ResponseException(BadRequestError.USER_USERNAME_EXITED.getMessage(), BadRequestError.USER_USERNAME_EXITED);
        }
        UserRegisterCmd userRegisterCmd = this.autoMapper.from(request);
        String encodedPassword = this.passwordEncoder.encode(userRegisterCmd.getPassword());
        userRegisterCmd.setPassword(encodedPassword);
        User user = new User();
        this.userService.save(user);
        return user;
    }

    @Override
    public User myProfile() {
        UserAuthentication userAuthentication = SecurityUtils.authentication();
        Optional<UserEntity> optionalUserEntity = this.userRepository.findByUserName(userAuthentication.getName());
        if (optionalUserEntity.isEmpty()) {
            throw new ResponseException(NotFoundError.USER_NOT_FOUND.getMessage(), NotFoundError.USER_NOT_FOUND);
        }
        User user = this.userMapper.toDomain(optionalUserEntity.get());
        log.info("User {}", user);
        return user;
    }

    @Override
    public User meUpdateProfile( UserUpdateProfileRequest request) {
        UserAuthentication userAuthentication = SecurityUtils.authentication();
        Optional<UserEntity> optionalUserEntity = this.userRepository.findByUserName(userAuthentication.getName());
        if (optionalUserEntity.isEmpty()) {
            throw new ResponseException(NotFoundError.USER_NOT_FOUND.getMessage(), NotFoundError.USER_NOT_FOUND);
        }
        UserUpdateCmd userCreateCmd = this.autoMapper.from(request);
        User user = new User();
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
