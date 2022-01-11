package com.ap.iamstu.application.service;

import com.ap.iamstu.application.sercurity.UserAuthority;

public interface AuthorityService {
    UserAuthority getUserAuthority(String userId);

}
