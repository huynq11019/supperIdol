package com.ap.iamstu.infrastructure.support.util;

import com.ap.iamstu.infrastructure.support.constant.AuthoritiesConstants;
import com.ap.iamstu.infrastructure.support.error.AuthenticationError;
import com.ap.iamstu.infrastructure.support.exeption.ResponseException;
import com.ap.iamstu.application.sercurity.UserAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static Optional<String> getCurrentUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
    }

    private static String extractPrincipal(Authentication authentication) {
        if (authentication == null)
            return null;
        else if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            return springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        }
        return null;
    }

    public static Optional<String> getCurrentUserJWT() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (Objects.nonNull(authentication) && authentication instanceof UserAuthentication) {
            UserAuthentication userAuthentication = (UserAuthentication) authentication;
            return Optional.of(userAuthentication.getToken());
        }
        return Optional.empty();
    }

    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null &&
                getAuthorities(authentication).noneMatch(AuthoritiesConstants.ANONYMOUS::equals);
    }

    public static boolean isCurrentUserInRole(String authority) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null &&
                getAuthorities(authentication).anyMatch(authority::equals);
    }

    private static Stream<String> getAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority);
    }

    public static Optional<String> getCurrentUserLoginId() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (Objects.nonNull(authentication) && authentication instanceof UserAuthentication) {
            UserAuthentication userAuthentication = (UserAuthentication) authentication;
            if (Objects.nonNull(userAuthentication.getUserId())) {
                return Optional.of(userAuthentication.getUserId());
            }
            return Optional.empty();
        }
        return Optional.empty();
    }

    public static Boolean isAdmin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (Objects.nonNull(authentication) && authentication instanceof UserAuthentication) {
            UserAuthentication userAuthentication = (UserAuthentication) authentication;
            return userAuthentication.isRoot();
        }
        return false;
    }

//    public static Optional<UserLevel> getCurrentUserLevel() {
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        Authentication authentication = securityContext.getAuthentication();
//        if (Objects.nonNull(authentication) && authentication instanceof UserAuthentication) {
//            UserAuthentication userAuthentication = (UserAuthentication) authentication;
//            return Optional.ofNullable(userAuthentication.getUserLevel());
//        }
//        return Optional.empty();
//    }

    public static UserAuthentication authentication() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (Objects.nonNull(authentication) && authentication instanceof UserAuthentication) {
            return (UserAuthentication) authentication;
        }
        throw new ResponseException(AuthenticationError.UNAUTHORISED);
    }
}
