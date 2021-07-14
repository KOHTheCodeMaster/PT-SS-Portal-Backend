package app.service;

import app.dto.TargetDTO;
import app.entity.Target;
import app.exceptions.TargetException;
import app.pojo.DailyTargetPOJO;
import app.pojo.YearMonthPojo;
import app.repository.TargetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Service(value = "targetService")
@Transactional
public class TargetService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TargetService.class);
    private final TargetRepository targetRepository;

    @Autowired
    public TargetService(TargetRepository targetRepository) {
        this.targetRepository = targetRepository;
    }

    /**
     * Insert new target record in Target table for the given targetDTO
     *
     * @param targetDTO targetDTO which needs to be inserted into Target Table
     * @return targetId of the newly added target record on successful insertion, -1 on Failure.
     * @throws TargetException If targetDTO, or any of its field is null
     */
    public int addTarget(TargetDTO targetDTO) throws TargetException {

        validateTargetDTO(targetDTO);

        //  Throw Target.ALREADY_EXISTS TargetException if given month and type already exists
        if (targetRepository.findByMonthAndYearAndType(targetDTO.getMonth(), targetDTO.getYear(), targetDTO.getType())
                .isPresent()) {
            String msg = "Target.ALREADY_EXISTS:";
            LOGGER.info(msg);
            throw new TargetException(msg, targetDTO, HttpStatus.ALREADY_REPORTED.value());
        }

        Target target = Target.parseDTO(targetDTO);
//        LOGGER.info("Target: " + target.toString());

        try {
            //  Save the entity & return the auto generated primary key of the newly saved record
            return targetRepository.save(target).getTargetId();
        } catch (Exception e) {
            LOGGER.error("Failed to Save Entry due to Exception: " + e.getMessage());
        }
        return -1;
    }

    /**
     * targetDTO is INVALID if its null OR any of its field is null, Otherwise Valid
     *
     * @param targetDTO targetDTO which needs to be validated
     * @throws TargetException If targetDTO, or any of its field is null
     */
    private void validateTargetDTO(TargetDTO targetDTO) throws TargetException {

        //  Throw TargetException for invalid targetDTO
        //  Note: type must be either "P" OR "S" only.
        if (targetDTO == null ||
//                targetDTO.getType() == null || !targetDTO.getType().matches("[PS]") ||
                targetDTO.getType() == null || (targetDTO.getType() != 'P' && targetDTO.getType() != 'S') ||
                targetDTO.getYear() == null || !(targetDTO.getYear().toString().matches("\\d{4}")) ||
                targetDTO.getMonth() == null || !(targetDTO.getMonth().toString().matches("\\d[012]?")) ||
                targetDTO.getTargetAmount() == null) {

            String msg = "Target.INVALID_TARGET_DTO";
            LOGGER.error(msg);
            throw new TargetException(msg, targetDTO, HttpStatus.CONFLICT.value());
        }

/*
        //  Individual If case check for logging purpose
        if (targetDTO == null) {
            String msg = "Target.INVALID_TARGET_DTO:\nTargetDTO is null";
            LOGGER.error(msg);
            throw new TargetException(msg);
        }
        if (targetDTO.getType() == null) {
            String msg = "Target.INVALID_TARGET_DTO:" +
                    "type is null\n" + targetDTO;
            LOGGER.error(msg);
            throw new TargetException(msg);
        }
        if (targetDTO.getType() != 'P' && targetDTO.getType() != 'S') {
            String msg = "Target.INVALID_TARGET_DTO:" +
                    "type not matches P or S\n" + targetDTO;
            LOGGER.error(msg);
            throw new TargetException(msg);
        }
//        if (!targetDTO.getType().matches("[PS]")) {
//            String msg = "Target.INVALID_TARGET_DTO:" +
//                    "type not matches P or S\n" + targetDTO;
//            LOGGER.error(msg);
//            throw new TargetException(msg);
//        }
        if (targetDTO.getYear() == null) {
            String msg = "Target.INVALID_TARGET_DTO:" +
                    "year is null\n" + targetDTO;
            LOGGER.error(msg);
            throw new TargetException(msg);
        }
        if (!(targetDTO.getYear().toString().matches("\\d{4}"))) {

            String msg = "Target.INVALID_TARGET_DTO:" +
                    "year is invalid\n" + targetDTO;
            LOGGER.error(msg);
            throw new TargetException(msg);

        }
        if (targetDTO.getMonth() == null) {

            String msg = "Target.INVALID_TARGET_DTO:" +
                    "month is null\n" + targetDTO;
            LOGGER.error(msg);
            throw new TargetException(msg);

        }
        if (!(targetDTO.getMonth().toString().matches("\\d[012]?"))) {

            String msg = "Target.INVALID_TARGET_DTO:" +
                    "month is invalid\n" + targetDTO;
            LOGGER.error(msg);
            throw new TargetException(msg);

        }
        if (targetDTO.getTargetAmount() == null) {

            String msg = "Target.INVALID_TARGET_DTO:" +
                    "target amount null\n" + targetDTO;
            LOGGER.error(msg);
            throw new TargetException(msg);
        }
*/

        LOGGER.info("TargetDTO is Valid.");
    }

    public ArrayList<DailyTargetPOJO> getMonthlyTargetListByMonthAndYearAndType(String strYearAndMonth, Character type) throws TargetException {

        ArrayList<DailyTargetPOJO> list = new ArrayList<>();

        //  Validate & Parse strYearAndMonth to YearAndMonthPojo
        YearMonthPojo yearMonthPojo = YearMonthPojo.parseStringToYearMonthPojo(strYearAndMonth);

        if (yearMonthPojo == null) {
            String msg = "Invalid strYearAndMonth format.\n" +
                    "Required: YYYY-MM\n" +
                    "Found: " + strYearAndMonth;
            throw new TargetException(msg);
        }

        //  Find target by year and month
        Optional<Target> optionalTarget = targetRepository.findByMonthAndYearAndType(yearMonthPojo.getMonth(),
                yearMonthPojo.getYear(), type);

        //  When target not found, Throw TargetException, Otherwise get targetDTO
        TargetDTO targetDTO = optionalTarget.orElseThrow(() -> new TargetException("Target.NOT_FOUND")).convertToDTO();

        //  Iterate for each day of given month
        int maxDayOfMonth = yearMonthPojo.getEndDate().getDayOfMonth();
        for (int dayCount = 1; dayCount <= maxDayOfMonth; dayCount++)
            //  Add new DailyTargetPOJO with targetAmount & date with dayCount
            //  Note: dailyTargetAmount = total monthly target / max. day of month
            list.add(new DailyTargetPOJO(
                    Long.parseLong(targetDTO.getTargetAmount() + "") / maxDayOfMonth,
                    LocalDate.of(yearMonthPojo.getYear(), yearMonthPojo.getMonth(), dayCount)
            ));

        return list;
    }

    public TargetDTO getTargetByMonthAndYearAndType(String strYearAndMonth, Character type) throws TargetException {

        //  Validate & Parse strYearAndMonth to YearAndMonthPojo
        YearMonthPojo yearMonthPojo = YearMonthPojo.parseStringToYearMonthPojo(strYearAndMonth);

        if (yearMonthPojo == null) {
            String msg = "Invalid strYearAndMonth format.\n" +
                    "Required: YYYY-MM\n" +
                    "Found: " + strYearAndMonth;
            throw new TargetException(msg);
        }

        //  Find target by year and month
        Optional<Target> optionalTarget = targetRepository.findByMonthAndYearAndType(yearMonthPojo.getMonth(),
                yearMonthPojo.getYear(), type);

        //  When target not found, Throw TargetException, Otherwise return targetDTO
        Target target = optionalTarget.orElseThrow(() -> new TargetException("Target.NOT_FOUND"));
        return target.convertToDTO();

    }
}
