package engine.repo;

import engine.model.Completed;
import engine.model.QuestionDB;
import engine.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletedRepository extends PagingAndSortingRepository<Completed, Integer> {
    Page<Completed> findAllByUser(Pageable pageable, User user);
    Completed findByUserAndQuestion(User user, QuestionDB question);
    void deleteByQuestion(QuestionDB question);
}
