//package ru.sbt.jschool.session5.problem2;



/**
 */
@FunctionalInterface
public interface JSONTypeFormatter<T> {
    String format(T t, JSONFormatter formatter, int ctx, StringBuilder lineForm);
}
