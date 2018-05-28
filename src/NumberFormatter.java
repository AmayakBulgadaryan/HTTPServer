//package ru.sbt.jschool.session5.problem2;



public class NumberFormatter implements JSONTypeFormatter<Number> {
    @Override
    public String format(Number number, JSONFormatter formatter, int ctx, StringBuilder lineForm) {

        String line = number.toString();
        return line;
    }
}
