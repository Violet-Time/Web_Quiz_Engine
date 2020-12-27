package engine.services;

import engine.model.QuestionDB;
import engine.model.QuestionJSON;
import engine.model.QuestionsPage;
import engine.repo.QuestionRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Page<QuestionDB> getQuestions(QuestionsPage questionsPage) {
        Sort sort = Sort.by(questionsPage.getSortDirection(), questionsPage.getSortBy());
        Pageable pageable = PageRequest.of(questionsPage.getPage(),
                                            questionsPage.getSize(),
                                            sort);
        return questionRepository.findAll(pageable);
    }
}
