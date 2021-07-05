package app.dto;

public class TargetDTO {

    private Integer targetId;
    private Character type;
    private Integer year;
    private Integer month;
    private Integer targetAmount;
    private Integer realizedAmount;

    @Override
    public String toString() {
        return "TargetDTO{" +
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
