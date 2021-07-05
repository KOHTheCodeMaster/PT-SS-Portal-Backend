package app.entity;

import app.dto.CorrugationDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Corrugation {

    // corrugationId, corrugationDate, itemType, colour, corrugationType, amount);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer corrugationId;
    private LocalDate corrugationDate;
    private String itemType;
    private String colour;
    private String corrugationType;
    private Integer amount;
    /**
     * Convert from given Corrugation entity instance to CorrugationDTO instance
     *
     * @return CorrugationDTO instance corresponding to the given Corrugation
     */
    public CorrugationDTO convertToDTO() {

        CorrugationDTO corrugationDTO = new CorrugationDTO();

        corrugationDTO.setCorrugationId(corrugationId);
        corrugationDTO.setCorrugationDate(corrugationDate);
        corrugationDTO.setItemType(itemType);
        corrugationDTO.setColour(colour);
        corrugationDTO.setCorrugationType(corrugationType);
        corrugationDTO.setAmount(amount);

        return corrugationDTO;
    }


    @Override
    public String toString() {
        return "CorrugationDTO{" +
                "corrugationId=" + corrugationId +
                ", corrugationDate=" + corrugationDate +
                ", itemType='" + itemType + '\'' +
                ", colour='" + colour + '\'' +
                ", corrugationType='" + corrugationType + '\'' +
                ", amount=" + amount +
                '}';
    }

    public Integer getCorrugationId() {
        return corrugationId;
    }

    public void setCorrugationId(Integer corrugationId) {
        this.corrugationId = corrugationId;
    }

    public LocalDate getCorrugationDate() {
        return corrugationDate;
    }

    public void setCorrugationDate(LocalDate corrugationDate) {
        this.corrugationDate = corrugationDate;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getCorrugationType() {
        return corrugationType;
    }

    public void setCorrugationType(String corrugationType) {
        this.corrugationType = corrugationType;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
