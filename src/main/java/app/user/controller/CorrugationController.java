package app.user.controller;

import app.user.dto.CorrugationDTO;
import app.user.exceptions.CorrugationException;
import app.user.service.CorrugationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class CorrugationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CorrugationController.class);
    private final CorrugationService corrugationService;

    @Autowired
    public CorrugationController(CorrugationService CorrugationService) {
        this.corrugationService = CorrugationService;

//        demoCorrugationService();
    }

    /**
     * Retrieve ArrayList of CorrugationDTO for all the corrugation records from Corrugation Table
     *
     * @return corrugationDTO ArrayList of all the corrugation records from Corrugation Table
     */
    @GetMapping(value = "/corrugation/")
    public ResponseEntity<ArrayList<CorrugationDTO>> getAllCorrugations() {

        LOGGER.info("Requesting All Corrugations.");
        return new ResponseEntity<>(corrugationService.getAllCorrugationsList(), HttpStatus.OK);

    }

    /**
     * Retrieve CorrugationDTO by providing corrugationId in the url
     *
     * @param corrugationId id corresponding to the corrugation record of the Corrugation Table
     * @return CorrugationDTO for the given corrugationId
     * @throws CorrugationException If corrugationId is null OR < 1 OR is not found in DB
     */
    @GetMapping(value = "/corrugation/{corrugationId}")
    public ResponseEntity<CorrugationDTO> getCorrugationById(@PathVariable Integer corrugationId) throws CorrugationException {

        LOGGER.info("Requesting Corrugation for id: {}", corrugationId);
        return new ResponseEntity<>(corrugationService.fetchCorrugationById(corrugationId), HttpStatus.OK);

    }

    /**
     * Add new corrugation record in DB by providing corrugationDTO as Json format in POST request body.
     * corrugationId is ignored since it is Auto-incremented by the DB.
     *
     * @param corrugationDTO corrugationDTO in the body of POST request as Json format
     * @return corrugationId of the newly added corrugation record in Corrugation Table
     */
    @PostMapping(value = "/corrugation")
    public ResponseEntity<String> addCorrugation(@RequestBody CorrugationDTO corrugationDTO) {

        int corrugationId = corrugationService.addCorrugation(corrugationDTO);

        String msg = "Corrugation added successfully with id: " + corrugationId;
        LOGGER.info(msg);

        return new ResponseEntity<>(msg, HttpStatus.CREATED);
    }

    /**
     * Delete the record from Corrugation Table for the given corrugationId
     *
     * @param corrugationId id corresponding to the corrugation record of the Corrugation Table
     * @return status msg. along with corrugationId on successful deletion otherwise Exception msg. on failure.
     * @throws CorrugationException If corrugationId is null OR < 1 OR is not found in DB
     */
    @DeleteMapping(value = "/corrugation/{corrugationId}")
    public ResponseEntity<String> deleteCorrugation(@PathVariable int corrugationId) throws CorrugationException {

        corrugationService.deleteCorrugationById(corrugationId);

        String msg = "Corrugation deleted successfully with corrugation id : " + corrugationId;
        LOGGER.info(msg);

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    /**
     * Demo corrugationService methods
     */
    private void demoCorrugationService() {

        LOGGER.info("Demo Corrugation");

        LOGGER.info("demo - Find by corrugationId - VALID");
        demoCorrugationFetchBycorrugationId(2);         //  Valid Entry
        LOGGER.info("demo - Find by corrugationId - INVALID");
        demoCorrugationFetchBycorrugationId(999);       //  Invalid Entry

        LOGGER.info("demo - Insert new record in Corrugation");
        demoAddCorrugation();                           //  Insert New Corrugation Record

        LOGGER.info("demo - Delete from Corrugation for corrugationId = 101");
        demoDeleteFromCorrugation(101);      //  Delete Entry with corrugationId 101
        LOGGER.info("demo - Delete from Corrugation for corrugationId = 242");
        demoDeleteFromCorrugation(102);      //  Delete Entry with corrugationId 102

    }

    private void demoDeleteFromCorrugation(int corrugationId) {

        try {

            corrugationService.deleteCorrugationById(corrugationId);
            LOGGER.info("Corrugation for id: " + corrugationId + " deleted successfully.");

        } catch (CorrugationException e) {
            LOGGER.error("Failed to delete Corrugation due to Exception: " + e.getMessage());
//            e.printStackTrace();
        }

    }

    private void demoAddCorrugation() {

        CorrugationDTO CorrugationDTO;
        try {

            CorrugationDTO = corrugationService.fetchCorrugationById(10);
            LOGGER.info(corrugationService.addCorrugation(CorrugationDTO) + " Inserted successfully.");

        } catch (CorrugationException e) {
            LOGGER.error("Corrugation Failed Insertion due to - Exception: " + e.getMessage());
//            e.printStackTrace();
        }

    }

    private void demoCorrugationFetchBycorrugationId(int corrugationId) {

        try {

            CorrugationDTO CorrugationDTO = corrugationService.fetchCorrugationById(corrugationId);
            LOGGER.info("Existing Id: " + CorrugationDTO.getCorrugationId());

        } catch (CorrugationException e) {
            LOGGER.info("Corrugation Failed with Exception - " + e.getMessage());
//            e.printStackTrace();
        }

    }

}
