package com.ap.iamstu.application.mapper;


import com.ap.iamstu.application.dto.request.*;
import com.ap.iamstu.domain.command.*;
import com.ap.iamstu.infrastructure.persistence.query.ClassSearchQuery;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutoMapper {

    UserCreateCmd from(UserCreateRequest userCreateRequest);

    UserRegisterCmd from(UserRegisterRequest request);

    UserUpdateCmd from(UserUpdateProfileRequest request);

    TeacherCreateCmd from (TeacherCreateRequest  teacherCreateRequest);

    ClassCreateCmd from(ClassCreateRequest classCreateRequest);
//
    ClassUpdateCmd toUpdate (ClassUpdateRequest classUpdateRequest);

    ClassSearchQuery form(ClassSearchRequest classSearchRequest);
}
