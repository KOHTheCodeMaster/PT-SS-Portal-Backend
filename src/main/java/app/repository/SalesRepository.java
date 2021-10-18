package app.repository;

import app.entity.Sales;
import app.enums.Status;
import app.pojo.SalesPOJO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;

@Transactional
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

    ArrayList<Sales> findFirst10ByStatusNotOrderBySalesIdDesc(Status status);

    @Transactional
    @Modifying
    @Query("UPDATE Sales S Set S.status = ?2 WHERE S.salesId = ?1")
    int updateSalesById(int salesId, Status status);

}
