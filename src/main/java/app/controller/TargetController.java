package app.controller;

import app.dto.TargetDTO;
import app.exceptions.TargetException;
import app.service.TargetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class TargetController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TargetController.class);
    private final TargetService targetService;

    @Autowired
    public TargetController(TargetService targetService) {
        this.targetService = targetService;
    }

    /**
     * Insert new target record in Target table for the given targetDTO.
     * Note: 1. targetDTO is in Json format provided in POST request body.
     * 2. targetId is ignored since it is Auto-incremented by the DB.
     *
     * @param targetDTO targetDTO in the body of POST request as Json format
     * @return productionId of the newly added production record in Production Table
     */
    @PostMapping(value = "/target")
    public ResponseEntity<String> addTarget(@RequestBody TargetDTO targetDTO) throws TargetException {

        //  Add targetDTO in DB
//        LOGGER.info(targetDTO.toString());
        int targetId = targetService.addTarget(targetDTO);

        String msg = "Target added successfully with id: " + targetId;
        LOGGER.info(msg);

        return new ResponseEntity<>(msg, HttpStatus.CREATED);
    }

}
