package app.user.service;

import app.user.dto.CorrugationDTO;
import app.user.entity.Corrugation;
import app.user.exceptions.CorrugationException;
import app.user.repository.CorrugationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service(value = "corrugationService")
@Transactional
public class CorrugationService {

    private final CorrugationRepository corrugationRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(CorrugationService.class);

    @Autowired
    public CorrugationService(CorrugationRepository CorrugationRepository) {
        this.corrugationRepository = CorrugationRepository;
    }

    /**
     * Insert new corrugation record in Corrugation table for the given corrugationDTO
     *
     * @param corrugationDTO corrugationDTO which needs to be inserted into Corrugation Table
     * @return corrugationId of the newly added corrugation record on successful insertion, -1 on Failure.
     */
    public int addCorrugation(CorrugationDTO corrugationDTO) {

        // corrugationId, corrugationDate, itemType, colour, corrugationType, amount);
        Corrugation corrugation = new Corrugation();

//        corrugation.setCorrugationId(corrugationDTO.getCorrugationId());  //  Auto Generated
        corrugation.setCorrugationDate(corrugationDTO.getCorrugationDate());
        corrugation.setItemType(corrugationDTO.getItemType());
        corrugation.setColour(corrugationDTO.getColour());
        corrugation.setCorrugationType(corrugationDTO.getCorrugationType());
        corrugation.setAmount(corrugationDTO.getAmount());

        try {
            //  Save the entity & return the auto generated primary key of the newly saved record
            Corrugation tempCorrugationEntity = corrugationRepository.save(corrugation);
            return tempCorrugationEntity.getCorrugationId();
        } catch (Exception e) {
            LOGGER.error("Failed to Save Entry due to Exception: " + e.getMessage());
        }
        return -1;
    }

    /**
     * Validate corrugationId, retrieve corrugation from DB for the given corrugationId & return its corrugationDTO.
     *
     * @param corrugationId id corresponding to the corrugation record of the Corrugation table in DB
     * @return corrugationDTO for the given corrugationId
     * @throws CorrugationException If corrugationId is null OR < 1 OR is not found in DB
     */
    public CorrugationDTO fetchCorrugationById(final int corrugationId) throws CorrugationException {

        validateCorrugationId(corrugationId);

        Corrugation corrugation = corrugationRepository.findById(corrugationId).orElseThrow(
                () -> new CorrugationException("Corrugation.CORRUGATION_NOT_FOUND with id: " + corrugationId)
        );
        return corrugation.convertToDTO();

    }

    /**
     * Retrieve all the corrugations records from the Corrugation Table and return it as an ArrayList<CorrugationDTO>
     *
     * @return corrugationDTO ArrayList of all the corrugation records from Corrugation Table
     */
    public ArrayList<CorrugationDTO> getAllCorrugationsList() {

        ArrayList<CorrugationDTO> corrugationDTOList = new ArrayList<>();

        //  Retrieve all the corrugations records from the Corrugation Table as Iterable<Corrugation>
        Iterable<Corrugation> iterable = corrugationRepository.findAll();

        //  Iterate each corrugation entity, convert into its DTO & Add it to the corrugationDTOList
        iterable.forEach(corrugation -> corrugationDTOList.add(corrugation.convertToDTO()));

        return corrugationDTOList;

    }

    /**
     * Delete corrugation record from DB for the given corrugationId
     *
     * @param corrugationId id corresponding to the corrugation which needs to be deleted
     * @throws CorrugationException If corrugationId is null OR < 1 OR is not found in DB
     */
    public void deleteCorrugationById(Integer corrugationId) throws CorrugationException {

        //  Validate & Check if corrugationId exists in DB
        fetchCorrugationById(corrugationId);

        //  Delete Corrugation from DB using Corrugation entity
//        CorrugationRepository.delete(Corrugation);

        //  Delete Corrugation from DB using corrugationId
        corrugationRepository.deleteById(corrugationId);

    }

    /**
     * corrugationId is INVALID for null OR < 1, Otherwise Valid
     *
     * @param corrugationId id which needs to be validated
     * @throws CorrugationException If corrugationId is null OR < 1
     */
    private void validateCorrugationId(Integer corrugationId) throws CorrugationException {

        //  Throw CorrugationException for invalid corrugationId
        if (corrugationId == null || corrugationId < 1)
            throw new CorrugationException("Corrugation.INVALID_CORRUGATION_ID : " + corrugationId);


    }
}
