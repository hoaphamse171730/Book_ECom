package com.tbsd.crawler.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateUtil {
    public static Date fromTikiDate(String date) throws ParseException {
        try {
            return new Date(new SimpleDateFormat("yyyy-MM-ddd HH:mm:ss").parse(date).getTime());
        } catch (ParseException e) {
            return new Date(new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", new Locale("us")).parse(date).getTime());
        }
    }
}
