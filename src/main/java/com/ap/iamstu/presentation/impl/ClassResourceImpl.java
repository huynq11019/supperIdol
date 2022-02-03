package com.ap.iamstu.presentation.impl;

import com.ap.iamstu.application.dto.request.ClassCreateRequest;
import com.ap.iamstu.application.dto.request.ClassUpdateRequest;
import com.ap.iamstu.application.dto.request.FindByIds;
import com.ap.iamstu.application.service.ClassService;
import com.ap.iamstu.domain.Class;
import com.ap.iamstu.infrastructure.support.query.response.Response;
import com.ap.iamstu.presentation.ClassResource;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ClassResourceImpl implements ClassResource {
    private final ClassService classService;

    public ClassResourceImpl(ClassService classService) {
        this.classService = classService;
    }

    @Override
    public Response<Class> createClass(ClassCreateRequest request) {
        return Response.of(classService.create(request));
    }

    @Override
    public Response<Class> updateClass(String id, ClassUpdateRequest request) {
        return Response.of(classService.update(id, request));
    }

    @Override
    public Response<Optional<Class>> findClassById(String id) {
        return Response.of(classService.findById(id));
    }

    @Override
    public Response<List<Class>> findClassByIds(FindByIds id) {
        return Response.of(classService.findAllByIds(id.getIds()));
    }
}
