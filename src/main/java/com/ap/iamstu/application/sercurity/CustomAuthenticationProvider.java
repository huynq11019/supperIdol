package com.ap.iamstu.application.sercurity;

import com.ap.iamstu.application.service.AuthFailCacheService;
import com.ap.iamstu.infrastructure.persistence.entity.UserEntity;
import com.ap.iamstu.infrastructure.persistence.repository.UserRepository;
import com.ap.iamstu.infrastructure.support.error.BadRequestError;
import com.ap.iamstu.infrastructure.support.exeption.ResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final Logger log = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthFailCacheService authFailCacheService;

    public CustomAuthenticationProvider(UserRepository userRepository,
                                        PasswordEncoder passwordEncoder,
                                        AuthFailCacheService authFailCacheService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authFailCacheService = authFailCacheService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.debug("Authenticating {}", authentication);
        String username = authentication.getName();
        String credentials = (String) authentication.getCredentials();

        Optional<UserEntity> optionalUserEntity = userRepository.findByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;

        if (authenticationToken.isAuthenticated()) {
            if (optionalUserEntity.isEmpty()) {
                // login fail -> increase number of block
                BadRequestError error = authFailCacheService.checkLoginFail(username);
                this.onError(error);
            }
            UserEntity userEntity = optionalUserEntity.get();
            String passwordStore = userEntity.getPassword();
            boolean matches = passwordEncoder.matches(credentials, passwordStore);

            //check UserName Password:
            if (!matches) {
                // login fail -> increase number of block
                BadRequestError error = authFailCacheService.checkLoginFail(username);
                this.onError(error);
            }
        }
        return new UsernamePasswordAuthenticationToken(username, authentication.getCredentials(), new ArrayList<>());
    }

    private void onError(BadRequestError error) {
        if (error == null) {
            throw new BadCredentialsException("Bad credential!");
        } else {
            throw new ResponseException(error.getMessage(), error);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
