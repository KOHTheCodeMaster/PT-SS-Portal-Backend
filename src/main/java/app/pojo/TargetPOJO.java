package app.pojo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

public class TargetPOJO {

    private Long targetAmount; //  total monthly target / max. days of month
    private LocalDate targetDate;
    private Long epochMilliSecond;  //  targetDate to equivalent milli seconds (UTC)

    public TargetPOJO(Long targetAmount, LocalDate targetDate) {
        this.targetAmount = targetAmount;
        this.targetDate = targetDate;
        this.epochMilliSecond = targetDate.toEpochSecond(LocalTime.MIN, ZoneOffset.UTC) * 1000;
    }

    @Override
    public String toString() {
        return "DailyTargetPOJO{" +
                "dailyTargetAmount=" + targetAmount +
                ", targetDate=" + targetDate +
                ", epochMilliSecond=" + epochMilliSecond +
                '}';
    }

    public Long getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(Long targetAmount) {
        this.targetAmount = targetAmount;
    }

    public LocalDate getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }

    public Long getEpochMilliSecond() {
        return epochMilliSecond;
    }

    public void setEpochMilliSecond(Long epochMilliSecond) {
        this.epochMilliSecond = epochMilliSecond;
    }
}
