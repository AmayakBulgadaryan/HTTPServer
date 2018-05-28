//package ru.sbt.jschool.session5.problem2;

public class PrimitiveShortArraysFormatter implements JSONTypeFormatter {
    private final int ind0 = 4;
    //private StringBuilder lineForm = new StringBuilder();

    private String buildSpaceLine(int indent){
        String spaceLine = "";
        for (int i = 0; i < indent; i++) {
            spaceLine += " ";
        }
        return spaceLine;
    }

    private void buildLineForm(Object obj, int ctx, StringBuilder lineForm){
        String type = obj.getClass().toString();
        int depth = type.lastIndexOf("[") - type.indexOf("[") + 1;
        String spaceLine = "";
        spaceLine = buildSpaceLine(ctx+ind0);
        lineForm.append("[\n").append(spaceLine);

        if (depth!=1) {
            Object[] objects = (Object[]) obj;
            for (int i = 0; i < objects.length; i++) {
                buildLineForm(objects[i], ctx + ind0, lineForm);
                lineForm.append(spaceLine+"]\n");
                if (i!=objects.length-1) lineForm.append(spaceLine);
            }

        }
        else {
            short[] shorts = (short[]) obj;
            for (int j = 0; j < shorts.length; j++) {
                if (j == shorts.length - 1)
                    lineForm.append(shorts[j]+"\n");
                else lineForm.append(shorts[j]+",\n"+spaceLine);
            }
        }

    }

    @Override
    public String format(Object obj, JSONFormatter formatter, int ctx, StringBuilder lineForm) {
        this.buildLineForm(obj, ctx, lineForm);
        lineForm.append(buildSpaceLine(ctx)+"]");
        String result = lineForm.toString();
        //lineForm = new StringBuilder();
        return result;
    }
}
