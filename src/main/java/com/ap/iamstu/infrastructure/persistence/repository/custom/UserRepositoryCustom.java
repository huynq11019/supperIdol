package com.ap.iamstu.infrastructure.persistence.repository.custom;

import com.ap.iamstu.application.dto.request.UserSearchRequest;
import com.ap.iamstu.infrastructure.persistence.entity.UserEntity;
import com.ap.iamstu.infrastructure.persistence.query.UserSearchQuery;

import java.util.List;

public interface UserRepositoryCustom {
    List<UserEntity> searchUser(UserSearchQuery searchQuery);

    Long countUser(UserSearchQuery searchQuery);
}
