package com.ap.iamstu.application.service.impl;

import com.ap.iamstu.application.sercurity.AuthorityService;
import com.ap.iamstu.application.sercurity.UserAuthority;
import com.ap.iamstu.infrastructure.persistence.entity.RoleEntity;
import com.ap.iamstu.infrastructure.persistence.entity.RolePermissionEntity;
import com.ap.iamstu.infrastructure.persistence.entity.UserEntity;
import com.ap.iamstu.infrastructure.persistence.entity.UserRoleEntity;
import com.ap.iamstu.infrastructure.persistence.repository.RolePermissionRepository;
import com.ap.iamstu.infrastructure.persistence.repository.RoleRepository;
import com.ap.iamstu.infrastructure.persistence.repository.UserRepository;
import com.ap.iamstu.infrastructure.persistence.repository.UserRoleRepository;
import com.ap.iamstu.infrastructure.support.enums.RoleStatus;
import com.ap.iamstu.infrastructure.support.error.BadRequestError;
import com.ap.iamstu.infrastructure.support.error.NotFoundError;
import com.ap.iamstu.infrastructure.support.exeption.ResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Primary
@Slf4j
public class AuthorityServiceImpl implements AuthorityService {

    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final UserRepository userRepository;

    public AuthorityServiceImpl(UserRoleRepository userRoleRepository,
                                RoleRepository roleRepository,
                                RolePermissionRepository rolePermissionRepository,
                                UserRepository userRepository
    ) {
        this.userRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.userRepository = userRepository;
    }

    @Cacheable(cacheNames = "user-authority", key = "#userId",
            condition = "#userId != null", unless = "#userId == null || #result == null")
    @Override
    public UserAuthority getUserAuthority(String userId) {
        UserEntity userEntity = ensureUserExisted(userId);
        List<String> grantedAuthorities = new ArrayList<>();
        boolean isRoot = false;
        List<UserRoleEntity> userRoleEntities = this.userRoleRepository.findAllByUserId(userEntity.getId());
        if (!CollectionUtils.isEmpty(userRoleEntities)) {
            List<String> roleIds = userRoleEntities.stream()
                    .map(UserRoleEntity::getRoleId).distinct().collect(Collectors.toList());
            List<RoleEntity> roleEntities = this.roleRepository.findAllByIds(roleIds);
            roleIds = roleEntities.stream()
                    .filter(r -> Objects.equals(RoleStatus.ACTIVE, r.getStatus()))
                    .map(RoleEntity::getId).distinct().collect(Collectors.toList());
            isRoot = roleEntities.stream().anyMatch(r -> Boolean.TRUE.equals(r.getIsRoot()));
            List<RolePermissionEntity> rolePermissionEntities = this.rolePermissionRepository.findAllByRoleIds(roleIds);
            if (!CollectionUtils.isEmpty(rolePermissionEntities)) {
                grantedAuthorities = rolePermissionEntities.stream()
                        .map(r -> String.format("%s:%s", r.getResourceCode().toLowerCase(), r.getScope().toString().toLowerCase()))
                        .distinct().collect(Collectors.toList());
            }
        }

        return UserAuthority.builder()
                .isRoot(isRoot)
                .grantedPermissions(grantedAuthorities)
                .userId(userEntity.getId())
                .accountType(userEntity.getAccountType())
                .lastAuthChangeAt(userEntity.getLastAuthChangeAt())
                .build();
    }

    private UserEntity ensureUserExisted(String userId) {
        return this.userRepository.findById(userId).orElseThrow(() ->
                new ResponseException(NotFoundError.USER_NOT_FOUND.getMessage(), NotFoundError.USER_NOT_FOUND));
    }

//    private List<String> getBuildingIds(UserEntity userEntity) {
//        List<UserLocationEntity> userLocations;
//        Boolean isAdmin = checkRoleAdminOfUser(userEntity.getId());
//        List<String> buildingIds = new ArrayList<>();
//        if (Boolean.TRUE.equals(isAdmin) || Objects.equals(userEntity.getUserLevel(), UserLevel.CENTER)) {
//            Response<List<BuildingDTO>> response = buildingClient.findAllBuilding();
//            if (response.isSuccess() && !CollectionUtils.isEmpty(response.getData())) {
//                buildingIds = response.getData().stream().map(BuildingDTO::getId).collect(Collectors.toList());
//            }
//        } else {
//            userLocations = userLocationRepository.findAllByUserId(userEntity.getId());
//            buildingIds = userLocations.stream()
//                    .map(UserLocationEntity::getBuildingId)
//                    .distinct().collect(Collectors.toList());
//        }
//        log.info("Get buildings by userId: {}", userEntity.getId());
//        return buildingIds;
//    }

    Boolean checkRoleAdminOfUser(String userId) {
        List<UserRoleEntity> userRoleEntities = userRoleRepository.findAllByUserId(userId);
        if (CollectionUtils.isEmpty(userRoleEntities)) {
            throw new ResponseException(BadRequestError.USER_CAN_NOT_PERFORM_THIS_ACTION);
        } else {
            List<String> roleIds = userRoleEntities.stream().map(UserRoleEntity::getRoleId).collect(Collectors.toList());
            List<RoleEntity> roleEntities = roleRepository.findAllByIds(roleIds);
            Optional<RoleEntity> roleAdmin = roleEntities.stream().filter(RoleEntity::getIsRoot).findFirst();
            return roleAdmin.isPresent();
        }
    }
}
