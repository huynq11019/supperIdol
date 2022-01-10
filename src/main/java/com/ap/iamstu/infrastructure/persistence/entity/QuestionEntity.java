package com.ap.iamstu.infrastructure.persistence.entity;

import com.ap.iamstu.infrastructure.support.constant.ValidateConstraint;
import com.ap.iamstu.infrastructure.support.entity.AuditableEntity;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "question")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class QuestionEntity extends AuditableEntity {

    @Id
    @Column(name = "id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH, nullable = false)
    private String id;

    private String content;

    private Boolean isRequired;

    private String type;

    private String answer;

    private String answerType;

    private String responseTable;

    private Integer precise;

    private Boolean isMultiChoice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        QuestionEntity that = (QuestionEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
