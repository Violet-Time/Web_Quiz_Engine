package engine.model;

import engine.model.Option;
import engine.model.Question;
import engine.model.QuestionDB;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

public class QuestionJSON extends Question {

    @NotNull(message = "Options is mandatory") @Size(min = 2)
    private List<String> options = new ArrayList<>();
    private List<Integer> answer = new ArrayList<>();

    public QuestionJSON() {
    }

    public QuestionJSON(int id, String title, String text) {
        super(id, title, text);
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    private List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }

    public QuestionDB toQuestionBD() {
        QuestionDB questionDB = new QuestionDB();
        questionDB.setText(getText());
        questionDB.setTitle(getTitle());
        List<Option> option = new ArrayList<>();
        Boolean[] userAnswersBoolean = new Boolean[options.size()];
        Arrays.fill(userAnswersBoolean, false);
        for (int i = 0; i < answer.size(); i++) {
            userAnswersBoolean[answer.get(i)] = true;
        }
        for (int i = 0; i < options.size(); i++) {
            option.add(new Option(options.get(i), userAnswersBoolean[i]));
        }
        questionDB.setOptions(option);
        return questionDB;
    }

    public boolean checkCorrectAnswer(List<Integer> userAnswers) {
        /*
        Boolean[] userAnswersBoolean = new Boolean[options.size()];
        Arrays.fill(userAnswersBoolean, false);
        if (userAnswers != null) {
            for (int i = 0; i < userAnswers.length; i++) {
                userAnswersBoolean[userAnswers[i]] = true;
            }
        }

        Boolean[] tmp = options.stream()
                .sorted(Comparator.comparingInt(Option::getId))
                .map(Option::isTrue)
                .toArray(Boolean[]::new);
*/
        return answer.equals(userAnswers);
    }

    @Override
    public String toString() {
        return "QuestionJSON{" +
                "\"id\":\"" + getId() + '\"' +
                "\"title\":\"" + getTitle() + '\"' +
                ",\"text\":\"" + getText() + '\"' +
                "options=" + options +
                ", answer=" + answer +
                '}';
    }
}
