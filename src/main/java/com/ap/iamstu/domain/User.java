package com.ap.iamstu.domain;

import com.ap.iamstu.infrastructure.support.domain.AuditableDomain;
import com.ap.iamstu.infrastructure.support.enums.AccountType;
import com.ap.iamstu.infrastructure.support.enums.AuthenticationType;
import com.ap.iamstu.infrastructure.support.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter(AccessLevel.PRIVATE)
@Getter
public class User extends AuditableDomain {
    private String id;
    private String username;

    @JsonIgnore
    private String password;
    private String fullName;
    private String email;
    private String phoneNumber;
    private LocalDate dayOfBirth;
    private Gender gender;
    private List<Role> roles = new ArrayList<>();
    private Boolean deleted;
    private AuthenticationType authenticationType;
    private String organizationId;
    private String employeeCode;
    private String title;
    private String description;
    //    private UserStatus status;
//    private List<UserLocation> userLocations = new ArrayList<>();
    private String departmentName;
    //    private UserLevel userLevel;
    private String avatarFileId;
    private String avatarFileUrl;
    private AccountType accountType;
    private Instant lastAuthChangeAt;

    @JsonIgnore
    private List<UserRole> userRoles;
}
