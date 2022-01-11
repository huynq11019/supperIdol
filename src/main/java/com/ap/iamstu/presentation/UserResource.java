package com.ap.iamstu.presentation;

import com.ap.iamstu.application.dto.request.UserInternalCreateRequest;
import com.ap.iamstu.domain.User;
import com.ap.iamstu.infrastructure.support.query.response.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Api(tags = "User Resource")
@RequestMapping("/api")
@Validated
public interface UserResource {
    @ApiOperation(value = "Create user")
    @PostMapping("/users/internal")
    @PreAuthorize("hasPermission(null, 'user:create')")
    Response<User> createUserInternal(@RequestBody @Valid UserInternalCreateRequest request);

    @ApiOperation(value = "Create user student")
    @PostMapping("/users/student")
    Response<User> createUserStudent(@RequestBody @Valid UserInternalCreateRequest request);
}
