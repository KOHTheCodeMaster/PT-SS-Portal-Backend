package app.pojo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

public class SalesPOJO {

    private Long salesAmount; //  Total sum of 1st (or 2nd) class prod amount for given prod date
    private LocalDate salesDate;
    private Long epochMilliSecond;  //  salesDate to equivalent milli seconds (UTC)

    public SalesPOJO(Long salesAmount, LocalDate salesDate) {
        this.salesAmount = salesAmount;
        this.salesDate = salesDate;
        this.epochMilliSecond = salesDate.toEpochSecond(LocalTime.MIN, ZoneOffset.UTC) * 1000;
    }

    @Override
    public String toString() {
        return "SalesPOJO{" +
                "salesAmount=" + salesAmount +
                ", salesDate=" + salesDate +
                ", dateTimeInMs=" + epochMilliSecond +
                '}';
    }

    public Long getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(Long salesAmount) {
        this.salesAmount = salesAmount;
    }

    public LocalDate getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(LocalDate salesDate) {
        this.salesDate = salesDate;
    }

    public Long getEpochMilliSecond() {
        return epochMilliSecond;
    }

    public void setEpochMilliSecond(Long epochMilliSecond) {
        this.epochMilliSecond = epochMilliSecond;
    }
}
