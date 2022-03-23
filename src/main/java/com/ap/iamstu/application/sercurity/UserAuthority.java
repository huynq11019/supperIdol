package com.ap.iamstu.application.sercurity;

import com.ap.iamstu.infrastructure.support.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAuthority implements Serializable {
    private Instant lastAuthChangeAt;
    private String userId;
    private Boolean isRoot;
    private AccountType accountType;
    private String organizationId;
    private List<String> buildingIds;
    private List<String> grantedPermissions;
}
