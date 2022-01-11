package com.ap.iamstu.application.sercurity;

import com.ap.iamstu.infrastructure.support.error.AuthorizationError;
import com.ap.iamstu.infrastructure.support.exeption.ResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.regex.Pattern;

@Slf4j
@Component
public class RegexPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        String requiredPermission = permission.toString();

        log.warn("RegexPermissionEvaluator hasPermission");

        if (!(authentication instanceof UserAuthentication)) {
            throw new ResponseException(
                    MessageFormat.format(
                            AuthorizationError.NOT_SUPPORTED_AUTHENTICATION.getMessage(),
                            authentication.getClass().getName()),
                    AuthorizationError.NOT_SUPPORTED_AUTHENTICATION, authentication.getClass().getName());
        }
        UserAuthentication userAuthentication = (UserAuthentication) authentication;
        // root được full quyền truy cập
        if (userAuthentication.isRoot()) {
            return true;
        }
        // nếu không có quyền truy cập thì trả về false
        boolean isPermitted = userAuthentication.getGrantedPermissions().stream()
                .anyMatch(p -> Pattern.matches(p, requiredPermission));

        if (!isPermitted) {
            throw new ResponseException(
                    MessageFormat.format(
                            AuthorizationError.ACCESS_DENIED.getMessage(), permission),
                    AuthorizationError.ACCESS_DENIED, permission);
        }

        return true;
    }

    @Override
    public boolean hasPermission(
            Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return hasPermission(authentication, null, permission);
    }
}
