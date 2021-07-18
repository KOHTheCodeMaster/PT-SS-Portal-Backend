package app.repository;

import app.entity.Production;
import app.pojo.ProductionPOJO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.ArrayList;

public interface ProductionRepository extends CrudRepository<Production, Integer> {

    @Query("SELECT DISTINCT P.supervisorName FROM Production P")
    ArrayList<String> findDistinctSupervisorNameList();

    @Query("SELECT DISTINCT P.size FROM Production P")
    ArrayList<String> findDistinctSizeList();

    //  Using class based projection for selecting specific columns
    @Query("SELECT NEW app.pojo.ProductionPOJO(" +
            "SUM(P.productionAmount1stClass), P.productionDate)  " +
            "FROM Production P " +
            "GROUP by P.productionDate " +
            "HAVING P.productionDate BETWEEN ?1 AND ?2")
    ArrayList<ProductionPOJO> findDailyProductionListBetween(LocalDate strStartDate, LocalDate strEndDate);

    //  Using class based projection for selecting specific columns
    @Query("SELECT NEW app.pojo.ProductionPOJO(" +
            "SUM(P.productionAmount2ndClass), P.productionDate)  " +
            "FROM Production P " +
            "GROUP by P.productionDate " +
            "HAVING P.productionDate BETWEEN ?1 AND ?2")
    ArrayList<ProductionPOJO> find2ndClassDailyProductionListBetween(LocalDate strStartDate, LocalDate strEndDate);

    @Query("SELECT NEW app.pojo.ProductionPOJO(" +
            "SUM(P.productionAmount1stClass), P.productionDate)  " +
            "FROM Production P " +
            "GROUP by P.productionDate " +
            "HAVING P.productionDate BETWEEN ?1 AND ?2")
    ArrayList<ProductionPOJO> find1stClassProductionBetweenDate(LocalDate strStartDate, LocalDate strEndDate);

}
