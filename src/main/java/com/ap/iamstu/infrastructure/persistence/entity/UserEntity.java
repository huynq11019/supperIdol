package com.ap.iamstu.infrastructure.persistence.entity;
import com.ap.iamstu.infrastructure.support.constant.ValidateConstraint;
import com.ap.iamstu.infrastructure.support.entity.AuditableEntity;
import com.ap.iamstu.infrastructure.support.enums.AccountType;
import com.ap.iamstu.infrastructure.support.enums.Gender;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

@Entity
//@Table(name = "users", indexes = {
//        @Index(name = "user_username_idx", columnList = "username"),
//        @Index(name = "user_deleted_idx", columnList = "deleted")
//})
@Table(name = "users")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class UserEntity extends AuditableEntity {
    @Id
    @Column(name = "id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH, nullable = false)
    private String id;

    @Column(name = "username", length = ValidateConstraint.LENGTH.CODE_MAX_LENGTH, nullable = false)
    private String username;

    @Column(name = "password", length = ValidateConstraint.LENGTH.VALUE_MAX_LENGTH)
    private String password;

    @Column(name = "full_name", length = ValidateConstraint.LENGTH.NAME_MAX_LENGTH, nullable = false)
    private String fullName;

    @Column(name = "email", length = ValidateConstraint.LENGTH.EMAIL_MAX_LENGTH, nullable = false)
    private String email;

    @Column(name = "phone_number", length = ValidateConstraint.LENGTH.PHONE_MAX_LENGTH, nullable = false)
    private String phoneNumber;

    @Column(name = "day_of_birth")
    private LocalDate dayOfBirth;

    @Column(name = "gender", length = ValidateConstraint.LENGTH.ENUM_MAX_LENGTH, nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender = Gender.OTHER;

    @Column(name = "department_name", length = ValidateConstraint.LENGTH.NAME_MAX_LENGTH)
    private String departmentName;

    @Column(name = "avatar_file_id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private String avatarFileId;

    @Column(name = "account_type", length = ValidateConstraint.LENGTH.ENUM_MAX_LENGTH)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(name = "last_auth_change_at")
    private Instant lastAuthChangeAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserEntity that = (UserEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
