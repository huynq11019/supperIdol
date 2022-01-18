package com.ap.iamstu.application.service.impl;

import com.ap.iamstu.application.dto.request.UserCreateRequest;
import com.ap.iamstu.application.mapper.AutoMapper;
import com.ap.iamstu.application.mapper.EntityMapper;
import com.ap.iamstu.application.mapper.UserMapper;
import com.ap.iamstu.application.service.UserService;
import com.ap.iamstu.domain.User;
import com.ap.iamstu.domain.command.UserCreateCmd;
import com.ap.iamstu.infrastructure.persistence.entity.UserEntity;
import com.ap.iamstu.infrastructure.support.Service.AbstractDomainService;
import com.ap.iamstu.infrastructure.support.error.NotFoundError;
import com.ap.iamstu.infrastructure.support.exeption.ResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl extends AbstractDomainService<User, UserEntity, String> implements UserService {

    private final UserMapper userMapper;
    private final AutoMapper autoMapper;

    protected UserServiceImpl(JpaRepository<UserEntity, String> jpaRepository,
                              EntityMapper<User, UserEntity> mapper, UserMapper userMapper, AutoMapper autoMapper) {
        super(jpaRepository, mapper);
        this.userMapper = userMapper;
        this.autoMapper = autoMapper;
    }

    @Override
    public User createUser(UserCreateRequest userCreateRequest) {
        UserCreateCmd createCmd = this.autoMapper.from(userCreateRequest);
        User user = new User(createCmd);
        this.save(user);
        return user;
    }

    @Override
    public User ensureExited(String userId) {
        UserEntity userEntity = this.jpaRepository.findById(userId).orElseThrow(
                () -> new ResponseException(NotFoundError.USER_NOT_FOUND)
        );
        return this.userMapper.toDomain(userEntity);
    }
}
