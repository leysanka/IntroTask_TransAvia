package com.epam.transavia.demo.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeConverter {

    private static DateTimeFormatter parseToLocalTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static DateTimeFormatter parseToSourceDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    public static LocalDateTime formatStringToLocalDateTime(String stringTime) {
        return LocalDateTime.parse(stringTime, parseToLocalTimeFormatter);
    }

    public static String convertLocalDateToSourceStringFormat(LocalDate localDate) {
        return localDate.format(parseToSourceDateFormatter);
    }
}
