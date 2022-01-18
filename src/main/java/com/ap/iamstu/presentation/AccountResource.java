package com.ap.iamstu.presentation;


import com.ap.iamstu.application.dto.request.*;
import com.ap.iamstu.application.dto.response.AuthToken;
import com.ap.iamstu.application.sercurity.UserAuthority;
import com.ap.iamstu.application.sercurity.request.LoginRequest;
import com.ap.iamstu.domain.User;
import com.ap.iamstu.infrastructure.support.query.response.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Api(tags = "Account Resource ( chứa các thông tin liên quan đến tài khoản )")
@RequestMapping("/api")
@Validated
public interface AccountResource {

    @ApiOperation(value = "Get my profile")
    @GetMapping("/me/profile")
    Response<User> myProfile();

    @ApiOperation(value = "Update profile")
    @PostMapping("/me/profile")
    Response<User> updateProfile(@RequestBody @Valid UserUpdateProfileRequest request);

    @ApiOperation(value = "Change password")
    @PostMapping("/me/change-password")
    Response<User> changePassword(@RequestBody @Valid UserChangePasswordRequest request,
                                  HttpServletRequest httpServletRequest);

    @ApiOperation(value = "Login")
    @PostMapping("/authenticate")
    Response<AuthToken> authenticate(@RequestBody @Valid LoginRequest request);

    @ApiOperation(value = "Refresh token")
    @PostMapping("/refresh-token")
    Response<AuthToken> refreshToken(@RequestBody @Valid RefreshTokenRequest request);

    @ApiOperation(value = "Register - Only for Dev")
    @PostMapping("/register")
    Response<User> register(@RequestBody @Valid UserRegisterRequest request);

    @ApiOperation(value = "Who I am - Only for Dev")
    @GetMapping("/account")
    Response<String> me();

    @ApiOperation(value = "Get my authorities")
    @GetMapping("/me/authorities")
    Response<UserAuthority> myAuthorities();

    @ApiOperation(value = "Logout device")
    @PostMapping("/logout")
    Response<Boolean> logout(@RequestBody(required = false) LogoutRevokeRequest logoutRevokeRequest);

    @ApiOperation(value = "Init reset password")
    @PostMapping("/account/reset-password/init")
    Response<Boolean> initResetPassword(@RequestBody @Valid EmailForgotPasswordRequest request) throws MessagingException;

    @ApiOperation(value = "Reset password")
    @PostMapping("/account/reset-password/finish")
    Response<Boolean> resetPassword(@RequestBody @Valid ForgotPasswordRequest request);

    @ApiOperation(value = "Link redirect reset password")
    @GetMapping("/account/reset-password/redirect")
    RedirectView redirect(@RequestParam(name = "token") String token, HttpServletResponse response);

}
