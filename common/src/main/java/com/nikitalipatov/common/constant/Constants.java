package com.nikitalipatov.common.constant;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Constants {
    public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DATE_PATTERN, Locale.ENGLISH);
}
