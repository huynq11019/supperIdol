package com.ap.iamstu.domain;

import com.ap.iamstu.domain.command.ClassCreateCmd;
import com.ap.iamstu.domain.command.ClassUpdateCmd;
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
public class Class extends AuditableDomain {
    private String id;
    private String code;
    private String name;
    private String description;
    private String fileId;
    private Boolean deleted;


    public static Class of(String id, String name, String description, String fileId) {
        return Class.builder()
                .id(id)
                .name(name)
                .description(description)
                .fileId(fileId)
                .build();
    }

    public Class(ClassCreateCmd cmd) {
        this.id = IdUtils.nextId();
        this.code = cmd.getCode();
        this.name = cmd.getName();
        this.description = cmd.getDescription();
        this.fileId = cmd.getFileId();
    }

    public void update(ClassUpdateCmd cmd) {
        this.name = cmd.getName();
        this.description = cmd.getDescription();
        this.fileId = cmd.getFileId();
    }

    public void delete() {
        this.setDeleted(true);
    }
}
