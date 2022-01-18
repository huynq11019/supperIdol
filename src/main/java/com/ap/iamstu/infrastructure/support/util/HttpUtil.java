package com.ap.iamstu.infrastructure.support.util;

import javax.servlet.http.HttpServletRequest;

import static java.util.Objects.nonNull;

/**
 * Utility class for generating random Strings.
 */
public final class HttpUtil {

    public final static int REDIRECTION_CODE = 302;
    private final static int OK_CODE = 200;
    private final static int NOT_FOUND_CODE = 404;

    public static String extractIp(HttpServletRequest request) {
        String clientIp;
        String clientXForwardedForIp = request.getHeader("x-forwarded-for");
        if (nonNull(clientXForwardedForIp)) {
            clientIp = parseXForwardedHeader(clientXForwardedForIp);
        } else {
            clientIp = request.getRemoteAddr();
        }

        return clientIp;
    }

    private static String parseXForwardedHeader(String header) {
        return header.split(" *, *")[0];
    }
}
