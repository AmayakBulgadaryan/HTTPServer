//package ru.sbt.jschool.session5.problem2;


import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;


public class CollectionsFormatter implements JSONTypeFormatter<Collection>{
    public static void main(String[] args) {
        ArrayList<String>[][][] arrayLists3 = new ArrayList[2][2][3];
        ArrayList<String> arls1 = new ArrayList<>();
        arls1.add("Ama");
        arls1.add("Ama");
        arls1.add("Ama");
        ArrayList<String> arls2 = new ArrayList<>();
        arls2.add("yak");
        arls2.add("yak");
        arls2.add("yak");
        ArrayList<String> arls3 = new ArrayList<>();
        arls3.add("good");
        arls3.add("good");
        arls3.add("good");

        arrayLists3[0][0][0] = arls1;
        arrayLists3[0][0][1] = arls2;
        arrayLists3[0][0][2] = arls3;

        System.out.println(arrayLists3.getClass().isArray());
        System.out.println(new JSONFormatterImpl().marshall(arrayLists3, 0));
        System.out.println();

        ArrayList<ArrayList<LinkedList<Integer>>> arrayLists = new ArrayList<>();

        LinkedList<Integer> linkedList = new LinkedList<>();
        linkedList.add(3);
        linkedList.add(4);
        linkedList.add(5);
        ArrayList<LinkedList<Integer>> ar = new ArrayList<>();
        ar.add(linkedList);
        arrayLists.add(ar);

        System.out.println(new JSONFormatterImpl().marshall(arrayLists, 0));
        System.out.println();

        ArrayList[][][] ar2 = new ArrayList[2][2][2];
        ar2[0][0][0] = arls1;
        ar2[0][0][1] = arls2;
        ArrayList<ArrayList<String>[][][]> arrayLists1 = new ArrayList<>();
        arrayLists1.add(ar2);
        JSONFormatterImpl jsonFormatter = new JSONFormatterImpl();
        System.out.println(jsonFormatter.marshall(arrayLists1, 0));
    }
    private final int ind0 = 4;
    //private StringBuilder lineForm = new StringBuilder();

    private String buildSpaceLine(int indent){
        String  spaceLine = "";
        for (int i = 0; i < indent; i++) {
            spaceLine += " ";
        }
        return spaceLine;
    }

    private void buildLineForm(Collection collection, JSONFormatter formatter, int ctx, StringBuilder lineForm){
        String spaceLine = "";
        spaceLine = buildSpaceLine(ctx+ind0);
        int i = 0;
        lineForm.append("[\n").append(spaceLine);
        for (Object object: collection) {
            Class clazz = object.getClass();
            if (object instanceof Collection) {
                buildLineForm((Collection) object, formatter, ctx + ind0, lineForm);
                lineForm.append(spaceLine + "]\n");
                if (i != collection.size() - 1) lineForm.append(spaceLine);
            }
            else {
                if (i!=collection.size()-1)
                    lineForm.append(formatter.marshall(object, ctx + ind0)+",\n"+spaceLine);
                else lineForm.append(formatter.marshall(object, ctx + ind0)+"\n") ;
            }
            i++;
        }
    }
    @Override
    public String format(Collection collection, JSONFormatter formatter, int ctx, StringBuilder lineForm) {
        this.buildLineForm(collection, formatter, ctx, lineForm);
        lineForm.append(buildSpaceLine(ctx)+"]");
        String result = lineForm.toString();
//        lineForm = new StringBuilder();
        return result;
    }
}
