package app.repository;

import app.entity.Corrugation;
import app.pojo.DailyProductionPOJO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.ArrayList;

public interface CorrugationRepository extends CrudRepository<Corrugation, Integer> {

    //  Using class based projection for selecting specific columns
    @Query("SELECT NEW app.pojo.DailyProductionPOJO(" +
            "SUM(C.amount), C.corrugationDate)  " +
            "FROM Corrugation C " +
            "WHERE C.itemType = ?1 AND " +
            "      C.corrugationDate BETWEEN ?2 AND ?3 " +
            "GROUP by C.corrugationDate ")
    ArrayList<DailyProductionPOJO> findByProductTypeDailyProductionListBetween(
            String itemType, LocalDate strStartDate, LocalDate strEndDate);
}
