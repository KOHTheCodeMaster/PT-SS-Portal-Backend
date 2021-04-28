package app.user.controller;

import app.user.dto.CorrugationDTO;
import app.user.exceptions.CorrugationException;
import app.user.service.CorrugationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CorrugationController {

    private static final Logger logger = LoggerFactory.getLogger(CorrugationController.class);
    CorrugationService corrugationService;

    @Autowired
    public CorrugationController(CorrugationService CorrugationService) {
        this.corrugationService = CorrugationService;

//        demoCorrugationService();
    }

    private void demoCorrugationService() {

        logger.info("Demo Corrugation");

        logger.info("demo - Find by corrugationId - VALID");
        demoCorrugationFetchBycorrugationId(2);        //  Valid Entry
        logger.info("demo - Find by corrugationId - INVALID");
        demoCorrugationFetchBycorrugationId(999);      //  Invalid Entry

        logger.info("demo - Insert new entry in Corrugation");
        demoInsertIntoCorrugation();             //  Insert New Sales Entry

        logger.info("demo - Delete from Corrugation for corrugationId = 101");
        demoDeleteFromCorrugation(101);    //  Delete Entry with corrugationId 101
        logger.info("demo - Delete from Corrugation for corrugationId = 242");
        demoDeleteFromCorrugation(102);    //  Delete Entry with corrugationId 102

    }

    private void demoDeleteFromCorrugation(int corrugationId) {

        try {
            corrugationService.deleteCorrugationById(corrugationId);
            logger.info("Corrugation for id: " + corrugationId + " deleted successfully.");

        } catch (CorrugationException e) {
            logger.error("Failed to delete Sales due to Exception: " + e.getMessage());
//            e.printStackTrace();
        }

    }

    private void demoInsertIntoCorrugation() {

        CorrugationDTO CorrugationDTO;
        try {

            CorrugationDTO = corrugationService.fetchCorrugationById(10);
            logger.info(corrugationService.insertIntoCorrugation(CorrugationDTO) + " Inserted successfully.");

        } catch (CorrugationException e) {
            logger.error("Corrugation Failed Insertion due to - Exception: " + e.getMessage());
//            e.printStackTrace();
        }

    }

    private void demoCorrugationFetchBycorrugationId(int corrugationId) {

        try {

            CorrugationDTO CorrugationDTO = corrugationService.fetchCorrugationById(corrugationId);
            logger.info("Existing Id: " + CorrugationDTO.getCorrugationId());

        } catch (CorrugationException e) {
            logger.info("Corrugation Failed with Exception - " + e.getMessage());
//            e.printStackTrace();
        }

    }


}