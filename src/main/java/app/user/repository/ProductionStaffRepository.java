package app.user.repository;

import app.user.entity.ProductionStaff;
import app.user.entity.SalesStaff;
import org.springframework.data.repository.CrudRepository;

public interface ProductionStaffRepository extends CrudRepository<ProductionStaff, Integer> {

}
