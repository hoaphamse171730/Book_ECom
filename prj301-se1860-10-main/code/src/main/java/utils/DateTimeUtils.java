package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateTimeUtils {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static java.sql.Date stringToDate(String dateStr) throws ParseException {
        java.util.Date utilDate = dateFormat.parse(dateStr);
        return new java.sql.Date(utilDate.getTime());
    }
}
