package engine.repo;

import engine.model.QuestionDB;
import engine.model.QuestionJSON;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends PagingAndSortingRepository<QuestionDB, Integer> {

}
