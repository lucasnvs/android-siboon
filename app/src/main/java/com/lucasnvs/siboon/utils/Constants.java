package com.lucasnvs.siboon.utils;

public final class Constants {
    private Constants() {
        throw new UnsupportedOperationException("Cannot instantiate a utility class.");
    }

    public static final String REGEX_VALID_PASSWORD = "^(?=.*\\d)(?=.*[!@#$%^&*()])(.{8,})$";

    // API
    public static final String BASE_URL = "http://10.0.2.2/siboon/";

    // DB
    public static final String DB_NAME = "siboon_database";
    public static final int DB_VERSION = 4;
}
