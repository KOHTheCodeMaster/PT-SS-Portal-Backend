package app.user.service;

import app.user.dto.ProductionStaffDTO;
import app.user.entity.ProductionStaff;
import app.user.exceptions.ProductionStaffException;
import app.user.repository.ProductionStaffRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service(value = "productionStaffService")
@Transactional
public class ProductionStaffService {

    private final ProductionStaffRepository productionStaffRepository;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ProductionStaffService(ProductionStaffRepository ProductionStaffRepository) {
        this.productionStaffRepository = ProductionStaffRepository;
    }

    public int insertIntoProductionStaff(ProductionStaffDTO productionStaffDTO) {

        //  Throw ProductionStaffException if productionStaff with same productionId already exists in DB
//        if (productionStaffRepository.findById(productionStaffDTO.getProductionId()).isPresent())
//            throw new ProductionStaffException("ProductionStaff.ENTRY_ALREADY_EXISTS with Id: " + productionStaffDTO.getProductionId());

        //  productionId, supervisorName, nameOfReporter, Shift, Date, cardNumber, coilNumber, weight
        //  size, startTime, endTime, totalTime, prodAmount1stClass, prodAmount2ndClass, notes
        ProductionStaff productionStaff = new ProductionStaff();

//        productionStaff.setProductionId(productionStaffDTO.getProductionId());  //  Auto Generated
        productionStaff.setSupervisorName(productionStaffDTO.getSupervisorName());
        productionStaff.setNameOfReporter(productionStaffDTO.getNameOfReporter());
        productionStaff.setShift(productionStaffDTO.getShift());
        productionStaff.setProductionDate(productionStaffDTO.getProductionDate());
        productionStaff.setCardNumber(productionStaffDTO.getCardNumber());
        productionStaff.setCoilNumber(productionStaffDTO.getCoilNumber());
        productionStaff.setWeight(productionStaffDTO.getWeight());
        productionStaff.setSize(productionStaffDTO.getSize());
        productionStaff.setStartTime(productionStaffDTO.getStartTime());
        productionStaff.setEndTime(productionStaffDTO.getEndTime());
        productionStaff.setTotalTime(productionStaffDTO.getTotalTime());
        productionStaff.setProdAmount1stClass(productionStaffDTO.getProdAmount1stClass());
        productionStaff.setProdAmount2ndClass(productionStaffDTO.getProdAmount2ndClass());
        productionStaff.setNotes(productionStaffDTO.getNotes());

        try {
            //  Save the entity & return the auto generated primary key of the newly saved record
            ProductionStaff tempProductionStaffEntity = productionStaffRepository.save(productionStaff);
            return tempProductionStaffEntity.getProductionId();
        } catch (Exception e) {
            logger.error("Failed to Save Entry due to Exception: " + e.getMessage());
        }
        return -1;
    }

    public ProductionStaffDTO fetchProductionStaffById(final int productionId) throws ProductionStaffException {

        ProductionStaff productionStaff = productionStaffRepository.findById(productionId).orElseThrow(
                () -> new ProductionStaffException("ProductionStaff.PRODUCTION_NOT_FOUND with id: " + productionId)
        );

        return productionStaff.convertToDTO();
    }

    public void deleteProductionStaffById(Integer productionId) throws ProductionStaffException {

        //  Throw ProductionStaffException for invalid productionId
        if (productionId == null || productionId < 1)
            throw new ProductionStaffException("ProductionStaff.INVALID_PRODUCTION_ID : " + productionId);

        productionStaffRepository.findById(productionId).orElseThrow(
                () -> new ProductionStaffException("ProductionStaff.PRODUCTION_NOT_FOUND with id: " + productionId)
        );

        //  Delete productionStaff from DB using productionStaff entity
//        productionStaffRepository.delete(productionStaff);

        //  Delete productionStaff from DB using productionId
        productionStaffRepository.deleteById(productionId);

    }

}
