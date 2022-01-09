package com.ap.iamstu.infrastructure.support.util;

import java.util.UUID;

public class IdUtils {

    public static String nextId() {
        //need fix for add partition info
        return UUID.randomUUID().toString();
    }
}
