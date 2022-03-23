package com.ap.iamstu.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Api(tags = "Jwk Resource")
public interface JwkSetResource {

    @ApiOperation("Get jwks.json")
    @GetMapping("/api/certificate/.well-known/jwks.json")
    Map<String, Object> keys();
}
