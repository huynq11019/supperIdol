package com.ap.iamstu.presentation.impl;

import com.ap.iamstu.application.dto.request.FindByIds;
import com.ap.iamstu.application.dto.request.UserCreateRequest;
import com.ap.iamstu.application.dto.request.UserInternalCreateRequest;
import com.ap.iamstu.application.dto.request.UserSearchRequest;
import com.ap.iamstu.application.service.UserService;
import com.ap.iamstu.domain.User;
import com.ap.iamstu.infrastructure.support.query.response.PagingResponse;
import com.ap.iamstu.infrastructure.support.query.response.Response;
import com.ap.iamstu.presentation.UserResource;

import java.util.List;

public class UserResourceImpl implements UserResource {
    private final UserService userService;

    public UserResourceImpl(UserService userService) {
        this.userService = userService;
    }
    @Override
    public Response<User> createUserInternal(UserCreateRequest request) {
        return Response.of(userService.createUser(request));
    }

    @Override
    public Response<User> createUserStudent(UserInternalCreateRequest request) {
        return null;
    }

    @Override
    public PagingResponse<User> searchUser(UserSearchRequest request) {
        return PagingResponse.of(this.userService.searchUser(request));
    }

    @Override
    public Response<List<User>> findByIds(FindByIds request) {
        return Response.of(this.userService.findAllByIds(request.getIds()));
    }
}
