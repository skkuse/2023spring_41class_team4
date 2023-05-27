package com.example.server.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class DateUtils {

    public static String formattedDate(LocalDateTime dateTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(dateTime);
    }
}
