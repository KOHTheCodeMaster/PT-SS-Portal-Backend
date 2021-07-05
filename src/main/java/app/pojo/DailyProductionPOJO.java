package app.pojo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

public class DailyProductionPOJO {

    private Long dailyProductionAmount; //  Total sum of 1st (or 2nd) class prod amount for given prod date
    private LocalDate productionDate;
    private Long epochMilliSecond;  //  productionDate to equivalent milli seconds (UTC)

    public DailyProductionPOJO(Long dailyProductionAmount, LocalDate productionDate) {
        this.dailyProductionAmount = dailyProductionAmount;
        this.productionDate = productionDate;
        this.epochMilliSecond = productionDate.toEpochSecond(LocalTime.MIN, ZoneOffset.UTC) * 1000;
    }

    @Override
    public String toString() {
        return "DailyProductionPOJO{" +
                "dailyProductionAmount=" + dailyProductionAmount +
                ", productionDate=" + productionDate +
                ", dateTimeInMs=" + epochMilliSecond +
                '}';
    }

    public Long getDailyProductionAmount() {
        return dailyProductionAmount;
    }

    public void setDailyProductionAmount(Long dailyProductionAmount) {
        this.dailyProductionAmount = dailyProductionAmount;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDate productionDate) {
        this.productionDate = productionDate;
    }

    public Long getEpochMilliSecond() {
        return epochMilliSecond;
    }

    public void setEpochMilliSecond(Long epochMilliSecond) {
        this.epochMilliSecond = epochMilliSecond;
    }
}
