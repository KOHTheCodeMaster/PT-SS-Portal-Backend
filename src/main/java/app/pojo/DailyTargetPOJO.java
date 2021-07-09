package app.pojo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

public class DailyTargetPOJO {

    private Long dailyTargetAmount; //  total monthly target / max. days of month
    private LocalDate targetDate;
    private Long epochMilliSecond;  //  targetDate to equivalent milli seconds (UTC)

    public DailyTargetPOJO(Long dailyTargetAmount, LocalDate targetDate) {
        this.dailyTargetAmount = dailyTargetAmount;
        this.targetDate = targetDate;
        this.epochMilliSecond = targetDate.toEpochSecond(LocalTime.MIN, ZoneOffset.UTC) * 1000;
    }

    @Override
    public String toString() {
        return "DailyTargetPOJO{" +
                "dailyTargetAmount=" + dailyTargetAmount +
                ", targetDate=" + targetDate +
                ", epochMilliSecond=" + epochMilliSecond +
                '}';
    }

    public Long getDailyTargetAmount() {
        return dailyTargetAmount;
    }

    public void setDailyTargetAmount(Long dailyTargetAmount) {
        this.dailyTargetAmount = dailyTargetAmount;
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
