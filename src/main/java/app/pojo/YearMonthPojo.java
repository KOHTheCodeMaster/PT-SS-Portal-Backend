package app.pojo;

import app.exceptions.TargetException;

import java.time.LocalDate;
import java.util.ArrayList;

public class YearMonthPojo {

    private final int year;
    private final int month;
    private final int lengthOfMonth;
    private LocalDate startDate;
    private LocalDate endDate;

    public YearMonthPojo(int year, int month) {
        this.year = year;
        this.month = month;

        //  Initialize startDate with 1 & endDate with last day of month
        lengthOfMonth = LocalDate.of(year, month, 1).lengthOfMonth();
        startDate = LocalDate.of(year, month, 1);
        endDate = LocalDate.of(year, month, lengthOfMonth);
    }

    public static ArrayList<YearMonthPojo> fromYear(String strYear) throws TargetException {

        //  Validate strYear format: YYYY i.e. ####
        if (strYear == null || !strYear.matches("2\\d{3}")) throw new TargetException("Target.INVALID_YEAR");

        //  Parse Year from strYear which is in form: YYYY
        int year = Integer.parseInt(strYear);

        //  Initialize list of YearMonthPojo for each month for given year
        ArrayList<YearMonthPojo> list = new ArrayList<>();
        for (int month = 1; month <= 12; month++) list.add(new YearMonthPojo(year, month));

        return list;

    }

    public static YearMonthPojo parseStringToYearMonthPojo(String strYearAndMonth) {

        //  Validate strYearAndMonth format: YYYY-MM i.e. ####-##
        if (strYearAndMonth == null || !strYearAndMonth.matches("\\d{4}-((0[1-9])|(1[0-2]))")) return null;

        //  Parse Year & Month from strYearAndMonth which is in form: YYYY-MM
        String[] arrYearAndMonth = strYearAndMonth.split("-");
        int year = Integer.parseInt(arrYearAndMonth[0]);
        int month = Integer.parseInt(arrYearAndMonth[1]);

        return new YearMonthPojo(year, month);

    }

    @Override
    public String toString() {
        return "YearMonthPojo{" +
                "year=" + year +
                ", month=" + month +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getLengthOfMonth() {
        return lengthOfMonth;
    }
}
