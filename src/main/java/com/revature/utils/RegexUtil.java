package com.revature.utils;

public class RegexUtil {
    public static final String PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    private RegexUtil() {
        super();
    }
}
