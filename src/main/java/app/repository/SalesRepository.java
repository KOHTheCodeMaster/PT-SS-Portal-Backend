package app.repository;

import app.entity.Sales;
import app.pojo.SalesPOJO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.ArrayList;

public interface SalesRepository extends CrudRepository<Sales, Integer> {

    //  Using class based projection for selecting specific columns
    @Query("SELECT NEW app.pojo.SalesPOJO(" +
            "SUM(S.amountOfItem), S.salesDate)  " +
            "FROM Sales S " +
            "GROUP by S.salesDate " +
            "HAVING S.salesDate BETWEEN ?1 AND ?2")
    ArrayList<SalesPOJO> findDailySalesListBetween(LocalDate strStartDate, LocalDate strEndDate);

    @Query("SELECT NEW app.pojo.SalesPOJO(" +
            "SUM(S.amountOfItem), S.salesDate)  " +
            "FROM Sales S " +
            "GROUP by S.salesDate " +
            "HAVING S.salesDate BETWEEN ?1 AND ?2")
    ArrayList<SalesPOJO> find1stClassSalesBetweenDate(LocalDate strStartDate, LocalDate strEndDate);

}
