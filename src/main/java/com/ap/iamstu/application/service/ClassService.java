package com.ap.iamstu.application.service;

import com.ap.iamstu.application.dto.request.ClassCreateRequest;
import com.ap.iamstu.application.dto.request.ClassSearchRequest;
import com.ap.iamstu.application.dto.request.ClassUpdateRequest;
import com.ap.iamstu.domain.Class;
import com.ap.iamstu.infrastructure.support.Service.DomainService;
import com.ap.iamstu.infrastructure.support.query.PageDTO;

public interface ClassService extends DomainService<Class, String> {
    Class create(ClassCreateRequest request);

    Class update(String id, ClassUpdateRequest classToUpdate);

    PageDTO<Class> Search(ClassSearchRequest classSearchRequest);

}
