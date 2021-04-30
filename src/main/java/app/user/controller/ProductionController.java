package app.user.controller;

import app.user.dto.ProductionStaffDTO;
import app.user.exceptions.ProductionStaffException;
import app.user.service.ProductionStaffService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductionController {

    Logger logger;
    ProductionStaffService productionStaffService;

    @Autowired
    public ProductionController(ProductionStaffService productionStaffService) {
        this.productionStaffService = productionStaffService;
        this.logger = LoggerFactory.getLogger(this.getClass());

//        demoProductionStaffService();
    }

    private void demoProductionStaffService() {

        logger.info("Demo Production Staff");

        logger.info("Demo - Find by productionId - VALID");
        demoProductionStaffFetchByProductionId(10);        //  Valid Entry
        logger.info("Demo - Find by productionId - INVALID");
        demoProductionStaffFetchByProductionId(999);      //  Invalid Entry

        logger.info("Demo - Insert new entry in Production Staff");
        demoAddProductionStaff();             //  Insert New Production Staff Entry

        logger.info("demo - Delete from Production Staff for productionId = 191");
        demoDeleteFromProductionStaff(191);    //  Delete Entry with productionId 191
        logger.info("demo - Delete from Production Staff for productionId = 192");
        demoDeleteFromProductionStaff(192);    //  Delete Entry with productionId 192

    }

    private void demoDeleteFromProductionStaff(int productionId) {

        try {
            productionStaffService.deleteProductionStaffById(productionId);
            logger.info("Production Staff for id: " + productionId + " deleted successfully.");
        } catch (ProductionStaffException e) {
            logger.error("Failed to delete Production due to Exception: " + e.getMessage());
//            e.printStackTrace();
        }

    }

    private void demoAddProductionStaff() {

        ProductionStaffDTO productionStaffDTO;
        try {

            logger.info("Inserting New Entry");
            productionStaffDTO = productionStaffService.fetchProductionStaffById(10);
            logger.info(productionStaffService.addProductionStaff(productionStaffDTO) + " Inserted!");
            logger.info("Insertion Complete.");

        } catch (ProductionStaffException e) {
            logger.error("Production Staff Failed Insertion due to - Exception: " + e.getMessage());
//            e.printStackTrace();
        }

    }


    private void demoProductionStaffFetchByProductionId(int productionId) {

        try {

            ProductionStaffDTO productionStaffDTO = productionStaffService.fetchProductionStaffById(productionId);
            logger.info("Existing Id: " + productionStaffDTO.getProductionId());

        } catch (ProductionStaffException e) {
            logger.info("Production Staff Failed with Exception - " + e.getMessage());
//            e.printStackTrace();
        }

    }



}
