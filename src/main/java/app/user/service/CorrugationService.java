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

@Service(value = "corrugationService")
@Transactional
public class CorrugationService {

    private final CorrugationRepository corrugationRepository;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public CorrugationService(CorrugationRepository CorrugationRepository) {
        this.corrugationRepository = CorrugationRepository;
    }

    public int insertIntoCorrugation(CorrugationDTO corrugationDTO) {

        //  Throw CorrugationException if corrugation with same corrugationId already exists in DB
//        if (CorrugationRepository.findById(corrugationDTO.getCorrugationId()).isPresent())
//            throw new CorrugationException("corrugation.ENTRY_ALREADY_EXISTS with Id: " + corrugationDTO.getCorrugationId());

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
            logger.error("Failed to Save Entry due to Exception: " + e.getMessage());
        }
        return -1;
    }

    public CorrugationDTO fetchCorrugationById(final int corrugationId) throws CorrugationException {

        Corrugation corrugation = corrugationRepository.findById(corrugationId).orElseThrow(
                () -> new CorrugationException("corrugation.CORRUGATION_NOT_FOUND with id: " + corrugationId)
        );

        return corrugation.convertToDTO();
    }

    public void deleteCorrugationById(Integer corrugationId) throws CorrugationException {

        //  Throw CorrugationException for invalid corrugationId
        if (corrugationId == null || corrugationId < 1)
            throw new CorrugationException("Corrugation.INVALID_CORRUGATION_ID : " + corrugationId);

        corrugationRepository.findById(corrugationId).orElseThrow(
                () -> new CorrugationException("Corrugation.CORRUGATION_NOT_FOUND with id: " + corrugationId)
        );

        //  Delete Corrugation from DB using Corrugation entity
//        CorrugationRepository.delete(Corrugation);

        //  Delete Corrugation from DB using corrugationId
        corrugationRepository.deleteById(corrugationId);

    }

}
