package com.ap.iamstu.application.service.impl;

import com.ap.iamstu.application.dto.request.ClassCreateRequest;
import com.ap.iamstu.application.dto.request.ClassSearchRequest;
import com.ap.iamstu.application.dto.request.ClassUpdateRequest;
import com.ap.iamstu.application.mapper.AutoMapper;
import com.ap.iamstu.application.mapper.ClassMapper;
import com.ap.iamstu.application.mapper.EntityMapper;
import com.ap.iamstu.application.service.ClassService;
import com.ap.iamstu.domain.Class;
import com.ap.iamstu.domain.command.ClassCreateCmd;
import com.ap.iamstu.domain.command.ClassUpdateCmd;
import com.ap.iamstu.infrastructure.persistence.entity.ClassEntity;
import com.ap.iamstu.infrastructure.persistence.query.ClassSearchQuery;
import com.ap.iamstu.infrastructure.persistence.repository.ClassRepository;
import com.ap.iamstu.infrastructure.support.Service.AbstractDomainService;
import com.ap.iamstu.infrastructure.support.error.NotFoundError;
import com.ap.iamstu.infrastructure.support.exeption.ResponseException;
import com.ap.iamstu.infrastructure.support.query.PageDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassServiceImpl extends AbstractDomainService<Class, ClassEntity, String> implements ClassService {
    private final ClassRepository classRepository;
    private final AutoMapper autoMapper;
    private final ClassMapper classMapper;

    protected ClassServiceImpl(JpaRepository<ClassEntity, String> jpaRepository, EntityMapper<Class, ClassEntity> mapper, ClassRepository classRepository, AutoMapper autoMapper, ClassMapper classMapper) {
        super(jpaRepository, mapper);
        this.classRepository = classRepository;
        this.autoMapper = autoMapper;
        this.classMapper = classMapper;
    }

    @Override
    public Class create(ClassCreateRequest request) {

        ClassCreateCmd classCreateCmd = autoMapper.from(request);
        Class aClass = new Class(classCreateCmd);
        return this.save(aClass);
    }

    @Override
    public Class update(String id, ClassUpdateRequest classToUpdate) {
        ClassUpdateCmd classUpdateCmd = autoMapper.toUpdate(classToUpdate);
        Class aClass = this.ensureExists(id);
        aClass.update(classUpdateCmd);
        return aClass;
    }

    private Class ensureExists(String id) {
        return this.findById(id).orElseThrow(()
                -> new ResponseException(NotFoundError.CLASS_NOT_FOUND));

    }

    @Override
    public PageDTO<Class> Search(ClassSearchRequest classSearchRequest) {
        ClassSearchQuery searchQuery = this.autoMapper.form(classSearchRequest);
        Long total = this.classRepository.countClasses(searchQuery);
        if (total == 0) {
            return new PageDTO<>(new ArrayList<>(), classSearchRequest.getPageIndex(), classSearchRequest.getPageSize(), 0L);
        }
        List<Class> classes = this.classMapper.toDomain(this.classRepository.searchClass(searchQuery));
        return new PageDTO<>(classes, classSearchRequest.getPageIndex(), classSearchRequest.getPageSize(), total);
    }
}
