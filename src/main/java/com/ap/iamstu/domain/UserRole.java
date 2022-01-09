package com.ap.iamstu.domain;

import com.ap.iamstu.infrastructure.support.domain.AuditableDomain;
import com.ap.iamstu.infrastructure.support.util.IdUtils;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter(AccessLevel.PRIVATE)
@Getter
public class UserRole  extends AuditableDomain {

    private String id;
    private String userId;
    private String roleId;
    private Boolean deleted;

    public UserRole(String userId, String roleId) {
        this.id = IdUtils.nextId();
        this.userId = userId;
        this.roleId = roleId;
        this.deleted = false;
    }

    void deleted() {
        this.deleted = true;
    }

    void unDelete() {
        this.deleted = false;
    }
}
