package app.user.controller;

import app.user.dto.SalesDTO;
import app.user.exceptions.SalesException;
import app.user.service.SalesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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