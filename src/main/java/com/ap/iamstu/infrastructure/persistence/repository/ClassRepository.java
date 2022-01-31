package com.ap.iamstu.infrastructure.persistence.repository;

import com.ap.iamstu.infrastructure.persistence.entity.ClassEntity;
import com.ap.iamstu.infrastructure.persistence.repository.custom.ClassRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<ClassEntity, String>, ClassRepositoryCustom {
}
