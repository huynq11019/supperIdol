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
import com.ap.iamstu.application.service.*;
import com.ap.iamstu.domain.User;
import com.ap.iamstu.domain.command.UserRegisterCmd;
import com.ap.iamstu.domain.command.UserUpdateCmd;
import com.ap.iamstu.infrastructure.persistence.entity.UserEntity;
import com.ap.iamstu.infrastructure.persistence.repository.UserRepository;
import com.ap.iamstu.infrastructure.support.enums.UserStatus;
import com.ap.iamstu.infrastructure.support.error.AuthenticationError;
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
import java.util.Objects;
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
    private final SendEmailService sendEmailService;

    public AccountServiceImpl(UserService userService, UserRepository userRepository, AutoMapper autoMapper, PasswordEncoder passwordEncoder, TokenProvider tokenProvider, AuthenticationProperties authenticationProperties, AuthorityService authorityService, AuthFailCacheService authFailCacheService,
                              AuthenticationManager authenticationManager, UserMapper userMapper, SendEmailService sendEmailService) {
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
        this.sendEmailService = sendEmailService;
    }


    @Override
    public AuthToken login(LoginRequest request) {
        // check account was locked
        if (authFailCacheService.isBlockedUser(request.getUsername())) {
            throw new ResponseException(BadRequestError.LOGIN_FAIL_BLOCK_ACCOUNT.getMessage(), BadRequestError.LOGIN_FAIL_BLOCK_ACCOUNT);
        }
        // check user
        Optional<UserEntity> optionalUserEntity = this.userRepository.findByUsername(request.getUsername());
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
            authFailCacheService.checkLoginFail(userEntity.getUsername());
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
        if (!this.tokenProvider.validateToken(request.getRefreshToken())) {
            throw new ResponseException(AuthenticationError.INVALID_REFRESH_TOKEN);
        }

        String userId = this.tokenProvider.getSubject(request.getRefreshToken());
        UserEntity userEntity = this.userRepository.findById(userId).orElseThrow(() ->
                new ResponseException(NotFoundError.USER_NOT_FOUND));
        if (!UserStatus.ACTIVE.equals(userEntity.getStatus())) {
            authFailCacheService.checkLoginFail(userEntity.getUsername());
            throw new ResponseException(BadRequestError.LOGIN_FAIL_BLOCK_ACCOUNT);
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(userEntity.getUsername(),
                "",
                new ArrayList<>());
        String accessToken = this.tokenProvider.createToken(authentication, userEntity.getId());
        String refreshToken = this.tokenProvider.createRefreshToken(userEntity.getId());

        long expiresIn = this.authenticationProperties.getAccessTokenExpiresIn().toSeconds();

        return AuthToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType(AuthToken.TOKEN_TYPE_BEARER)
                .expiresIn(expiresIn)
                .build();
    }

    @Override
    public User register(UserRegisterRequest request) {
        Optional<UserEntity> optionalUserEntity = this.userRepository.findByUsername(request.getUsername());
        if (optionalUserEntity.isPresent()) {
            throw new ResponseException(BadRequestError.USER_USERNAME_EXITED.getMessage(), BadRequestError.USER_USERNAME_EXITED);
        }
        UserRegisterCmd userRegisterCmd = this.autoMapper.from(request);
        String encodedPassword = this.passwordEncoder.encode(userRegisterCmd.getPassword());
        userRegisterCmd.setPassword(encodedPassword);
        User user = new User(userRegisterCmd);
        log.info("userRegisterCmd: {}", user);
        this.userService.save(user);
        return user;
    }

    @Override
    public User myProfile() {
//        UserAuthentication userAuthentication = SecurityUtils.authentication();
//        Optional<UserEntity> optionalUserEntity = this.userRepository.findByUsername(userAuthentication.getName());
//        if (optionalUserEntity.isEmpty()) {
//            throw new ResponseException(NotFoundError.USER_NOT_FOUND.getMessage(), NotFoundError.USER_NOT_FOUND);
//        }
//        User user = this.userMapper.toDomain(optionalUserEntity.get());
//        log.info("User {}", user);
//        return user;
        return currentAccount();

    }

    @Override
    public User meUpdateProfile( UserUpdateProfileRequest request) {
        UserAuthentication userAuthentication = SecurityUtils.authentication();
        Optional<UserEntity> optionalUserEntity = this.userRepository.findByUsername(userAuthentication.getName());
        if (optionalUserEntity.isEmpty()) {
            throw new ResponseException(NotFoundError.USER_NOT_FOUND.getMessage(), NotFoundError.USER_NOT_FOUND);
        }
        UserUpdateCmd userCreateCmd = this.autoMapper.from(request);
        User user = new User();
        return null;
    }

    @Override
    public User changePassword(UserChangePasswordRequest request, HttpServletRequest httpServletRequest) {
        User user = currentAccount();
        if (!this.passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new ResponseException(BadRequestError.WRONG_PASSWORD);
        }

        // invalid token and refresh token
        tokenProvider.invalidJwt(tokenProvider.resolveToken(httpServletRequest), request.getRefreshToken());

        String newEncodedPassword = this.passwordEncoder.encode(request.getNewPassword());
        user.changePassword(newEncodedPassword);
        this.userService.save(user);
        return user;
    }

    @Override
    public String currentUser() {
        Optional<String> currentUser = SecurityUtils.getCurrentUser();
        if (currentUser.isEmpty()) {
            throw new ResponseException(AuthenticationError.UNAUTHORISED);
        }
        return currentUser.get();
    }

    @Override
    public UserAuthority myAuthorities() {
        String me = currentUserId();
        return this.authorityService.getUserAuthority(me);
    }

    @Override
    public void logout(LogoutRevokeRequest logoutRevokeRequest) {
        // Neu mobile logout thi revoke token device
//        if (Objects.nonNull(logoutRevokeRequest.getDeviceToken())) {
//            logoutRevokeRequest.setUserId(currentUserId());
//            notificationClient.revokeDevice(logoutRevokeRequest);
//        }

        // revoke device -> invalid token and refresh token
        String accessToken;
        if (SecurityUtils.getCurrentUserJWT().isPresent()) {
            accessToken = SecurityUtils.getCurrentUserJWT().get();
            tokenProvider.invalidJwt(accessToken, logoutRevokeRequest.getRefreshToken());
        }
    }

    @Override
    public void forgotPassword(EmailForgotPasswordRequest request) throws MessagingException {
        Optional<UserEntity> userEntityByEmail = userRepository.findByEmail(request.getEmail());
        if (userEntityByEmail.isEmpty()) {
            log.warn("Password reset requested for non existing mail '{}'", request.getEmail());
            throw new ResponseException(BadRequestError.EMAIL_NOT_EXISTED_IN_SYSTEM);
        }

//        if (userEntityByEmail.get().getAuthenticationType().equals(AuthenticationType.LDAP)) {
//            throw new ResponseException(BadRequestError.ACCOUNT_EMPLOYEE_CAN_NOT_CHANGE_PASSWORD);
//        }

        User user = userMapper.toDomain(userEntityByEmail.get());
        String token = tokenProvider.createTokenSendEmail(user.getId(), request.getEmail());
        sendEmailService.send(user, TEMPLATE_NAME, TITLE_KEY, token);
    }

    @Override
    public void resetPassword(ForgotPasswordRequest request) {
        String userId = tokenProvider.validateEmailToken(request.getToken());
        User user = userService.ensureExited(userId);
        if (!Objects.equals(request.getPassword(), request.getRepeatPassword())) {
            throw new ResponseException(BadRequestError.REPEAT_PASSWORD_DOES_NOT_MATCH);
        }
        String encodedPassword = this.passwordEncoder.encode(request.getPassword());
        user.changePassword(encodedPassword);
        userService.save(user);
    }

    public String currentUserId() {
        Optional<String> currentUserLoginId = SecurityUtils.getCurrentUserLoginId();
        if (currentUserLoginId.isEmpty()) {
            throw new ResponseException(AuthenticationError.UNAUTHORISED);
        }
        return currentUserLoginId.get();
    }

    private User currentAccount() {
        String me = currentUser();
        UserEntity userEntity = ensureUserExisted(me);
        //        user.enrichOrganization(getOrganization(user.getOrganizationId()));
        return this.userMapper.toDomain(userEntity);
    }


    private UserEntity ensureUserExisted(String username) {
        return this.userRepository.findByUsername(username).orElseThrow(() ->
                new ResponseException(NotFoundError.USER_NOT_FOUND));
    }

//    private Organization getOrganization(String organizationId) {
//        Optional<OrganizationEntity> optionalOrganizationEntity = organizationRepository.findById(organizationId);
//        return optionalOrganizationEntity.map(organizationEntityMapper::toDomain).orElse(null);
//    }
}
