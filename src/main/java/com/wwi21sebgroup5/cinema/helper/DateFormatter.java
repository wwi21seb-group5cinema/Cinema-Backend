package com.wwi21sebgroup5.cinema.helper;

import java.time.format.DateTimeFormatter;

public class DateFormatter {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static final DateTimeFormatter TMDB_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

}
