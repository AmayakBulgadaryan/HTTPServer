//package ru.sbt.jschool.session5.problem2;

import java.util.Calendar;

public class CalendarFormatter implements JSONTypeFormatter<Calendar> {
    @Override
    public String format(Calendar c, JSONFormatter formatter, int ctx, StringBuilder lineForm) {
        String line = c.get(Calendar.DAY_OF_MONTH)+"."+(c.get(Calendar.MONTH) + 1)+"."+c.get(Calendar.YEAR);
        return line;
    }
}
