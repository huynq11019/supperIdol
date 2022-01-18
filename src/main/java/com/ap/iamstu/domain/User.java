package com.ap.iamstu.domain;

import com.ap.iamstu.domain.command.UserCreateCmd;
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
import java.util.UUID;

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

    public User(UserCreateCmd userCreateCmd) {
        this.id = UUID.randomUUID().toString();
        this.username = userCreateCmd.getUsername();
        this.password = userCreateCmd.getPassword();
        this.fullName = userCreateCmd.getFullName();
        this.email = userCreateCmd.getEmail();
        this.phoneNumber = userCreateCmd.getPhoneNumber();
        this.dayOfBirth = userCreateCmd.getDayOfBirth();
        this.gender = userCreateCmd.getGender();
        this.avatarFileId = userCreateCmd.getAvatarFileId();
        this.roles = userCreateCmd.getRoles();
        this.deleted = userCreateCmd.getDeleted();

    }

    public User( String username, String password, String fullName, String email, String phoneNumber, LocalDate dayOfBirth, Gender gender, List<Role> roles, AuthenticationType authenticationType, String organizationId, String employeeCode, String title, String description, String departmentName, String avatarFileId, String avatarFileUrl, AccountType accountType, List<UserRole> userRoles) {
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dayOfBirth = dayOfBirth;
        this.gender = gender;
        this.roles = roles;
        this.authenticationType = authenticationType;
        this.organizationId = organizationId;
        this.employeeCode = employeeCode;
        this.title = title;
        this.description = description;
        this.departmentName = departmentName;
        this.avatarFileId = avatarFileId;
        this.avatarFileUrl = avatarFileUrl;
        this.accountType = accountType;
        this.userRoles = userRoles;

        this.deleted = false;

    }

    public void update(String username, String password, String fullName, String email, String phoneNumber, LocalDate dayOfBirth) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dayOfBirth = dayOfBirth;
    }

    public void inactive() {
        this.deleted = true;
    }

    public void active() {
        this.deleted = false;
    }

    public void changePassword(String newPassword) {

        this.password = newPassword;
        this.lastAuthChangeAt = Instant.now();
    }
//    public void createPermission(String permissionId) {
//        this.roles.add(new Role(permissionId));
//    }
//
//    public void updatePermission(String permissionId) {
//        this.roles.add(new Role(permissionId));
//    }
}
