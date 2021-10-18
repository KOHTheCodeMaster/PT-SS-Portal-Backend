package app.service;

import app.dto.SalesDTO;
import app.entity.Sales;
import app.enums.Status;
import app.exceptions.InvalidYearMonthException;
import app.exceptions.SalesException;
import app.exceptions.TargetException;
import app.pojo.SalesPOJO;
import app.pojo.YearMonthPojo;
import app.repository.SalesRepository;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service(value = "salesService")
@Transactional
public class SalesService {

    private final SalesRepository salesRepository;
    Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public SalesService(SalesRepository SalesRepository) {
        this.salesRepository = SalesRepository;
    }

    /**
     * Insert new sales record in Sales table for the given salesDTO
     *
     * @param salesDTO salesDTO which needs to be inserted into Sales Table
     * @return salesId of the newly added sales record on successful insertion, -1 on Failure.
     */
    public int addSales(SalesDTO salesDTO) {

        // Sales_Id, Buyer Name, Buyer Phone number, Buyer Address, Item Types, Colour, Item Size, Date, Amount of Item,
        // Payment, Sales Name
        Sales sales = new Sales();

//        sales.setUserId(salesDTO.getUserId());  //  Auto Generated
        sales.setBuyerName(salesDTO.getBuyerName());
        sales.setBuyerPhoneNumber(salesDTO.getBuyerPhoneNumber());
        sales.setBuyerAddress(salesDTO.getBuyerAddress());
        sales.setItemSize(salesDTO.getItemType());
        sales.setColour(salesDTO.getColour());
        sales.setItemType(salesDTO.getItemType());
        sales.setSalesDate(salesDTO.getSalesDate());
        sales.setAmountOfItem(salesDTO.getAmountOfItem());
        sales.setPayment(salesDTO.getPayment());
        sales.setSalesName(salesDTO.getSalesName());

        try {
            //  Save the entity & return the auto generated primary key of the newly saved record
            return salesRepository.save(sales).getSalesId();
        } catch (Exception e) {
            LOGGER.error("Failed to Save Entry due to Exception: " + e.getMessage());
        }
        return -1;
    }

    /**
     * Retrieve all the sales records from the Sales Table and return it as an ArrayList<SalesDTO>
     *
     * @return SalesDTO ArrayList of all the sales records from Sales Table
     */
    public ArrayList<SalesDTO> getAllSales() {

        ArrayList<SalesDTO> salesDTOList = new ArrayList<>();

        //  Retrieve all the saless records from the Sales Table as Iterable<Sales>
        Iterable<Sales> iterable = salesRepository.findAll();

        //  Iterate each sales entity, convert into its DTO & Add it to the salesDTOList
        iterable.forEach(sales -> salesDTOList.add(sales.convertToDTO()));

        return salesDTOList;

    }

    /**
     * Validate salesId, retrieve sales from DB for the given salesId & return its salesDTO.
     *
     * @param salesId id corresponding to the sales record of the Sales table in DB
     * @return salesDTO for the given salesId
     * @throws SalesException If salesId is null OR < 1 OR is not found in DB
     */
    public SalesDTO getSalesById(final int salesId) throws SalesException {

        validateSalesId(salesId);

        Sales sales = salesRepository.findById(salesId).orElseThrow(
                () -> new SalesException("Sales.SALES_NOT_FOUND with id: " + salesId)
        );
        return sales.convertToDTO();
    }

    /**
     * Delete sales record from DB for the given salesId
     *
     * @param salesId id corresponding to the sales which needs to be deleted
     * @throws SalesException If salesId is null OR < 1 OR is not found in DB
     */
    public void deleteSalesById(Integer salesId) throws SalesException {

        validateSalesId(salesId);

        salesRepository.findById(salesId).orElseThrow(
                () -> new SalesException("Sales.SALES_NOT_FOUND with id: " + salesId)
        );

        //  Delete sales from DB using sales entity
//        salesRepository.delete(sales);

        //  Delete sales from DB using salesId
        salesRepository.deleteById(salesId);

    }

    /**
     * salesId is INVALID for null OR < 1, Otherwise Valid
     *
     * @param salesId id which needs to be validated
     * @throws SalesException If salesId is null OR < 1
     */
    private void validateSalesId(Integer salesId) throws SalesException {

        //  Throw SalesException for invalid salesId
        if (salesId == null || salesId < 1)
            throw new SalesException("Sales.INVALID_SALES_ID : " + salesId);


    }

    /**
     * Validate & parse strYearAndMonth into YearMonthPojo,
     * Retrieve list of daily sales from DB for the given year & month.
     *
     * @param strYearAndMonth month & year for which the daily sales is required. <br>
     *                        format: YYYY-MM | E.g.: 2021-01
     * @return ArrayList List of Daily Sales for the given strYearAndMonth
     * @throws InvalidYearMonthException If strYearAndMonth is null OR (Month is < 1 OR > 12)
     */
    public ArrayList<SalesPOJO> getDailySalesListForAll(final String strYearAndMonth) throws InvalidYearMonthException {

        //  Validate & Parse strYearAndMonth to YearAndMonthPojo
        YearMonthPojo yearMonthPojo = YearMonthPojo.parseStringToYearMonthPojo(strYearAndMonth);
        ArrayList<SalesPOJO> dailySalesList = new ArrayList<>();

        //  Iterate for each day of given month
        int maxDayOfMonth = yearMonthPojo.getEndDate().getDayOfMonth();
        for (int dayCount = 1; dayCount <= maxDayOfMonth; dayCount++) {
            //  Add new SalesPOJO with salesAmount & date with dayCount
            //  Note: salesAmount = total monthly sales

            LocalDate tempStartDate = LocalDate.of(yearMonthPojo.getYear(), yearMonthPojo.getMonth(), dayCount);

            //  Find sales by year and month for current dayCount
            ArrayList<SalesPOJO> currentDaySalesList = salesRepository.findDailySalesListBetween(
                    tempStartDate, tempStartDate);

            //  When sales not found, add empty salesPojo with current dayCount date & continue
            if (currentDaySalesList.isEmpty()) {
                dailySalesList.add(new SalesPOJO(0L,
                        LocalDate.of(yearMonthPojo.getYear(), yearMonthPojo.getMonth(), dayCount)
                ));
                continue;
            }

            //  Compute total sales for current day
            long currentDaySalesAmount = currentDaySalesList.stream()
                    .mapToLong(SalesPOJO::getSalesAmount)
                    .sum();

            /*
                Add Sales Pojo to the dailySalesList
                salesAmount -> total sales amount for each day
                salesData -> date for year, month & each day of the month
            */
            dailySalesList.add(new SalesPOJO(currentDaySalesAmount,
                    LocalDate.of(yearMonthPojo.getYear(), yearMonthPojo.getMonth(), dayCount)
            ));

        }


        return dailySalesList;

    }

    /**
     * Validate & parse strYearAndMonth into YearMonthPojo,
     * Retrieve JSON containing list of daily sales from DB for each itemType for given year & month.
     *
     * @param strYearAndMonth month & year for which the daily sales is required. <br>
     *                        format: YYYY-MM | E.g.: 2021-01
     * @return JSON of each itemType with their List of Daily Sales for the given strYearAndMonth
     * @throws InvalidYearMonthException If strYearAndMonth is null OR (Month is < 1 OR > 12)
     */
    public String getDailySalesListForEachItemType(
            final String strYearAndMonth) throws InvalidYearMonthException {

        //  Map representing resultant JSON string, Key: itemType  |  value: list of Daily Prod Pojo
        Map<String, ArrayList<SalesPOJO>> map = new HashMap<>();

        //  Initialize array with item types of corrugation table
        String[] arrItemTypes = {"Seng Kaki", "Seng Lebar", "Galvalum", "Spandeck", "Coil"};

        //  Validate & Parse strYearAndMonth to YearAndMonthPojo
        YearMonthPojo yearMonthPojo = YearMonthPojo.parseStringToYearMonthPojo(strYearAndMonth);

        //  Iterate each itemType in arrItemTypes & find list of daily prod pojo
        for (String itemType : arrItemTypes) {
            //  Fetch list of daily sales pojo for itemType from Corrugation Table in DB
            ArrayList<SalesPOJO> listDailyProd = this.getMonthlySalesListByItemType(
                    itemType, yearMonthPojo);

            //  Add the retrieved list into map with corresponding itemType as the key
            map.put(itemType, listDailyProd);
        }

//        LOGGER.info(map.toString());

        return new Gson().toJson(map);

    }


    /**
     * Retrieve List of monthly sales for each month of the given year.
     * Monthly sales consists of following:
     * 1. Sales Amount = total monthly sales
     * 2. Sales Date = date for year, month & last day of the month
     * 3. epochMilliSecond
     *
     * @param strYear year for which the sales data is required
     * @return ArrayList List of SalesPojo containing monthly sales for the given strYear
     * @throws TargetException If strYear is null OR strYear < 2000
     */
    public ArrayList<SalesPOJO> getMonthlySalesListByYear(String strYear) throws TargetException {

        ArrayList<SalesPOJO> monthlySalesList = new ArrayList<>();

        //  Validate & Parse strYear to YearMonthPojo
        ArrayList<YearMonthPojo> yearMonthPojoList = YearMonthPojo.fromYear(strYear);

        //  Find and add monthly sales for each month of given year
        for (YearMonthPojo yearMonthPojo : yearMonthPojoList) {

            //  Find sales by year and month
            ArrayList<SalesPOJO> currentSalesList = salesRepository.findDailySalesListBetween(
                    yearMonthPojo.getStartDate(), yearMonthPojo.getEndDate());

            //  When sales not found, add empty salesPojo with endDate & continue
            if (currentSalesList.isEmpty()) {
                monthlySalesList.add(new SalesPOJO(0L, yearMonthPojo.getEndDate()));
                continue;
            }

            //  Compute total sales for current month
            long currentMonthSalesAmount = currentSalesList.stream()
                    .mapToLong(SalesPOJO::getSalesAmount)
                    .sum();

            /*
                Add Sales Pojo to the monthlySalesList
                salesAmount -> total sales amount for the month
                salesData -> date for year, month & last day of the month
            */
            monthlySalesList.add(new SalesPOJO(currentMonthSalesAmount, yearMonthPojo.getEndDate()));

        }


        LOGGER.info(monthlySalesList.size() + "");
        monthlySalesList.forEach(salesPOJO -> LOGGER.info(salesPOJO.toString()));

        return monthlySalesList;
    }

    /**
     * Retrieve list of daily sales from DB for the given itemType, year & month.
     *
     * @param itemType      item type (of corrugation) for which the daily sales is required. <br>
     * @param yearMonthPojo month & year for which the daily sales is required. <br>
     *                      yearMonthPojo contains required startDate & endDate fields. <br>
     *                      format: YYYY-MM | E.g.: 2021-01
     * @return ArrayList List of Daily Sales for the given itemType & strYearAndMonth
     */
    public ArrayList<SalesPOJO> getMonthlySalesListByItemType(
            String itemType, final YearMonthPojo yearMonthPojo) {

        return salesRepository.findByProductTypeDailySalesListBetween(
                itemType, yearMonthPojo.getStartDate(), yearMonthPojo.getEndDate());

    }

    /**
     * Retrieve latest list of SalesDTO from DB for the given size.
     * List would be in Descending Order by the salesId.
     *
     * @param size length of the list to be retrieved from DB. <br>
     * @return ArrayList of SalesDTO of given size for Status Table
     * @throws SalesException If size <= 0  OR  size > 100
     */
    public ArrayList<SalesDTO> getSalesDTOList(int size) throws SalesException {

        if (size <= 0 || size > 100) throw new SalesException("Invalid List Size parameter - " + size);

        ArrayList<SalesDTO> resultList = new ArrayList<>();
        ArrayList<Sales> salesList = salesRepository.findFirst10ByStatusNotOrderBySalesIdDesc(Status.DONE);
        salesList.stream().limit(size).forEach(sales -> resultList.add(sales.convertToDTO()));
        return resultList;

    }

    /**
     * Update sales status in DB for the list of salesId available in the POST request Body.
     * Body contains list of following:
     * 1. salesId   2. status   3. index (for removing current row at front-end)
     *
     * @param salesDTOList List of salesDTO in the body of POST request as Json format which contains
     *                     the list of salesId along with the status that needs to be updated.
     * @return true when all the sales records are updated successfully in DB, otherwise false.
     */
    public boolean updateSales(ArrayList<SalesDTO> salesDTOList) {

        AtomicInteger updateCount = new AtomicInteger();
        salesDTOList.forEach(salesDTO -> {
            int queryResult = salesRepository.updateSalesById(salesDTO.getSalesId(), salesDTO.getStatus());
            if (queryResult != 0) updateCount.incrementAndGet();
        });

        return updateCount.get() == salesDTOList.size();
    }
}
