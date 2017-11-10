package com.epam.transavia.demo.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeHelper {

    private static final DateTimeFormatter PARSE_TO_LOCAL_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter PARSE_TO_SOURCE_DATE_FORMATTER = DateTimeFormatter.ofPattern("d MMM yyyy");


    public static LocalDateTime formatStringToLocalDateTime(String stringTime) {
        return LocalDateTime.parse(stringTime, PARSE_TO_LOCAL_TIME_FORMATTER);
    }

    public static String convertLocalDateToSourceStringFormat(LocalDate localDate) {
        return localDate.format(PARSE_TO_SOURCE_DATE_FORMATTER);
    }

    public static String calculateDateNowPlusLag(long lagDays) {
        return convertLocalDateToSourceStringFormat(LocalDate.now().plusDays(lagDays));
    }
}
