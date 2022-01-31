package com.ap.iamstu.application.mapper;

import com.ap.iamstu.domain.Class;
import com.ap.iamstu.infrastructure.persistence.entity.ClassEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface ClassMapper extends EntityMapper<Class, ClassEntity> {

}
