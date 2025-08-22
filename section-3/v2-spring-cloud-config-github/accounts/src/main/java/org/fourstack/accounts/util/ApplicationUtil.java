package org.fourstack.accounts.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;
import java.util.Random;

public class ApplicationUtil {
    private static ObjectMapper objectMapper;

    private ApplicationUtil() {
        /* restricted Access */
    }

    public static long generateAccountNumber() {
        return 1000000000L + new Random().nextInt(900000000);
    }

    public static boolean isStringNullOrEmpty(String str) {
        return Objects.isNull(str) || str.isBlank();
    }

    private static ObjectMapper getObjectMapperInstance() {
        if (Objects.isNull(objectMapper)) {
            synchronized (ApplicationUtil.class) {
                if (Objects.isNull(objectMapper)) {
                    objectMapper = new ObjectMapper();
                }
            }
        }
        return objectMapper;
    }

    public static String convertToString(Object obj) {
        try {
            return getObjectMapperInstance().writeValueAsString(obj);
        } catch (Exception e) {
            return "";
        }
    }
}
