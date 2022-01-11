package com.ap.iamstu.application.service;

import com.ap.iamstu.application.dto.request.UserInternalCreateRequest;
import com.ap.iamstu.domain.User;
import com.ap.iamstu.infrastructure.support.Service.DomainService;

public interface UserService extends DomainService<User, String> {
    User createUser(UserInternalCreateRequest userInternalCreateRequest);
}
