//package ru.sbt.jschool.session5.problem2;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ObjectFormatter implements JSONTypeFormatter {

//  private StringBuilder lineForm = new StringBuilder();
    private int ind0 = 4;

    private String buildSpaceLine(int indent){
        String spaceLine = "";
        for (int i = 0; i < indent; i++) {
            spaceLine+=" ";
        }
        return spaceLine;
    }
    @Override
    public String format(Object obj, JSONFormatter formatter, int ctx, StringBuilder lineForm) {
        Field[] fields = null;
        Class clazz = obj.getClass();
        List<String> allFieldsNames = new ArrayList<>();
        lineForm.append("{");
        while(!clazz.equals(Object.class))
        {
            fields = clazz.getDeclaredFields();
            for (Field field: fields) {
                String name = field.getName();
                if (!allFieldsNames.contains(name)) {
                    allFieldsNames.add(name);
                    try {
                        lineForm.append("\n");
                        field.setAccessible(true);
                        lineForm.append(buildSpaceLine(ctx+ind0)+"\""+name+"\": ").append(formatter.marshall(field.get(obj), ctx + ind0));
                        lineForm.append("\n");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
        lineForm.append(buildSpaceLine(ctx)+"}");
        String result = lineForm.toString();
       // lineForm = new StringBuilder();

        return result;
    }
}



