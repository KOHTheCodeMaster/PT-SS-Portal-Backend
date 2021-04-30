package app.user.service;

import app.user.dto.SalesDTO;
import app.user.entity.Sales;
import app.user.exceptions.SalesException;
import app.user.repository.SalesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service(value = "salesService")
@Transactional
public class SalesService {

    private final SalesRepository salesRepository;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public SalesService(SalesRepository SalesRepository) {
        this.salesRepository = SalesRepository;
    }

    /**
     * Insert new sales record in Sales table for the given salesDTO
     *
     * @param salesDTO salesDTO which needs to be inserted into Sales Table
     * @return salesId of the newly added sales record on successful insertion, -1 on Failure.
     */
    public int addSales(SalesDTO salesDTO) {

        // Sales_Id, Buyer Name, Buyer Phone number, Buyer Address, Item Types, Colour, Item Size, Date, Amount of Item,
        // Payment, Sales Name
        Sales sales = new Sales();

//        sales.setUserId(salesDTO.getUserId());  //  Auto Generated
        sales.setBuyerName(salesDTO.getBuyerName());
        sales.setBuyerPhoneNumber(salesDTO.getBuyerPhoneNumber());
        sales.setBuyerAddress(salesDTO.getBuyerAddress());
        sales.setItemSize(salesDTO.getItemType());
        sales.setColour(salesDTO.getColour());
        sales.setItemSize(salesDTO.getItemSize());
        sales.setSalesDate(salesDTO.getSalesDate());
        sales.setAmountOfItem(salesDTO.getAmountOfItem());
        sales.setPayment(salesDTO.getPayment());
        sales.setSalesName(salesDTO.getSalesName());

        try {
            //  Save the entity & return the auto generated primary key of the newly saved record
            return salesRepository.save(sales).getSalesId();
        } catch (Exception e) {
            logger.error("Failed to Save Entry due to Exception: " + e.getMessage());
        }
        return -1;
    }

    /**
     * Retrieve all the sales records from the Sales Table and return it as an ArrayList<SalesDTO>
     *
     * @return SalesDTO ArrayList of all the sales records from Sales Table
     */
    public ArrayList<SalesDTO> getAllSales() {

        ArrayList<SalesDTO> salesDTOList = new ArrayList<>();

        //  Retrieve all the saless records from the Sales Table as Iterable<Sales>
        Iterable<Sales> iterable = salesRepository.findAll();

        //  Iterate each sales entity, convert into its DTO & Add it to the salesDTOList
        iterable.forEach(sales -> salesDTOList.add(sales.convertToDTO()));

        return salesDTOList;

    }

    /**
     * Validate salesId, retrieve sales from DB for the given salesId & return its salesDTO.
     *
     * @param salesId id corresponding to the sales record of the Sales table in DB
     * @return salesDTO for the given salesId
     * @throws SalesException If salesId is null OR < 1 OR is not found in DB
     */
    public SalesDTO getSalesById(final int salesId) throws SalesException {

        validateSalesId(salesId);

        Sales sales = salesRepository.findById(salesId).orElseThrow(
                () -> new SalesException("Sales.SALES_NOT_FOUND with id: " + salesId)
        );
        return sales.convertToDTO();
    }

    /**
     * Delete sales record from DB for the given salesId
     *
     * @param salesId id corresponding to the sales which needs to be deleted
     * @throws SalesException If salesId is null OR < 1 OR is not found in DB
     */
    public void deleteSalesById(Integer salesId) throws SalesException {

        validateSalesId(salesId);

        salesRepository.findById(salesId).orElseThrow(
                () -> new SalesException("Sales.SALES_NOT_FOUND with id: " + salesId)
        );

        //  Delete sales from DB using sales entity
//        salesRepository.delete(sales);

        //  Delete sales from DB using salesId
        salesRepository.deleteById(salesId);

    }

    /**
     * salesId is INVALID for null OR < 1, Otherwise Valid
     *
     * @param salesId id which needs to be validated
     * @throws SalesException If salesId is null OR < 1
     */
    private void validateSalesId(Integer salesId) throws SalesException {

        //  Throw SalesException for invalid salesId
        if (salesId == null || salesId < 1)
            throw new SalesException("Sales.INVALID_SALES_ID : " + salesId);


    }
}
