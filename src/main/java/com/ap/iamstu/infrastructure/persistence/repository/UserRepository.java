package com.ap.iamstu.infrastructure.persistence.repository;

import com.ap.iamstu.infrastructure.persistence.entity.UserEntity;
import com.ap.iamstu.infrastructure.persistence.repository.custom.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String>, UserRepositoryCustom {
    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);
}
