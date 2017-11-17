package com.epam.transavia.demo.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeHelper {

    private static final DateTimeFormatter LOCAL_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter BOOKING_INPUT_DATE_FORMATTER = DateTimeFormatter.ofPattern("d MMM yyyy");


    public static LocalDateTime formatStringToLocalDateTime(String stringTime) {
        return LocalDateTime.parse(stringTime, LOCAL_TIME_FORMATTER);
    }

    public static String convertLocalDateToBookingDateInputStringFormat(LocalDate localDate) {
        return localDate.format(BOOKING_INPUT_DATE_FORMATTER);
    }

    public static String generateBookingInputDateAsNowPlusLag(long lagDays) {
        return convertLocalDateToBookingDateInputStringFormat(LocalDate.now().plusDays(lagDays));
    }

    public static String formatLocalNow(DateTimeFormatter expectedFormat) {
        return LocalDateTime.now().format(expectedFormat);
    }
}
