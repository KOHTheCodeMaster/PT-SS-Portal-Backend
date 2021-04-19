package app.user.service;

import app.user.dto.SalesStaffDTO;
import app.user.dto.UserDTO;
import app.user.entity.SalesStaff;
import app.user.entity.User;
import app.user.exceptions.SalesStaffException;
import app.user.exceptions.SalesStaffException;
import app.user.repository.SalesStaffRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service(value = "salesStaffService")
@Transactional
public class SalesStaffService {

    private final SalesStaffRepository salesStaffRepository;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public SalesStaffService(SalesStaffRepository SalesStaffRepository) {
        this.salesStaffRepository = SalesStaffRepository;
    }

    public int insertIntoSalesStaff(SalesStaffDTO salesStaffDTO) throws SalesStaffException {

        //  Throw SalesStaffException if salesStaff with same salesId already exists in DB
//        if (salesStaffRepository.findById(salesStaffDTO.getSalesId()).isPresent())
//            throw new SalesStaffException("SalesStaff.ENTRY_ALREADY_EXISTS with Id: " + salesStaffDTO.getSalesId());

        SalesStaff salesStaff = new SalesStaff();
        // Sales_Id, Buyer Name, Buyer Phone number, Buyer Address, Item Types, Colour, Item Size, Date, Amount of Item,
        // Payment, Sales Name
//        salesStaff.setUserId(salesStaffDTO.getUserId());  //  Auto Generated
        salesStaff.setBuyerName(salesStaffDTO.getBuyerName());
        salesStaff.setBuyerPhoneNumber(salesStaffDTO.getBuyerPhoneNumber());
        salesStaff.setBuyerAddress(salesStaffDTO.getBuyerAddress());
        salesStaff.setItemSize(salesStaffDTO.getItemType());
        salesStaff.setColour(salesStaffDTO.getColour());
        salesStaff.setItemSize(salesStaffDTO.getItemSize());
        salesStaff.setSalesDate(salesStaffDTO.getSalesDate());
        salesStaff.setAmountOfItem(salesStaffDTO.getAmountOfItem());
        salesStaff.setPayment(salesStaffDTO.getPayment());
        salesStaff.setSalesName(salesStaffDTO.getSalesName());
        try {
            SalesStaff tempSalesStaffEntity = salesStaffRepository.save(salesStaff);
            return tempSalesStaffEntity.getSalesId();
        } catch (Exception e){
            logger.error("Failed to Save Entry due to Exception: " + e.getMessage());
        }
        return -1;
    }

    public SalesStaffDTO fetchSalesStaffById(final int salesId) throws SalesStaffException {

        SalesStaff salesStaff = salesStaffRepository.findById(salesId).orElseThrow(
                () -> new SalesStaffException("SalesStaff.SALES_NOT_FOUND with id: " + salesId)
        );

        return salesStaff.convertToDTO();
    }

    public void deleteSalesById(Integer salesId) throws SalesStaffException {

/*
        SalesStaff salesStaff = salesStaffRepository.findById(salesId).orElseThrow(
                () -> new SalesStaffException("SalesStaff.SALES_NOT_FOUND with id: " + salesId)
        );

        //  Delete salesStaff from DB
//        salesStaffRepository.delete(salesStaff);
*/
        //  Delete salesStaff from DB
        salesStaffRepository.deleteById(salesId);
        
    }
}
