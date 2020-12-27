package engine.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Entity
@JsonSerialize(using = QuestionSerializer.class)
public class QuestionDB extends Question {

    @NotNull(message = "Options is mandatory") @Size(min = 2)
    @OneToMany(cascade = CascadeType.ALL)// @JoinColumn(nullable = false)
    private List<Option> options = new ArrayList<>();
    //@OneToMany(cascade = CascadeType.ALL) @JoinColumn(nullable = false)
    //private List<Completed> completedList = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;


    public QuestionDB() {
    }

    public QuestionDB(int id, String title, String text, User user) {
        super(id, title, text);
        this.user = user;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public QuestionJSON toQuestionJSON() {
        QuestionJSON questionJSON = new QuestionJSON(getId(), getTitle(), getText());
        options.sort(Comparator.comparingInt(Option::getId));
        questionJSON.setOptions(getJsonOptions());
        questionJSON.setAnswer(getJsonAnswers());
        return questionJSON;
    }

    public List<String> getJsonOptions() {
        return options.stream().map(Option::getName).collect(Collectors.toList());
    }

    public List<Integer> getJsonAnswers() {
        return IntStream.range(0,options.size()).filter(i -> options.get(i).isTrue()).boxed().collect(Collectors.toList());
    }

   /* public List<Completed> getCompletedList() {
        return completedList;
    }

    public void setCompletedList(List<Completed> completedList) {
        this.completedList = completedList;
    }*/

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + getId() + '\"' +
                "\"title\":\"" + getTitle() + '\"' +
                ",\"text\":\"" + getText() + '\"' +
                ",\"options\":\"" + options.toString() +
                '}';
    }
}
