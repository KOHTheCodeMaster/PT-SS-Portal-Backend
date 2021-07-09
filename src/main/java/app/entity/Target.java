package app.entity;

import app.dto.TargetDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Target {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer targetId;
    private Character type;
    private Integer year;
    private Integer month;
    private Integer targetAmount;
    private Integer realizedAmount;

    public static Target parseDTO(TargetDTO targetDTO) {

        Target target = new Target();

        target.setType(targetDTO.getType());
        target.setYear(targetDTO.getYear());
        target.setMonth(targetDTO.getMonth());
        target.setTargetAmount(targetDTO.getTargetAmount());
        target.setRealizedAmount(targetDTO.getRealizedAmount());

        return target;

    }

    public TargetDTO convertToDTO() {

        TargetDTO targetDTO = new TargetDTO();

        targetDTO.setTargetId(targetId);
        targetDTO.setType(type);
        targetDTO.setYear(year);
        targetDTO.setMonth(month);
        targetDTO.setTargetAmount(targetAmount);
//        targetDTO.setRealizedAmount(realizedAmount);

        return targetDTO;

    }

    @Override
    public String toString() {
        return "Target{" +
                "targetId=" + targetId +
                ", type=" + type +
                ", year=" + year +
                ", month=" + month +
                ", targetAmount=" + targetAmount +
                ", realizedAmount=" + realizedAmount +
                '}';
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public Character getType() {
        return type;
    }

    public void setType(Character type) {
        this.type = type;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(Integer targetAmount) {
        this.targetAmount = targetAmount;
    }

    public Integer getRealizedAmount() {
        return realizedAmount;
    }

    public void setRealizedAmount(Integer realizedAmount) {
        this.realizedAmount = realizedAmount;
    }
}
