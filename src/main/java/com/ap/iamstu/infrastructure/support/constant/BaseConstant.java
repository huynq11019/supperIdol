package com.ap.iamstu.infrastructure.support.constant;

import java.util.Arrays;
import java.util.List;

public interface BaseConstant {

    String SYSTEM_ACCOUNT = "system";

    String DEFAULT_LANGUAGE = "en";

    String LOGIN_REGEX = "^[_.@A-Za-z0-9-]*$";

    String DOT = ".";

    String HTTP_PREFIX = "http";
    List<String> EXTENSIONS = Arrays.asList(("bmp,jpg,png,jpeg".split(",")));
    String AUTHORITY_TYPE = "auth_type";
    String USER_ID = "user_id";
    String EMAIL = "email";
    String CLIENT = "client";
    String REFRESH_TOKEN = "refresh_token";
    String CLIENT_AUTHORITY = "client";

    static List<String> getValidExtensions() {
        return EXTENSIONS;
    }

    interface IMAGE_SIZE_URI {
        String THUMB = "/crop/480x320";
    }
}
