package com.ap.iamstu.domain.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterCmd {

    private String username;
    private String password;
    private String fullName;
    private String email;
    private String mobile;
}
