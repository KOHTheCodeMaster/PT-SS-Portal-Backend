package app.user.controller;

import app.user.dto.ProductionDTO;
import app.user.exceptions.ProductionException;
import app.user.service.ProductionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin
@RestController
public class ProductionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductionController.class);
    private final ProductionService productionService;

    @Autowired
    public ProductionController(ProductionService productionService) {
        this.productionService = productionService;

//        demoProductionService();
    }

    /**
     * Retrieve ArrayList of ProductionDTO for all the production records from Production Table
     *
     * @return productionDTO ArrayList of all the production records from Production Table
     */
    @GetMapping(value = "/production/")
    public ResponseEntity<ArrayList<ProductionDTO>> getAllProductions() {

        LOGGER.info("Requesting All Productions.");
        return new ResponseEntity<>(productionService.getAllProductions(), HttpStatus.OK);

    }

    /**
     * Retrieve ArrayList of Names of All Distinct Supervisors for all the production records from Production Table
     *
     * @return supervisorNameList ArrayList of all the production records from Production Table
     */
    @GetMapping(value = "/production/list/supervisor")
    public ResponseEntity<ArrayList<String>> getSupervisorNameList() {

        LOGGER.info("Requesting Supervisor Name List.");
        return new ResponseEntity<>(productionService.getSupervisorNameList(), HttpStatus.OK);

    }

    /**
     * Retrieve ArrayList of All Distinct sizes for all the production records from Production Table
     *
     * @return sizeList of all the production records from Production Table
     */
    @GetMapping(value = "/production/list/size")
    public ResponseEntity<ArrayList<String>> getSizeList() {

        LOGGER.info("Requesting Size List.");
        return new ResponseEntity<>(productionService.getSizeList(), HttpStatus.OK);

    }

    /**
     * Retrieve ProductionDTO by providing productionId in the url
     *
     * @param productionId id corresponding to the production record of the Production Table
     * @return ProductionDTO for the given productionId
     * @throws ProductionException If productionId is null OR < 1 OR is not found in DB
     */
    @GetMapping(value = "/production/{productionId}")
    public ResponseEntity<ProductionDTO> getProductionById(@PathVariable Integer productionId) throws ProductionException {

        LOGGER.info("Requesting production for id: {}", productionId);
        return new ResponseEntity<>(productionService.getProductionById(productionId), HttpStatus.OK);

    }

    /**
     * Add new production record in DB by providing productionDTO as Json format in POST request body.
     * productionId is ignored since it is Auto-incremented by the DB.
     *
     * @param productionDTO productionDTO in the body of POST request as Json format
     * @return productionId of the newly added production record in Production Table
     */
    @PostMapping(value = "/production")
    public ResponseEntity<String> addProduction(@RequestBody ProductionDTO productionDTO) {

        int productionId = productionService.addProduction(productionDTO);

        String msg = "Production added successfully with id: " + productionId;
        LOGGER.info(msg);

        return new ResponseEntity<>(msg, HttpStatus.CREATED);
    }

    /**
     * Delete the record from Production Table for the given productionId
     *
     * @param productionId id corresponding to the production record of the Production Table
     * @return status msg. along with productionId on successful deletion otherwise Exception msg. on failure.
     * @throws ProductionException If productionId is null OR < 1 OR is not found in DB
     */
    @DeleteMapping(value = "/production/{productionId}")
    public ResponseEntity<String> deleteProduction(@PathVariable int productionId) throws ProductionException {

        productionService.deleteProductionById(productionId);

        String msg = "Production deleted successfully with production id : " + productionId;
        LOGGER.info(msg);

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }


    /**
     * Demo ProductionService methods
     */
    private void demoProductionService() {

        LOGGER.info("Demo Production ");

        LOGGER.info("Demo - Find by productionId - VALID");
        demoProductionFetchByProductionId(10);        //  Valid Entry
        LOGGER.info("Demo - Find by productionId - INVALID");
        demoProductionFetchByProductionId(999);      //  Invalid Entry

        LOGGER.info("Demo - Insert new entry in Production ");
        demoAddProduction();             //  Insert New Production  Entry

        LOGGER.info("demo - Delete from Production For productionId = 191");
        demoDeleteFromProduction(191);    //  Delete Entry with productionId 191
        LOGGER.info("demo - Delete from Production For productionId = 192");
        demoDeleteFromProduction(192);    //  Delete Entry with productionId 192

    }

    private void demoDeleteFromProduction(int productionId) {

        try {
            productionService.deleteProductionById(productionId);
            LOGGER.info("Production For id: " + productionId + " deleted successfully.");
        } catch (ProductionException e) {
            LOGGER.error("Failed to delete Production due to Exception: " + e.getMessage());
//            e.printStackTrace();
        }

    }

    private void demoAddProduction() {

        ProductionDTO productionDTO;
        try {

            LOGGER.info("Inserting New Entry");
            productionDTO = productionService.getProductionById(10);
            LOGGER.info(productionService.addProduction(productionDTO) + " Inserted!");
            LOGGER.info("Insertion Complete.");

        } catch (ProductionException e) {
            LOGGER.error("Production Failed Insertion due to - Exception: " + e.getMessage());
//            e.printStackTrace();
        }

    }

    private void demoProductionFetchByProductionId(int productionId) {

        try {

            ProductionDTO productionDTO = productionService.getProductionById(productionId);
            LOGGER.info("Existing Id: " + productionDTO.getProductionId());

        } catch (ProductionException e) {
            LOGGER.info("Production Failed with Exception - " + e.getMessage());
//            e.printStackTrace();
        }

    }


}
