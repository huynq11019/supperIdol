package com.ap.iamstu.application.sercurity;

import com.ap.iamstu.application.service.AuthorityService;
import com.ap.iamstu.infrastructure.persistence.entity.UserEntity;
import com.ap.iamstu.infrastructure.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthorityService authorityService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        log.info("authenticating: {}", userName);
        if (!StringUtils.hasLength(userName)) {
            throw new UsernameNotFoundException("User name is null");
        }
        UserEntity user = null;
//            Đăng nhập bằng email
        if (userName.contains("@")) {
            Optional<UserEntity> userEntity = userRepository.findByEmail(userName);
            if (userEntity.isPresent()) {
                user = userEntity.get();
            }
        } else {
//            Đămg nhập bằng user Name
            Optional<UserEntity> optionalUser = userRepository.findByUserName(userName);
            if (optionalUser.isPresent()) {
                user = optionalUser.get();
            }
        }

        if (user == null) {
            log.error("User not found: {}", userName);
            return null;
        }
        return enrichUserInfo(user);
    }

    private User enrichUserInfo(UserEntity userEntity) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        List<String> listAuthorities = authorityService.getUserAuthority(userEntity.getId()).getGrantedPermissions();
        authorities = listAuthorities.isEmpty() ? authorities : listAuthorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        return new User(userEntity.getUserName(), userEntity.getPassword(), authorities);
    }
}
