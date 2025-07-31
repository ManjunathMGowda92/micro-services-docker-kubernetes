package org.fourstack.accounts.util;

import java.util.Objects;
import java.util.Random;

public class ApplicationUtil {
    private ApplicationUtil(){
        /* restricted Access */
    }

    public static long generateAccountNumber() {
        return 1000000000L + new Random().nextInt(900000000);
    }

    public static boolean isStringNullOrEmpty(String str) {
        return Objects.isNull(str) || str.isBlank();
    }
}
