package com.ap.iamstu.application.sercurity;

import com.ap.iamstu.infrastructure.support.enums.AccountType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class UserAuthentication extends UsernamePasswordAuthenticationToken {

    private final boolean isRoot;

    private final boolean isClient;

    private final String userId;

//    private final UserLevel userLevel;

    private final AccountType accountType;

    private final String token;

    private final List<String> grantedPermissions;

    private final String organizationId;

    private final List<String> buildingIds;

    public UserAuthentication(Object principal, Object credentials,
                              Collection<? extends GrantedAuthority> authorities,
                              Boolean isRoot,
                              String userId,
                              AccountType accountType,
                              String organizationId,
                              List<String> buildingIds,
                              String token) {
        super(principal, credentials, authorities);
        this.isRoot = isRoot != null && isRoot;
        this.isClient = false;
        this.userId = userId;
        if (accountType == null) {
            accountType = AccountType.STUDENT;
        }
        this.accountType = accountType;
        this.organizationId = organizationId;
        this.buildingIds = buildingIds;
        this.token = token;
        this.grantedPermissions = CollectionUtils.isEmpty(authorities) ? new ArrayList<>()
                : authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

    public boolean isRoot() {
        return isRoot;
    }

    public boolean isClient() {
        return isClient;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getToken() {
        return this.token;
    }

    public List<String> getGrantedPermissions() {
        if (CollectionUtils.isEmpty(this.grantedPermissions)) {
            return new ArrayList<>();
        }
        return grantedPermissions;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public List<String> getBuildingIds() {
        if (CollectionUtils.isEmpty(this.buildingIds)) {
            return new ArrayList<>();
        }
        return buildingIds;
    }

//    public UserLevel getUserLevel() {
//        return userLevel;
//    }

    public AccountType getAccountType() {
        return accountType;
    }
}
