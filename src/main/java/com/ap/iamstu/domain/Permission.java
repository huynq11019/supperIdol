package com.ap.iamstu.domain;

import com.ap.iamstu.infrastructure.support.domain.AuditableDomain;
import com.ap.iamstu.infrastructure.support.enums.Scope;
import com.ap.iamstu.infrastructure.support.util.IdUtils;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter(AccessLevel.PRIVATE)
@Getter
public class Permission extends AuditableDomain {


    private String id;
    private Scope scope;
    private String resourceCode;
    private String resourceName;
    private String name;
    private Boolean deleted;

    public Permission(String resourceCode, Scope scope, String name) {
        this.id = IdUtils.nextId();
        this.resourceCode = resourceCode;
        this.scope = scope;
        this.name = name;
        this.deleted = false;
    }

    public void deleted() {
        this.deleted = true;
    }

    public void enrichResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
}
