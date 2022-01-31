package com.ap.iamstu.presentation.impl;

import com.ap.iamstu.application.dto.request.ClassCreateRequest;
import com.ap.iamstu.application.dto.request.ClassUpdateRequest;
import com.ap.iamstu.domain.Class;
import com.ap.iamstu.infrastructure.support.query.response.Response;
import com.ap.iamstu.presentation.ClassResource;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClassResourceImpl implements ClassResource {
    @Override
    public Response<Class> createClass(ClassCreateRequest request) {
        return null;
    }

    @Override
    public Response<Class> updateClass(ClassUpdateRequest request) {
        return null;
    }
}
