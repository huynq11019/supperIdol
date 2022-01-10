package com.ap.iamstu.infrastructure.persistence.repository;

import com.ap.iamstu.infrastructure.persistence.entity.RoleEntity;
import com.ap.iamstu.infrastructure.support.enums.RoleLevel;
import com.ap.iamstu.infrastructure.support.enums.RoleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, String> {
    @Query("from RoleEntity u where u.deleted = false and u.id in :ids")
    List<RoleEntity> findAllByIds(List<String> ids);

    @Query("from RoleEntity u where u.deleted = false and u.status = :status")
    List<RoleEntity> findAllByStatus(RoleStatus status);

    @Query("select f from RoleEntity f where f.deleted = false and f.roleLevel in :roleLevels" +
            " and (:keyword is null or (" +
            " lower(f.name) like %:keyword% or" +
            " lower(f.code) like %:keyword% " +
            " ))")
    Page<RoleEntity> search(@Param("keyword") String keyword,
                            @Param("roleLevels") List<RoleLevel> roleLevels, Pageable pageable);

    @Query("select f from RoleEntity f where f.deleted = false and f.status = :status and f.roleLevel in :roleLevels" +
            " and (:keyword is null or (" +
            " lower(f.name) like %:keyword% or" +
            " lower(f.code) like %:keyword% " +
            " ))")
    Page<RoleEntity> search(@Param("keyword") String keyword, @Param("status") RoleStatus status,
                            @Param("roleLevels") List<RoleLevel> roleLevels, Pageable pageable);
}
