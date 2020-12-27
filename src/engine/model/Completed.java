package engine.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@JsonSerialize(using = CompletedSerializer.class)
public class Completed {
    @Id @GeneratedValue
    private int id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id")//(fetch = FetchType.EAGER)// @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    private LocalDateTime localDateTime;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "question_id")//(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private QuestionDB question;

    public Completed(){
    }

    public Completed(User user, QuestionDB question) {
        localDateTime = LocalDateTime.now();
        this.user = user;
        this.question = question;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public QuestionDB getQuestion() {
        return question;
    }

    public void setQuestion(QuestionDB question) {
        this.question = question;
    }

}
