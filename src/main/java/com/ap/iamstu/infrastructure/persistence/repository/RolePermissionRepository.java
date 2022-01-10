package com.ap.iamstu.infrastructure.persistence.repository;

import com.ap.iamstu.infrastructure.persistence.entity.RolePermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermissionEntity, String> {
    @Query("from RolePermissionEntity u where u.deleted = false and u.roleId in :roleIds")
    List<RolePermissionEntity> findAllByRoleIds(List<String> roleIds);
}
