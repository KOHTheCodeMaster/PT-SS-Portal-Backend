package app.controller;

import app.dto.TargetDTO;
import app.exceptions.TargetException;
import app.pojo.TargetPOJO;
import app.service.TargetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
     * @return targetId of the newly added target record in Target Table
     */
    @PostMapping(value = "/target")
    public ResponseEntity<Map<String, Object>> addTarget(@RequestBody TargetDTO targetDTO) throws TargetException {

        //  Add targetDTO in DB
        int targetId = targetService.addTarget(targetDTO);

        String msg = "Target added successfully with id: " + targetId;
        LOGGER.info(msg);

        Map<String, Object> map = new HashMap<>();
        map.put("message", msg);
        map.put("status", 201);

        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    /**
     * Retrieve List of daily target for the given year & month.
     * Daily target consists of following:
     * 1. dailyTargetAmount = total monthly target / max. day of month
     * 2. Target Date
     * 3. epochMilliSecond
     *
     * @param strYearAndMonth for which month & year the target is required
     * @return ArrayList List of TargetDTO containing daily target for the given strYearAndMonth
     * @throws TargetException If strYearAndMonth is null OR (Month is < 1 OR > 12)
     */
    @GetMapping(value = "/target/p/year-month/{strYearAndMonth}")
    public ResponseEntity<ArrayList<TargetPOJO>> getDailyProductionTargetByYearAndMonth(
            @PathVariable String strYearAndMonth) throws TargetException {

        LOGGER.info("Requesting TargetDTO for date: {}", strYearAndMonth);
        return new ResponseEntity<>(targetService.getDailyTargetListByMonthAndYearAndType(
                strYearAndMonth, 'P'), HttpStatus.OK);

    }

    /**
     * Retrieve List of daily selling target for the given year & month.
     * Daily target consists of following:
     * 1. target per day = total monthly target / max. day of month
     * 2. Target Date
     * 3. epochMilliSecond
     *
     * @param strYearAndMonth for which month & year the target is required
     * @return ArrayList List of TargetDTO containing daily target for the given strYearAndMonth
     * @throws TargetException If strYearAndMonth is null OR (Month is < 1 OR > 12)
     */
    @GetMapping(value = "/target/s/year-month/{strYearAndMonth}")
    public ResponseEntity<ArrayList<TargetPOJO>> getDailySellingTargetByYearAndMonth(
            @PathVariable String strYearAndMonth) throws TargetException {

        LOGGER.info("Requesting TargetDTO for date: {}", strYearAndMonth);
        return new ResponseEntity<>(targetService.getDailyTargetListByMonthAndYearAndType(strYearAndMonth, 'S'), HttpStatus.OK);

    }

    /**
     * Retrieve List of monthly targets for each month of the given year.
     * Monthly target consists of following:
     * 1. dailyTargetAmount = total monthly target
     * 2. Target Date = date for year, month & last day of the month
     * 3. epochMilliSecond
     *
     * @param strYear for which year the target data is required
     * @return ArrayList List of TargetDTO containing monthly targets for the given strYear
     * @throws TargetException If strYear is null OR strYear < 2000
     */
    @GetMapping(value = "/target/p/year/{strYear}")
    public ResponseEntity<ArrayList<TargetPOJO>> getMonthlyProductionTargetByYearAndMonth(
            @PathVariable String strYear) throws TargetException {

        LOGGER.info("Requesting Yearly Report for : {}", strYear);
        return new ResponseEntity<>(targetService.getMonthlyTargetListByYearAndType(
                strYear, 'P'), HttpStatus.OK);

    }

}
