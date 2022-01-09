package com.ap.iamstu.application.mapper;

import com.ap.iamstu.domain.User;
import com.ap.iamstu.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<User, UserEntity> {
}
