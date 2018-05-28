//package ru.sbt.jschool.session5.problem2;


import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author NIzhikov
 */
public class DateFormatter implements JSONTypeFormatter<Date> {
    @Override public String format(Date date, JSONFormatter formatter, int ctx, StringBuilder lineForm) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(date);
    }
}
