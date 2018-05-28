//package ru.sbt.jschool.session5.problem2;

public class BooleanFormatter implements JSONTypeFormatter<Boolean> {
    @Override
    public String format(Boolean bool, JSONFormatter formatter, int ctx, StringBuilder lineForm) {
        String line = bool.toString();

        return line;
    }
}
