package com.ap.iamstu.presentation.impl;

import com.ap.iamstu.application.sercurity.TokenProvider;
import com.ap.iamstu.presentation.JwkSetResource;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class JwkSetResourceImpl implements JwkSetResource {

    private final TokenProvider tokenProvider;

    public JwkSetResourceImpl(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    public Map<String, Object> keys() {
        return this.tokenProvider.jwkSet().toJSONObject();
    }
}
