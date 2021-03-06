package app.controller;

import app.dto.SalesDTO;
import app.exceptions.InvalidYearMonthException;
import app.exceptions.SalesException;
import app.exceptions.TargetException;
import app.pojo.SalesPOJO;
import app.service.SalesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin
@RestController
public class SalesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SalesController.class);
    private final SalesService salesService;

    @Autowired
    public SalesController(SalesService SalesService) {
        this.salesService = SalesService;

//        demoSalesService();
    }

    /**
     * Retrieve ArrayList of SalesDTO for all the sales records from Sales Table
     *
     * @return salesDTO ArrayList of all the sales records from Sales Table
     */
    @GetMapping(value = "/sales/")
    public ResponseEntity<ArrayList<SalesDTO>> getAllSales() {

        LOGGER.info("Requesting All Sales.");
        return new ResponseEntity<>(salesService.getAllSales(), HttpStatus.OK);

    }

    /**
     * Retrieve SalesDTO by providing salesId in the url
     *
     * @param salesId id corresponding to the sales record of the Sales Table
     * @return SalesDTO for the given salesId
     * @throws SalesException If salesId is null OR < 1 OR is not found in DB
     */
    @GetMapping(value = "/sales/{salesId}")
    public ResponseEntity<SalesDTO> getSalesById(@PathVariable Integer salesId) throws SalesException {

        LOGGER.info("Requesting sales for id: {}", salesId);
        return new ResponseEntity<>(salesService.getSalesById(salesId), HttpStatus.OK);

    }

    /**
     * Add new sales record in DB by providing salesDTO as Json format in POST request body.
     * salesId is ignored since it is Auto-incremented by the DB.
     *
     * @param salesDTO salesDTO in the body of POST request as Json format
     * @return salesId of the newly added sales record in Sales Table
     */
    @PostMapping(value = "/sales")
    public ResponseEntity<String> addSales(@RequestBody SalesDTO salesDTO) {

        int salesId = salesService.addSales(salesDTO);

        String msg = "Sales added successfully with id: " + salesId;
        LOGGER.info(msg);

        return new ResponseEntity<>(msg, HttpStatus.CREATED);
    }

    /**
     * Delete the record from Sales Table for the given salesId
     *
     * @param salesId id corresponding to the sales record of the Sales Table
     * @return status msg. along with salesId on successful deletion otherwise Exception msg. on failure.
     * @throws SalesException If salesId is null OR < 1 OR is not found in DB
     */
    @DeleteMapping(value = "/sales/{salesId}")
    public ResponseEntity<String> deleteSales(@PathVariable int salesId) throws SalesException {

        salesService.deleteSalesById(salesId);

        String msg = "Sales deleted successfully with sales id : " + salesId;
        LOGGER.info(msg);

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    //  Daily Report
    //  --------------

    /**
     * Retrieve List of daily sales for all product types for the given year & month.
     * Daily sales consists of following:
     * 1. Sum of salesAmount on that sales date
     * 2. Sales Date
     * 3. epochMilliSecond
     *
     * @param strYearAndMonth for which month & year the daily sales is required
     * @return ArrayList List of Daily Sales for the given strYearAndMonth
     * @throws InvalidYearMonthException If strYearAndMonth is null OR (Month is < 1 OR > 12)
     */
    @GetMapping(value = "/sales/daily/all/{strYearAndMonth}")
    public ResponseEntity<ArrayList<SalesPOJO>> getDailySalesListForAll(
            @PathVariable String strYearAndMonth) throws InvalidYearMonthException {

        LOGGER.info("Requesting Daily Sales List for All - date: {}", strYearAndMonth);
        return new ResponseEntity<>(salesService.getDailySalesListForAll(strYearAndMonth), HttpStatus.OK);

    }

    /**
     * Retrieve JSON containing list of daily sales from DB for each itemType for given year & month. <br>
     *
     * @param strYearAndMonth month & year for which the daily sales is required. <br>
     *                        format: YYYY-MM | E.g.: 2021-01
     * @return JSON of each itemType with their List of Daily Sales for the given strYearAndMonth
     */
    @GetMapping(value = "/sales/daily/each-item-type/{strYearAndMonth}")
    public ResponseEntity<String> getdailySalesListForEachItemType(@PathVariable String strYearAndMonth)
            throws InvalidYearMonthException {

        LOGGER.info("Requesting daily Sales for each item type - Date: " + strYearAndMonth);
        return new ResponseEntity<>(salesService.getDailySalesListForEachItemType(strYearAndMonth),
                HttpStatus.OK);
        /*
            //  JSON result format:
            {
                "Galvalum": [
                    {
                        "dailySalesAmount": 11,
                        "salesDate": {
                            "year": 2021,
                            "month": 6,
                            "day": 10
                        },
                        "epochMilliSecond": 1623283200000
                    }
                ],
                "Seng Kaki": [],
                "Seng Lebar": [],
                "Spandeck": [],
                "Coil": []
            }
         */
    }


    //  Monthly Report
    //  --------------

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
    @GetMapping(value = "/sales/monthly/{strYear}")
    public ResponseEntity<ArrayList<SalesPOJO>> getMonthlySalesByYear(
            @PathVariable String strYear) throws SalesException, TargetException {

        LOGGER.info("Requesting Monthly Sales Report for : {}", strYear);
        return new ResponseEntity<>(salesService.getMonthlySalesListByYear(
                strYear), HttpStatus.OK);

    }

    //  Status Table
    //  --------------

    /**
     * Retrieve latest list of SalesDTO from DB for the given size.
     * List would be in Descending Order by the salesId.
     *
     * @param size length of the list to be retrieved
     * @return ArrayList of SalesDTO of given size for Status Table
     * @throws SalesException If size <= 0  OR  size > 100
     */
    @GetMapping(value = "/sales/status-table/{size}")
    public ResponseEntity<ArrayList<SalesDTO>> getSalesDTOList(
            @PathVariable int size) throws SalesException {

        LOGGER.info("Requesting {} Sales Transactions.", size);
        return new ResponseEntity<>(salesService.getSalesDTOList(
                size), HttpStatus.OK);

    }

    /**
     * Update sales status in DB for the list of salesId available in the POST request Body.
     * Body contains list of following:
     * 1. salesId   2. status   3. index (for removing current row at front-end)
     *
     * @param salesDTOList List of salesDTO in the body of POST request as Json format which contains
     *                     the list of salesId along with the status that needs to be updated.
     * @return true when sales records are updated successfully, otherwise false.
     */
    @PostMapping(value = "/sales/status-table/save")
    public ResponseEntity<Boolean> updateSales(@RequestBody ArrayList<SalesDTO> salesDTOList) {

        boolean updateSuccessful = salesService.updateSales(salesDTOList);
        HttpStatus httpStatus = updateSuccessful ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;

        LOGGER.info(httpStatus.getReasonPhrase());
        return new ResponseEntity<>(updateSuccessful, httpStatus);
    }

    /**
     * Demo SalesService methods
     */
    private void demoSalesService() {

        LOGGER.info("Demo Sales ");

        LOGGER.info("demo - Find by salesId - VALID");
        demoSalesFetchBySalesId(2);        //  Valid Entry
        LOGGER.info("demo - Find by salesId - INVALID");
        demoSalesFetchBySalesId(999);      //  Invalid Entry

        LOGGER.info("demo - Insert new entry in Sales ");
        demoAddSales();             //  Insert New Sales Entry

        LOGGER.info("demo - Delete from Sales for salesId = 241");
        demoDeleteFromSales(241);    //  Delete Entry with salesId 241
        LOGGER.info("demo - Delete from Sales for salesId = 242");
        demoDeleteFromSales(242);    //  Delete Entry with salesId 242

    }

    private void demoDeleteFromSales(int salesId) {

        try {
            salesService.deleteSalesById(salesId);
            LOGGER.info("Sales for id: " + salesId + " deleted successfully.");

        } catch (SalesException e) {
            LOGGER.error("Failed to delete Sales due to Exception: " + e.getMessage());
//            e.printStackTrace();
        }

    }

    private void demoAddSales() {

        SalesDTO salesDTO;
        try {

            LOGGER.info("Inserting New Entry");
            salesDTO = salesService.getSalesById(10);
            LOGGER.info(salesService.addSales(salesDTO) + " Inserted!");
            LOGGER.info("Insertion Complete.");

        } catch (SalesException e) {
            LOGGER.error("Sales Failed Insertion due to - Exception: " + e.getMessage());
//            e.printStackTrace();
        }

    }

    private void demoSalesFetchBySalesId(int salesId) {

        try {

            SalesDTO salesDTO = salesService.getSalesById(salesId);
            LOGGER.info("Existing Id: " + salesDTO.getSalesId());

        } catch (SalesException e) {
            LOGGER.info("Sales Failed with Exception - " + e.getMessage());
//            e.printStackTrace();
        }

    }


}