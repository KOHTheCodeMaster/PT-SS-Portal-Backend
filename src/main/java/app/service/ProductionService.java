package app.service;

import app.dto.ProductionDTO;
import app.entity.Production;
import app.exceptions.ProductionException;
import app.exceptions.TargetException;
import app.pojo.ProductionPOJO;
import app.pojo.YearMonthPojo;
import app.repository.ProductionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service(value = "productionService")
@Transactional
public class ProductionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductionService.class);
    private final ProductionRepository productionRepository;

    @Autowired
    public ProductionService(ProductionRepository productionRepository) {
        this.productionRepository = productionRepository;
    }

    /**
     * Insert new production record in Production table for the given productionDTO
     *
     * @param productionDTO productionDTO which needs to be inserted into Production Table
     * @return productionId of the newly added production record on successful insertion, -1 on Failure.
     */
    public int addProduction(ProductionDTO productionDTO) {

        //  productionId, supervisorName, nameOfReporter, Shift, Date, cardNumber, coilNumber, weight
        //  size, startTime, endTime, totalTime, prodAmount1stClass, prodAmount2ndClass, notes
        Production production = new Production();

//        production.setProductionId(productionDTO.getProductionId());  //  Auto Generated
        production.setSupervisorName(productionDTO.getSupervisorName());
        production.setNameOfReporter(productionDTO.getNameOfReporter());
        production.setShift(productionDTO.getShift());
        production.setProductionDate(productionDTO.getProductionDate());
        production.setCardNumber(productionDTO.getCardNumber());
        production.setCoilNumber(productionDTO.getCoilNumber());
        production.setWeight(productionDTO.getWeight());
        production.setSize(productionDTO.getSize());
        production.setStartTime(productionDTO.getStartTime());
        production.setEndTime(productionDTO.getEndTime());
        production.setTotalTime(productionDTO.getTotalTime());
        production.setProductionAmount1stClass(productionDTO.getProductionAmount1stClass());
        production.setProductionAmount2ndClass(productionDTO.getProductionAmount2ndClass());
        production.setNotes(productionDTO.getNotes());

        try {
            //  Save the entity & return the auto generated primary key of the newly saved record
            return productionRepository.save(production).getProductionId();
        } catch (Exception e) {
            LOGGER.error("Failed to Save Entry due to Exception: " + e.getMessage());
        }
        return -1;
    }

    /**
     * Retrieve all the production records from the Production Table and return it as an ArrayList<ProductionDTO>
     *
     * @return ProductionDTO ArrayList of all the production records from Production Table
     */
    public ArrayList<ProductionDTO> getAllProductions() {

        ArrayList<ProductionDTO> productionDTOList = new ArrayList<>();

        //  Retrieve all the productions records from the Production Table as Iterable<Production>
        Iterable<Production> iterable = productionRepository.findAll();

        //  Iterate each Production entity, convert into its DTO & Add it to the productionDTOList
        iterable.forEach(production -> productionDTOList.add(production.convertToDTO()));

        return productionDTOList;

    }

    /**
     * Retrieve all the unique supervisor names from the Production Table and return it as an ArrayList<String>
     *
     * @return supervisorNameList ArrayList of all the production records from Production Table
     */
    public ArrayList<String> getSupervisorNameList() {

        ArrayList<String> supervisorNameList;

        //  Retrieve all the productions records from the Production Table as Iterable<Production>
        supervisorNameList = productionRepository.findDistinctSupervisorNameList();

        if (supervisorNameList != null) System.out.println("Supervisor Name List: " + supervisorNameList.size());

        return supervisorNameList;

    }

    /**
     * Retrieve all the sizes from the Production Table and return it as an ArrayList<String>
     *
     * @return list of size for all the production records from Production Table
     */
    public ArrayList<String> getSizeList() {

        ArrayList<String> sizeList;

        //  Retrieve all the productions records from the Production Table as Iterable<Production>
        sizeList = productionRepository.findDistinctSizeList();

        if (sizeList != null) System.out.println("Size List: " + sizeList.size());

        return sizeList;

    }

    /**
     * Validate productionId, retrieve production from DB for the given productionId & return its productionDTO.
     *
     * @param productionId id corresponding to the production record of the Production table in DB
     * @return productionDTO for the given productionId
     * @throws ProductionException If productionId is null OR < 1 OR is not found in DB
     */
    public ProductionDTO getProductionById(final int productionId) throws ProductionException {

        validateProductionId(productionId);

        Production production = productionRepository.findById(productionId).orElseThrow(
                () -> new ProductionException("Production.PRODUCTION_NOT_FOUND with id: " + productionId)
        );

        return production.convertToDTO();
    }

    /**
     * Delete production record from DB for the given productionId
     *
     * @param productionId id corresponding to the production which needs to be deleted
     * @throws ProductionException If productionId is null OR < 1 OR is not found in DB
     */
    public void deleteProductionById(Integer productionId) throws ProductionException {

        validateProductionId(productionId);

        productionRepository.findById(productionId).orElseThrow(
                () -> new ProductionException("Production.PRODUCTION_NOT_FOUND with id: " + productionId)
        );

        //  Delete production from DB using production entity
//        productionRepository.delete(production);

        //  Delete production from DB using productionId
        productionRepository.deleteById(productionId);

    }


    /**
     * Validate & parse strYearAndMonth into YearMonthPojo,
     * Retrieve list of daily production from DB for the given year & month.
     *
     * @param strYearAndMonth month & year for which the daily production is required. <br>
     *                        format: YYYY-MM | E.g.: 2021-01
     * @return ArrayList List of Daily Production for the given strYearAndMonth
     */
    public ArrayList<ProductionPOJO> getDailyProductionListForAll(final String strYearAndMonth) throws ProductionException {

        //  Validate & Parse strYearAndMonth to YearAndMonthPojo
        YearMonthPojo yearMonthPojo = YearMonthPojo.parseStringToYearMonthPojo(strYearAndMonth);

        if (yearMonthPojo == null) {
            String msg = "Invalid strYearAndMonth format.\n" +
                    "Required: YYYY-MM\n" +
                    "Found: " + strYearAndMonth;
            throw new ProductionException(msg);
        }

        return productionRepository.findDailyProductionListBetween(yearMonthPojo.getStartDate(),
                yearMonthPojo.getEndDate());

    }

    /**
     * Validate & parse strYearAndMonth into YearMonthPojo,
     * Retrieve list of daily production for 2nd class prod amount from DB for the given year & month.
     *
     * @param strYearAndMonth month & year for which the daily production is required. <br>
     *                        format: YYYY-MM | E.g.: 2021-01
     * @return ArrayList List of Daily Production of 2nd class for the given strYearAndMonth
     */
    public ArrayList<ProductionPOJO> get2ndClassDailyProductionList(final String strYearAndMonth) throws ProductionException {

        //  Validate & Parse strYearAndMonth to YearAndMonthPojo
        YearMonthPojo yearMonthPojo = YearMonthPojo.parseStringToYearMonthPojo(strYearAndMonth);

        if (yearMonthPojo == null) {
            String msg = "Invalid strYearAndMonth format.\n" +
                    "Required: YYYY-MM\n" +
                    "Found: " + strYearAndMonth;
            throw new ProductionException(msg);
        }

        return productionRepository.find2ndClassDailyProductionListBetween(yearMonthPojo.getStartDate(),
                yearMonthPojo.getEndDate());

    }

    /**
     * productionId is INVALID for null OR < 1, Otherwise Valid
     *
     * @param productionId id which needs to be validated
     * @throws ProductionException If productionId is null OR < 1
     */
    private void validateProductionId(Integer productionId) throws ProductionException {

        //  Throw ProductionException for invalid productionId
        if (productionId == null || productionId < 1)
            throw new ProductionException("Production.INVALID_PRODUCTION_ID : " + productionId);


    }

    /**
     * Retrieve List of monthly production for each month of the given year.
     * Monthly production consists of following:
     * 1. Production Amount = total monthly production
     * 2. Production Date = date for year, month & last day of the month
     * 3. epochMilliSecond
     *
     * @param strYear year for which the production data is required
     * @return ArrayList List of ProductionPojo containing monthly production for the given strYear
     * @throws TargetException If strYear is null OR strYear < 2000
     */
    public ArrayList<ProductionPOJO> getMonthlyProductionListByYear(String strYear) throws TargetException {

        ArrayList<ProductionPOJO> monthlyProductionList = new ArrayList<>();

        //  Validate & Parse strYear to YearMonthPojo
        ArrayList<YearMonthPojo> yearMonthPojoList = YearMonthPojo.fromYear(strYear);

        //  Find and add monthly production for each month of given year
        for (YearMonthPojo yearMonthPojo : yearMonthPojoList) {

            //  Find production by year and month
            ArrayList<ProductionPOJO> currentProductionList = productionRepository.find1stClassProductionBetweenDate(
                    yearMonthPojo.getStartDate(), yearMonthPojo.getEndDate());

            //  When production not found, continue
            if (currentProductionList.isEmpty()) continue;

            //  Compute total production for current month
            long currentMonthProductionAmount = currentProductionList.stream()
                    .mapToLong(ProductionPOJO::getProductionAmount)
                    .sum();

            /*
                Add Production Pojo to the monthlyProductionList
                productionAmount -> total production amount for the month
                productionData -> date for year, month & last day of the month
            */
            monthlyProductionList.add(new ProductionPOJO(currentMonthProductionAmount, yearMonthPojo.getEndDate()));

        }


        LOGGER.info(monthlyProductionList.size() + "");
        monthlyProductionList.forEach(productionPOJO -> LOGGER.info(productionPOJO.toString()));

        return monthlyProductionList;
    }
}
