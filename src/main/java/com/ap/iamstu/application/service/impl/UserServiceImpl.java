package com.ap.iamstu.application.service.impl;

import com.ap.iamstu.application.dto.request.UserInternalCreateRequest;
import com.ap.iamstu.application.mapper.EntityMapper;
import com.ap.iamstu.application.service.UserService;
import com.ap.iamstu.domain.User;
import com.ap.iamstu.infrastructure.persistence.entity.UserEntity;
import com.ap.iamstu.infrastructure.support.Service.AbstractDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl extends AbstractDomainService<User, UserEntity, String> implements UserService{

    protected UserServiceImpl(JpaRepository<UserEntity, String> jpaRepository, EntityMapper<User, UserEntity> mapper) {
        super(jpaRepository, mapper);
    }

    @Override
    public User createUser(UserInternalCreateRequest userInternalCreateRequest) {
        return null;
    }
}
