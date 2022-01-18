package com.ap.iamstu.domain.command;

import com.ap.iamstu.domain.Role;
import com.ap.iamstu.infrastructure.support.enums.AuthenticationType;
import com.ap.iamstu.infrastructure.support.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreateCmd {
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
    private String avatarFileId;
    //    private UserStatus status;
//    private List<UserLocation> userLocations = new ArrayList<>();
    private String departmentName;
}
