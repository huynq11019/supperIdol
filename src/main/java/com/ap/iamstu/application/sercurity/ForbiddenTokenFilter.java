package com.ap.iamstu.application.sercurity;

import com.ap.iamstu.infrastructure.support.error.AuthenticationError;
import com.ap.iamstu.infrastructure.support.error.ErrorResponse;
import com.ap.iamstu.infrastructure.support.i18n.LocaleStringService;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.mbamc.common.dto.error.ErrorResponse;
//import com.mbamc.common.error.AuthenticationError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class ForbiddenTokenFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER = "Bearer ";
    private final TokenCacheService tokenCacheService;
    private final ObjectMapper objectMapper;
    private final LocaleStringService localeStringService;

    public ForbiddenTokenFilter(TokenCacheService tokenCacheService, ObjectMapper objectMapper, LocaleStringService localeStringService) {
        this.tokenCacheService = tokenCacheService;
        this.objectMapper = objectMapper;
        this.localeStringService = localeStringService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        String token = resolveToken(httpServletRequest);
        log.info("token: {}", token);
        if (tokenCacheService.isInvalidToken(token)) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "InvalidJWT");
            String message = localeStringService.getMessage(
                    AuthenticationError.UNAUTHORISED.getName(),
                    AuthenticationError.UNAUTHORISED.getMessage());
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(
                    ErrorResponse.builder()
                            .error(AuthenticationError.UNAUTHORISED.getMessage())
                            .code(AuthenticationError.UNAUTHORISED.getCode())
                            .message(message).build()));
            return;
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
//        String token = resolveToken(request);
//
//        log.info("token: {}", token);

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication == null) {
            return true;
        }
        if (authentication instanceof JwtAuthenticationToken) {
            return !authentication.isAuthenticated();
        }
        return authentication instanceof AnonymousAuthenticationToken;
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)) {
            return bearerToken.substring(BEARER.length());
        }

        return null;
    }
}
