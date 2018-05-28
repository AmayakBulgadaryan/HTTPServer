//package ru.sbt.jschool.session5.problem2;


public class ReferenceArraysFormatter implements JSONTypeFormatter {
    private final int ind0 = 4;
   // private StringBuilder lineForm = new StringBuilder();

    private String buildSpaceLine(int indent){
        String spaceLine = "";
        for (int i = 0; i < indent; i++) {
            spaceLine += " ";
        }
        return spaceLine;
    }

    private void buildLineForm(Object obj, JSONFormatter formatter, int ctx, StringBuilder lineForm){
        String type = obj.getClass().toString();
        int depth = type.lastIndexOf("[") - type.indexOf("[") + 1;
        String spaceLine = "";
        spaceLine = buildSpaceLine(ctx+ind0);
        lineForm.append("[\n").append(spaceLine);
        Object[] objects = (Object[]) obj;
        for (int i = 0; i < objects.length; i++) {
            if (depth!=1) {
                buildLineForm(objects[i], formatter,ctx+ind0, lineForm);
                lineForm.append(spaceLine+"]\n");
                if (i!=objects.length-1)
                    lineForm.append(spaceLine);
            }
            else {
                if (i!=objects.length-1)
                    lineForm.append(formatter.marshall(objects[i],ctx+ind0)+",\n"+spaceLine);
                else lineForm.append(formatter.marshall(objects[i], ctx+ind0)+"\n");
            }
        }
    }

    @Override
    public String format(Object obj, JSONFormatter formatter, int ctx, StringBuilder lineForm) {
        this.buildLineForm(obj, formatter, ctx, lineForm);
        lineForm.append(buildSpaceLine(ctx)+"]");
        String result = lineForm.toString();
        //lineForm = new StringBuilder();
        return result;
    }
}
