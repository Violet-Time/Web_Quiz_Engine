package engine.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import engine.model.*;
import engine.repo.CompletedRepository;
import engine.repo.QuestionRepository;
import engine.services.CompletedService;
import engine.services.MongoUserDetailsService;
import engine.services.QuestionService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
@RestController
@RequestMapping("/api/quizzes")
public class QuizzesController {

    private final QuestionRepository questionRepository;
    private final MongoUserDetailsService mongoUserDetailsService;
    private final QuestionService questionService;
    private final CompletedRepository completedRepository;
    private final CompletedService completedService;

    public QuizzesController(MongoUserDetailsService mongoUserDetailsService,
                             QuestionRepository questionRepository,
                             QuestionService questionService,
                             CompletedRepository completedRepository,
                             CompletedService completedService) {
        this.questionRepository = questionRepository;
        this.mongoUserDetailsService = mongoUserDetailsService;
        this.questionService = questionService;
        this.completedRepository = completedRepository;
        this.completedService = completedService;
    }

    @GetMapping
    public ResponseEntity<Page<QuestionDB>> getQuizzes(QuestionsPage questionsPage) {
        System.out.println("/?page" + questionsPage.getPage());
        return new ResponseEntity<>(questionService.getQuestions(questionsPage), HttpStatus.OK);
        /*questionRepository.findAll(new P)
        return StreamSupport.stream(questionRepository.findAllById(Arrays.asList(mongoUserDetailsService.getUser().getId())).spliterator(), false)
                .map(QuestionDB::toQuestionJSON)
                .collect(Collectors.toList());*/
    }

    @GetMapping("/{id}")
    public QuestionJSON getQuiz(@PathVariable int id) {
        System.out.println("/id");
        if (id < 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "404 (Not found)"
            );
        }
        try {
            return questionRepository.findById(id).get().toQuestionJSON();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "404 (Not found)"
            );
        }
    }

    @PostMapping
    public Question addQuizzes(@Valid @RequestBody QuestionJSON questionJSON) {
        System.out.println("/add");
        //System.out.println(questionJSON);
        QuestionDB questionDB = questionJSON.toQuestionBD();
        questionDB.setUser(mongoUserDetailsService.getUser());
        return questionRepository.save(questionDB).toQuestionJSON();
        //return questionJSON.toQuestionBD();
    }

    @DeleteMapping("/{id}")
    public void deleteQuiz(@PathVariable int id) {
        System.out.println("Delete /id");
        Optional<QuestionDB> optionalQuestionDB = questionRepository.findById(id);
        if (optionalQuestionDB.isPresent()) {
            QuestionDB question = optionalQuestionDB.get();
            System.out.println(question.getId());
            if (question.getUser().getId() == mongoUserDetailsService.getUser().getId()) {
                //completedRepository.deleteByQuestion(question);
                questionRepository.deleteById(id);
                throw new ResponseStatusException(HttpStatus.NO_CONTENT);
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/solve")
    public String checkAnswer(@PathVariable int id, @RequestBody Map<String, Integer[]> answer) {
        System.out.println("solve");

        if (id < 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "404 (Not found)"
            );
        }
        //System.out.println(listQuestions.get(id - 1));
        //System.out.println(Arrays.toString(answer.get("answer")));

        Optional<QuestionDB> optional = questionRepository.findById(id);
        if (optional.isPresent()) {
            QuestionDB question = optional.get();
            if (question.toQuestionJSON().checkCorrectAnswer(Arrays.asList(answer.get("answer") != null ? answer.get("answer") : new Integer[0]))) {
                    //if (/*question.getUser().getId() != mongoUserDetailsService.getUser().getId() &&*/ completedRepository.findByUserAndQuestion(mongoUserDetailsService.getUser(), question) == null) {
                        completedRepository.save(new Completed(mongoUserDetailsService.getUser(), question));
                        System.out.println(mongoUserDetailsService.getUser().getId() + " - " + question.getId() + " - " + question.getUser().getId());
                    //}
                    //System.out.println(new ObjectMapper().writeValueAsString(completedRepository.save(new Completed(mongoUserDetailsService.getUser(), question))));
                return "{\"success\":true,\"feedback\":\"Congratulations, you're right!\"}";
            }
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "404 (Not found)"
            );
        }

        return "{\"success\":false,\"feedback\":\"Wrong answer! Please, try again.\"}";
    }

    @GetMapping("/completed")
    public ResponseEntity<Page<Completed>> getCompletedQuizzes(CompletedPage completedPage) {
        return new ResponseEntity<>(completedService.getCompleted(completedPage), HttpStatus.OK);
    }
}
