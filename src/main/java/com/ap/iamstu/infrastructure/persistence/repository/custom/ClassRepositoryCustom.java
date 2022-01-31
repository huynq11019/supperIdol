package com.ap.iamstu.infrastructure.persistence.repository.custom;

import com.ap.iamstu.infrastructure.persistence.entity.ClassEntity;
import com.ap.iamstu.infrastructure.persistence.query.ClassSearchQuery;

import java.util.List;

public interface ClassRepositoryCustom {
    List<ClassEntity> searchClass(ClassSearchQuery search);

    Long countClasses(ClassSearchQuery searchQuery);
}
