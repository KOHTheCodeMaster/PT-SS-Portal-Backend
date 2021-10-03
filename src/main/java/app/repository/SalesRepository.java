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

    //  Using class based projection for selecting specific columns
    @Query("SELECT NEW app.pojo.SalesPOJO(" +
            "SUM(S.amountOfItem), S.salesDate)  " +
            "FROM Sales S " +
            "WHERE S.itemType = ?1 AND " +
            "      S.salesDate BETWEEN ?2 AND ?3 " +
            "GROUP by S.salesDate ")
    ArrayList<SalesPOJO> findByProductTypeDailySalesListBetween(
            String itemType, LocalDate strStartDate, LocalDate strEndDate);
}
