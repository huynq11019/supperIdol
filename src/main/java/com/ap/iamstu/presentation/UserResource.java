package com.ap.iamstu.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Api(tags = "User Resource")
@RequestMapping("/api")
@Validated
public interface UserResource {
//    @ApiOperation(value = "Create user")
//    @PostMapping("/users")
//    @PreAuthorize("hasPermission(null, 'user:create')")
//    Response<User> create(@RequestBody @Valid UserInternalCreateRequest request);
}
