package com.ap.iamstu.domain;


import com.ap.iamstu.infrastructure.support.domain.AuditableDomain;
import com.ap.iamstu.infrastructure.support.enums.RoleLevel;
import com.ap.iamstu.infrastructure.support.enums.RoleStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;


@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter(AccessLevel.PRIVATE)
@Getter
public class Role extends AuditableDomain {

    private String id;
    private String code;
    private String name;
    private String description;
    private Boolean isRoot;
    private RoleStatus status;
    private List<RolePermission> permissions;
    private Boolean deleted;
    private RoleLevel roleLevel;

//    public Role(RoleCreateOrUpdateCmd cmd, List<Permission> existedPermissions) {
//        this.id = IdUtils.nextId();
//        this.code = cmd.getCode();
//        this.name = cmd.getName();
//        this.description = cmd.getDescription();
//        this.status = RoleStatus.ACTIVE;
//
//        if (Objects.isNull(cmd.getIsRoot())) {
//            cmd.setIsRoot(false);
//        }
//        this.isRoot = cmd.getIsRoot();
//        if (Boolean.TRUE.equals(this.isRoot)
//                && (!RoleLevel.CENTER.equals(cmd.getRoleLevel()))) {
//            throw new ResponseException(BadRequestError.ADMIN_MUST_CENTER_LEVEL);
//        }
//        this.roleLevel = cmd.getRoleLevel();
//
//        this.permissions = new ArrayList<>();
//        if (!CollectionUtils.isEmpty(cmd.getPermissions())) {
//            List<RolePermissionCreateCmd> permissionCmds = cmd.getPermissions().stream().distinct().collect(Collectors.toList());
//            for (RolePermissionCreateCmd permissionCmd : permissionCmds) {
//                List<Permission> permissionResources = existedPermissions.stream()
//                        .filter(p -> Objects.equals(permissionCmd.getResourceCode(), p.getResourceCode()))
//                        .collect(Collectors.toList());
//
//                if (!CollectionUtils.isEmpty(permissionCmd.getScopes())) {
//                    for (Scope scope : permissionCmd.getScopes()) {
//                        Optional<Permission> optionalPermission = permissionResources.stream()
//                                .filter(p -> scope.equals(p.getScope())).findFirst();
//                        optionalPermission.ifPresent(permission
//                                -> this.permissions.add(new RolePermission(this.id, permission.getResourceCode(), permission.getScope())));
//                    }
//                }
//            }
//        }
//        this.deleted = false;
//    }
}
