package com.ap.iamstu.domain;

import com.ap.iamstu.infrastructure.support.domain.AuditableDomain;
import com.ap.iamstu.infrastructure.support.enums.Scope;
import com.ap.iamstu.infrastructure.support.util.IdUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
//@SuperBuilder
@Setter(AccessLevel.PRIVATE)
@Getter
public class RolePermission extends AuditableDomain {
    @JsonIgnore
    private String id;
    private String roleId;
    private String resourceCode;
    private Scope scope;
    private Boolean deleted;

    public RolePermission(String roleId, String resourceCode, Scope scope) {
        this.id = IdUtils.nextId();
        this.roleId = roleId;
        this.resourceCode = resourceCode;
        this.scope = scope;
        this.deleted = false;
    }

    public void deleted() {
        this.deleted = true;
    }
}
