package com.ap.iamstu.application.service;

import com.ap.iamstu.application.dto.request.*;
import com.ap.iamstu.application.dto.response.AuthToken;
import com.ap.iamstu.application.sercurity.UserAuthority;
import com.ap.iamstu.application.sercurity.request.LoginRequest;
import com.ap.iamstu.domain.User;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

public interface AccountService {

    AuthToken login(LoginRequest request);

    AuthToken refreshToken(RefreshTokenRequest request);

    User register(UserRegisterRequest request);

    User myProfile();

    User meUpdateProfile(UserUpdateProfileRequest request);

    User changePassword(UserChangePasswordRequest request, HttpServletRequest httpServletRequest);

    String currentUser();

    UserAuthority myAuthorities();

    void logout(LogoutRevokeRequest logoutRevokeRequest);

//    List<BuildingDTO> myBuildings();
//
//    List<FloorDTO> myFloors(String buildingId);
//
//    List<Organization> getByCurrentUser();

    void forgotPassword(EmailForgotPasswordRequest request) throws MessagingException;

    void resetPassword(ForgotPasswordRequest request);
}
