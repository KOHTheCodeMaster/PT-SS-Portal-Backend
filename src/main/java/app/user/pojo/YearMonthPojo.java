package app.user.pojo;

import java.time.LocalDate;

public class YearMonthPojo {

    private final int year;
    private final int month;
    private LocalDate startDate;
    private LocalDate endDate;

    public YearMonthPojo(int year, int month) {
        this.year = year;
        this.month = month;
    }

    public static YearMonthPojo parseStringToYearMonthPojo(String strYearAndMonth) {

        //  Validate strYearAndMonth format: YYYY-MM i.e. ####-##
        if (strYearAndMonth == null || !strYearAndMonth.matches("\\d{4}-\\d{2}")) return null;

        //  Parse Year & Month from strYearAndMonth which is in form: YYYY-MM
        String[] arrYearAndMonth = strYearAndMonth.split("-");
        int year = Integer.parseInt(arrYearAndMonth[0]);
        int month = Integer.parseInt(arrYearAndMonth[1]);

        YearMonthPojo yearMonthPojo = new YearMonthPojo(year, month);

        //  Initialize startDate with 1 & endDate with last day of month
        int lengthOfMonth = LocalDate.of(year, month, 1).lengthOfMonth();
        yearMonthPojo.setStartDate(LocalDate.of(year, month, 1));
        yearMonthPojo.setEndDate(LocalDate.of(year, month, lengthOfMonth));
//        System.out.println("startDate: " + yearMonthPojo.getStartDate() +
//                " | endDate: " + yearMonthPojo.getEndDate());

        return yearMonthPojo;

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
}
