package com.ap.iamstu.application.mapper;


import com.ap.iamstu.application.dto.request.TeacherCreateRequest;
import com.ap.iamstu.application.dto.request.UserCreateRequest;
import com.ap.iamstu.application.dto.request.UserRegisterRequest;
import com.ap.iamstu.application.dto.request.UserUpdateProfileRequest;
import com.ap.iamstu.domain.command.TeacherCreateCmd;
import com.ap.iamstu.domain.command.UserCreateCmd;
import com.ap.iamstu.domain.command.UserRegisterCmd;
import com.ap.iamstu.domain.command.UserUpdateCmd;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutoMapper {

    UserCreateCmd from(UserCreateRequest userCreateRequest);

    UserRegisterCmd from(UserRegisterRequest request);

    UserUpdateCmd from(UserUpdateProfileRequest request);

    TeacherCreateCmd from (TeacherCreateRequest  teacherCreateRequest);
}
