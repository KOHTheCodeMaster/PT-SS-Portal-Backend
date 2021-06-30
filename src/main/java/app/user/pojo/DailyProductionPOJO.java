package app.user.pojo;

import java.time.LocalDate;

public class DailyProductionPOJO {

    private Long dailyProductionAmount;
    private LocalDate productionDate;

    public DailyProductionPOJO(Long dailyProductionAmount, LocalDate productionDate) {
        this.dailyProductionAmount = dailyProductionAmount;
        this.productionDate = productionDate;
    }

    @Override
    public String toString() {
        return "DailyProductionPOJO{" +
                "dailyProductionAmount=" + dailyProductionAmount +
                ", productionDate=" + productionDate +
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
}
