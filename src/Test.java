import engine.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Test {

    public static void main(String[] args) {
        QuestionDB questionBD = new QuestionDB(1, "1", "2");
        ArrayList<Option> arrayList = new ArrayList<>();
        arrayList.add(new Option(1, "1",true));
        arrayList.add(new Option(2, "2",false));
        arrayList.add(new Option(3, "3",true));
        arrayList.add(new Option(4, "4",true));
        questionBD.setOptions(arrayList);
        System.out.println(questionBD);
        QuestionJSON questionJSON = questionBD.toQuestionJSON();
        System.out.println(questionJSON);
        System.out.println(questionJSON.checkCorrectAnswer(Arrays.asList(0,1,2,3)));
        questionBD = questionJSON.toQuestionBD();
        System.out.println(questionBD);
    }
}
