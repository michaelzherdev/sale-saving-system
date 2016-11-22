package com.mzherdev.salesavingsystem.tools;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private TimeUtils() { }

    public static <T extends Comparable<? super T>> boolean isBetween(T value, T start, T end) {
        return value.compareTo(start) >= 0 && value.compareTo(end) <= 0;
    }

    public static <T extends Comparable<? super T>> boolean isEqual(T value1, T value2) {
        return value1.compareTo(value2) == 0;
    }

    public static String toString(LocalDate ld) {
        return ld == null ? "" : ld.format(DATE_FORMATTER);
    }

    public static String toString(LocalTime lt) {
        return lt == null ? "" : lt.format(TIME_FORMATTER);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}
