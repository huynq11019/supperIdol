package com.ap.iamstu.infrastructure.persistence.repository;

import com.ap.iamstu.infrastructure.persistence.entity.UserLoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoginRepository extends JpaRepository<UserLoginEntity, Long> {
}
