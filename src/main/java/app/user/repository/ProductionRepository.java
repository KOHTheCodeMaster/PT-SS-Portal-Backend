package app.user.repository;

import app.user.entity.Production;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ProductionRepository extends CrudRepository<Production, Integer> {

    @Query("SELECT DISTINCT P.supervisorName FROM Production P")
    ArrayList<String> findDistinctSupervisorName();

}
