package com.ap.iamstu.presentation;

import com.ap.iamstu.application.dto.request.FindByIds;
import com.ap.iamstu.application.dto.request.UserCreateRequest;
import com.ap.iamstu.application.dto.request.UserInternalCreateRequest;
import com.ap.iamstu.application.dto.request.UserSearchRequest;
import com.ap.iamstu.domain.User;
import com.ap.iamstu.infrastructure.support.query.response.PagingResponse;
import com.ap.iamstu.infrastructure.support.query.response.Response;
import com.ap.iamstu.infrastructure.support.validate.ValidatePaging;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "User Resource (Quản lý các thông tin của tài khoản)")
@RequestMapping("/api")
@Validated
public interface UserResource {
    @ApiOperation(value = "Create user")
    @PostMapping("/users/internal")
    @PreAuthorize("hasPermission(null, 'user:create')")
    Response<User> createUserInternal(@RequestBody @Valid UserCreateRequest request);

    @ApiOperation(value = "Create user student")
    @PostMapping("/users/student")
    Response<User> createUserStudent(@RequestBody @Valid UserInternalCreateRequest request);

    @ApiOperation(value = "Search user")
    @PostMapping("/users")
    PagingResponse<User> searchUser(@ValidatePaging(allowedSorts = {"lastModifiedAt", "createdAt"})
                                            UserSearchRequest request);

    @ApiOperation(value = "Find user by ids")
    @PostMapping("/users/find-by-ids")
    Response<List<User>> findByIds(@RequestBody @Valid FindByIds request);

}
