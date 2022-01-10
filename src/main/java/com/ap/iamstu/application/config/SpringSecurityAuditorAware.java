package com.ap.iamstu.application.config;

import com.ap.iamstu.infrastructure.support.constant.BaseConstant;
import com.ap.iamstu.infrastructure.support.util.SecurityUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;

import java.util.Optional;

/**
 * Implementation of {@link AuditorAware} based on Spring Security.
 */
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    @NonNull
    public Optional<String> getCurrentAuditor() {
        return Optional.of(SecurityUtils.getCurrentUser().orElse(BaseConstant.SYSTEM_ACCOUNT));
    }
}
