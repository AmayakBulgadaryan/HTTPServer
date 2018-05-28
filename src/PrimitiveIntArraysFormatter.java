//package ru.sbt.jschool.session5.problem2;

public class PrimitiveIntArraysFormatter implements JSONTypeFormatter {
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
            int[] ints = (int[]) obj;
            for (int j = 0; j < ints.length; j++) {
                if (j == ints.length - 1)
                    lineForm.append(ints[j]+"\n");
                else lineForm.append(ints[j]+",\n"+spaceLine);
            }
        }

    }

    @Override
    public String format(Object obj, JSONFormatter formatter, int ctx, StringBuilder lineForm) {
        this.buildLineForm(obj, ctx, lineForm);
        lineForm.append(buildSpaceLine(ctx)+"]");
        String result = lineForm.toString();
       // lineForm = new StringBuilder();
        return result;
    }
}
