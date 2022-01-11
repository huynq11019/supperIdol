package com.ap.iamstu.application.mapper;


import com.ap.iamstu.application.dto.request.UserCreateRequest;
import com.ap.iamstu.application.dto.request.UserRegisterRequest;
import com.ap.iamstu.domain.command.UserCreateCmd;
import com.ap.iamstu.domain.command.UserRegisterCmd;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutoMapper {

    UserCreateCmd userCreateCmd(UserCreateRequest userCreateRequest);

    UserRegisterCmd from(UserRegisterRequest request);

}
