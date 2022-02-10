package com.ap.iamstu.infrastructure.persistence.repository.impl;

import com.ap.iamstu.infrastructure.persistence.entity.UserEntity;
import com.ap.iamstu.infrastructure.persistence.query.UserSearchQuery;
import com.ap.iamstu.infrastructure.persistence.repository.custom.UserRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserEntity> searchUser(UserSearchQuery searchQuery) {
        return new ArrayList<>();
    }

    @Override
    public Long countUser(UserSearchQuery searchQuery) {
        return 0L;
    }
}
