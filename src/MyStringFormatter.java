//package ru.sbt.jschool.session5.problem2;



public class MyStringFormatter implements JSONTypeFormatter<String> {
    @Override
    public String format(String s, JSONFormatter formatter, int ctx, StringBuilder lineForm) {
        return "\"" + s + "\"";
    }
}
