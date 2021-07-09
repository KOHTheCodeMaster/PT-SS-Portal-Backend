package app.repository;

import app.entity.Target;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TargetRepository extends CrudRepository<Target, Integer> {

    Optional<Target> findByMonthAndYearAndType(Integer month, Integer year, Character type);

}
