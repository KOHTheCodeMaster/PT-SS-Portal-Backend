package app.user.repository;

import app.user.entity.SalesStaff;
import app.user.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface SalesStaffRepository extends CrudRepository<SalesStaff, Integer> {

}
