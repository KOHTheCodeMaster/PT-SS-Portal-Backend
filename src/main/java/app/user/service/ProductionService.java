package app.user.service;

import app.user.dto.ProductionDTO;
import app.user.entity.Production;
import app.user.exceptions.ProductionException;
import app.user.repository.ProductionRepository;
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
     * Validate productionId, retrieve production from DB for the given productionId & return its productionDTO.
     *
     * @param productionId id corresponding to the production record of the Production table in DB
     * @return productionDTO for the given productionId
     * @throws ProductionException If productionId is null OR < 1 OR is not found in DB
     */
    public ProductionDTO getProductionById(final int productionId) throws ProductionException {

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

        //  Throw ProductionException for invalid productionId
        if (productionId == null || productionId < 1)
            throw new ProductionException("Production.INVALID_PRODUCTION_ID : " + productionId);

        productionRepository.findById(productionId).orElseThrow(
                () -> new ProductionException("Production.PRODUCTION_NOT_FOUND with id: " + productionId)
        );

        //  Delete production from DB using production entity
//        productionRepository.delete(production);

        //  Delete production from DB using productionId
        productionRepository.deleteById(productionId);

    }

}