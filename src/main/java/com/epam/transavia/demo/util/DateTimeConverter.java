package com.epam.transavia.demo.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeConverter {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static LocalDateTime formatStringToLocalDateTime(String stringTime) {
        return LocalDateTime.parse(stringTime, formatter);
    }
}
