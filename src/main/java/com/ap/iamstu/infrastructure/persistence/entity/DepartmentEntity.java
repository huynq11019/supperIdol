package com.ap.iamstu.infrastructure.persistence.entity;

import com.ap.iamstu.infrastructure.support.constant.ValidateConstraint;
import com.ap.iamstu.infrastructure.support.entity.AuditableEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "department")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class DepartmentEntity extends AuditableEntity {
    @Id
    @Column(name = "id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH, nullable = false)
    private String id;

    @Column(name = "name", length = ValidateConstraint.LENGTH.NAME_MAX_LENGTH, nullable = false)
    private String name;

    @Column(name = "code", length = ValidateConstraint.LENGTH.CODE_MAX_LENGTH, nullable = false, unique = true)
    private String code;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "description", length = ValidateConstraint.LENGTH.DESC_MAX_LENGTH)
    private String description;

    @Column(name = "parent_path", length = ValidateConstraint.LENGTH.NOTE_MAX_LENGTH)
    private String parentPath;

    @Column(name = "parent_id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private String parentId;
}
