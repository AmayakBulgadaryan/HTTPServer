//package ru.sbt.jschool.session5.problem2;




import java.util.*;

/**
 * @author NIzhikov
 */
public class JSONFormatterImpl implements JSONFormatter {
    private Map<Class, JSONTypeFormatter> types = new HashMap<>();

    public JSONFormatterImpl() {
        types.put(Collection.class, new CollectionsFormatter());
        types.put(Object.class, new ObjectFormatter());
        types.put(Number.class, new NumberFormatter());
        types.put(Character.class, new CharacterFormatter());
        types.put(Boolean.class, new BooleanFormatter());
        types.put(String.class, new MyStringFormatter());
        types.put(Date.class, new DateFormatter());
        types.put(Calendar.class, new CalendarFormatter());
        types.put(byte[].class, new PrimitiveByteArraysFormatter());
        types.put(short[].class, new PrimitiveShortArraysFormatter());
        types.put(int[].class, new PrimitiveIntArraysFormatter());
        types.put(long[].class, new PrimitiveLongArraysFormatter());
        types.put(float[].class, new PrimitiveFloatArraysFormatter());
        types.put(double[].class, new PrimitiveDoubleArraysFormatter());
        types.put(char[].class, new PrimitiveCharArraysFormatter());
        types.put(boolean[].class, new PrimitiveBooleanArraysFormatter());
        types.put(Object[].class, new ReferenceArraysFormatter());
    }

    @Override public String marshall(Object obj, int ctx) {
        StringBuilder lineForm = new StringBuilder();
        if (obj == null)
            return "null";

        if (obj instanceof Collection)
            return types.get(Collection.class).format(obj, this, ctx,lineForm);
        if (obj.getClass().isArray())
        {
            if (obj.getClass().toString().endsWith("[B"))
                return types.get(byte[].class).format(obj, this, ctx, lineForm);
            if (obj.getClass().toString().endsWith("[S"))
                return types.get(short[].class).format(obj, this, ctx, lineForm);
            if (obj.getClass().toString().endsWith("[I"))
                return types.get(int[].class).format(obj, this, ctx, lineForm);
            if (obj.getClass().toString().endsWith("[J"))
                return types.get(long[].class).format(obj, this, ctx, lineForm);
            if (obj.getClass().toString().endsWith("[F"))
                return types.get(float[].class).format(obj, this, ctx, lineForm);
            if (obj.getClass().toString().endsWith("[D"))
                return types.get(double[].class).format(obj, this, ctx, lineForm);
            if (obj.getClass().toString().endsWith("[C"))
                return types.get(char[].class).format(obj, this, ctx, lineForm);
            if (obj.getClass().toString().endsWith("[Z"))
                return types.get(boolean[].class).format(obj, this, ctx, lineForm);

            return types.get(Object[].class).format(obj, this, ctx, lineForm);
        }
        if (obj instanceof Number)
            return types.get(Number.class).format(obj, this, ctx, lineForm);
        if (obj instanceof Calendar)
            return types.get(Calendar.class).format(obj,this, ctx, lineForm);
        if (!types.containsKey(obj.getClass()))
            return types.get(Object.class).format(obj, this, ctx, lineForm);


        return types.get(obj.getClass()).format(obj, this, ctx, lineForm);
    }

    @Override public <T> boolean addType(Class<T> clazz, JSONTypeFormatter<T> formatter) {
        try {
            if (!types.containsKey(clazz))
            types.put(clazz, formatter);
        }
        catch (Exception e){

        }
        if (types.containsKey(clazz))
        return true;

        return false;
    }
}