package app.user.controller;

import app.user.dto.SalesStaffDTO;
import app.user.exceptions.SalesStaffException;
import app.user.service.SalesStaffService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SalesController {

    private static final Logger logger = LoggerFactory.getLogger(SalesController.class);
    SalesStaffService salesStaffService;

    @Autowired
    public SalesController(SalesStaffService SalesStaffService) {
        this.salesStaffService = SalesStaffService;

//        demoSalesStaffService();
    }

    private void demoSalesStaffService() {

        logger.info("Demo Sales Staff");

        logger.info("demo - Find by salesId - VALID");
        demoSalesStaffFetchBySalesId(2);        //  Valid Entry
        logger.info("demo - Find by salesId - INVALID");
        demoSalesStaffFetchBySalesId(999);      //  Invalid Entry

        logger.info("demo - Insert new entry in Sales Staff");
        demoAddSalesStaff();             //  Insert New Sales Entry

        logger.info("demo - Delete from Sales Staff for salesId = 241");
        demoDeleteFromSalesStaff(241);    //  Delete Entry with salesId 241
        logger.info("demo - Delete from Sales Staff for salesId = 242");
        demoDeleteFromSalesStaff(242);    //  Delete Entry with salesId 242

    }

    private void demoDeleteFromSalesStaff(int salesId) {

        try {
            salesStaffService.deleteSalesById(salesId);
            logger.info("Production Staff for id: " + salesId + " deleted successfully.");

        } catch (SalesStaffException e) {
            logger.error("Failed to delete Sales due to Exception: " + e.getMessage());
//            e.printStackTrace();
        }

    }

    private void demoAddSalesStaff() {

        SalesStaffDTO salesStaffDTO;
        try {

            logger.info("Inserting New Entry");
            salesStaffDTO = salesStaffService.fetchSalesStaffById(10);
            logger.info(salesStaffService.addSalesStaff(salesStaffDTO) + " Inserted!");
            logger.info("Insertion Complete.");

        } catch (SalesStaffException e) {
            logger.error("Sales Staff Failed Insertion due to - Exception: " + e.getMessage());
//            e.printStackTrace();
        }

    }

    private void demoSalesStaffFetchBySalesId(int salesId) {

        try {

            SalesStaffDTO salesStaffDTO = salesStaffService.fetchSalesStaffById(salesId);
            logger.info("Existing Id: " + salesStaffDTO.getSalesId());

        } catch (SalesStaffException e) {
            logger.info("Sales Staff Failed with Exception - " + e.getMessage());
//            e.printStackTrace();
        }

    }


}