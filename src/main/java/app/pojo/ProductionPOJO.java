package app.pojo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

public class ProductionPOJO {

    private Long productionAmount; //  Total sum of 1st (or 2nd) class prod amount for given prod date
    private LocalDate productionDate;
    private Long epochMilliSecond;  //  productionDate to equivalent milli seconds (UTC)

    public ProductionPOJO(Long productionAmount, LocalDate productionDate) {
        this.productionAmount = productionAmount;
        this.productionDate = productionDate;
        this.epochMilliSecond = productionDate.toEpochSecond(LocalTime.MIN, ZoneOffset.UTC) * 1000;
    }

    @Override
    public String toString() {
        return "ProductionPOJO{" +
                "productionAmount=" + productionAmount +
                ", productionDate=" + productionDate +
                ", dateTimeInMs=" + epochMilliSecond +
                '}';
    }

    public Long getProductionAmount() {
        return productionAmount;
    }

    public void setProductionAmount(Long productionAmount) {
        this.productionAmount = productionAmount;
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
