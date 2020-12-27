package engine;

import engine.config.SecurityConfiguration;
import engine.model.Question;
import engine.model.QuestionDB;
import engine.model.QuestionJSON;
import engine.model.User;
import engine.repo.QuestionRepository;
import engine.repo.UserRepository;
import engine.services.MongoUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@SpringBootApplication
@RestController
public class WebQuizEngine {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MongoUserDetailsService mongoUserDetailsService;

    public WebQuizEngine(QuestionRepository questionRepository,
                         UserRepository userRepository,
                         PasswordEncoder passwordEncoder,
                         MongoUserDetailsService mongoUserDetailsService) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mongoUserDetailsService = mongoUserDetailsService;
    }

    public static void main(String[] args) {
        SpringApplication.run(WebQuizEngine.class, args);
    }



    @PostMapping("/api/register")
    public void register(@Valid @RequestBody User user) {
        System.out.println(user.getEmail());
        if (userRepository.findByEmail(user.getEmail()) != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }






}
