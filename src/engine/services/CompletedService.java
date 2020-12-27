package engine.services;

import engine.model.Completed;
import engine.model.CompletedPage;
import engine.model.QuestionDB;
import engine.model.QuestionsPage;
import engine.repo.CompletedRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class CompletedService {
    private final CompletedRepository completedRepository;
    private final MongoUserDetailsService mongoUserDetailsService;

    public CompletedService(CompletedRepository completedRepository,
                            MongoUserDetailsService mongoUserDetailsService) {
        this.completedRepository = completedRepository;
        this.mongoUserDetailsService = mongoUserDetailsService;
    }

    public Page<Completed> getCompleted(CompletedPage completedPage) {
        Sort sort = Sort.by(completedPage.getSortDirection(), completedPage.getSortBy());
        Pageable pageable = PageRequest.of(completedPage.getPage(),
                completedPage.getSize(),
                sort);
        return completedRepository.findAllByUser(pageable, mongoUserDetailsService.getUser());
        //return completedRepository.findAll(pageable);
    }
}
