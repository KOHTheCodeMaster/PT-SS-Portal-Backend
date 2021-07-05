package app.dto;

import app.enums.Shift;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ProductionDTO {

    //  productionId, supervisorName, nameOfReporter, Shift, Date, cardNumber, coilNumber, weight
    //  size, startTime, endTime, totalTime, prodAmount1stClass, prodAmount2ndClass, notes
    private Integer productionId;
    private String supervisorName;
    private String nameOfReporter;
    private Shift shift;
    private LocalDate productionDate;
    private Integer cardNumber;
    private String coilNumber;
    private Integer weight;
    private String size;
    private BigDecimal startTime;
    private BigDecimal endTime;
    private BigDecimal totalTime;
    private Integer productionAmount1stClass;
    private Integer productionAmount2ndClass;
    private String notes;

    @Override
    public String toString() {
        return "ProductionDTO{" +
                "productionId=" + productionId +
                ", supervisorName='" + supervisorName + '\'' +
                ", nameOfReporter='" + nameOfReporter + '\'' +
                ", shift=" + shift +
                ", date='" + productionDate + '\'' +
                ", cardNumber=" + cardNumber +
                ", coilNumber='" + coilNumber + '\'' +
                ", weight=" + weight +
                ", size='" + size + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", totalTime=" + totalTime +
                ", prodAmount1stClass=" + productionAmount1stClass +
                ", prodAmount2ndClass=" + productionAmount2ndClass +
                ", notes='" + notes + '\'' +
                '}';
    }

    public Integer getProductionId() {
        return productionId;
    }

    public void setProductionId(Integer productionId) {
        this.productionId = productionId;
    }

    public String getSupervisorName() {
        return supervisorName;
    }

    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }

    public String getNameOfReporter() {
        return nameOfReporter;
    }

    public void setNameOfReporter(String nameOfReporter) {
        this.nameOfReporter = nameOfReporter;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDate productionDate) {
        this.productionDate = productionDate;
    }

    public Integer getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Integer cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCoilNumber() {
        return coilNumber;
    }

    public void setCoilNumber(String coilNumber) {
        this.coilNumber = coilNumber;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public BigDecimal getStartTime() {
        return startTime;
    }

    public void setStartTime(BigDecimal startTime) {
        this.startTime = startTime;
    }

    public BigDecimal getEndTime() {
        return endTime;
    }

    public void setEndTime(BigDecimal endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(BigDecimal totalTime) {
        this.totalTime = totalTime;
    }

    public Integer getProductionAmount1stClass() {
        return productionAmount1stClass;
    }

    public void setProductionAmount1stClass(Integer productionAmount1stClass) {
        this.productionAmount1stClass = productionAmount1stClass;
    }

    public Integer getProductionAmount2ndClass() {
        return productionAmount2ndClass;
    }

    public void setProductionAmount2ndClass(Integer productionAmount2ndClass) {
        this.productionAmount2ndClass = productionAmount2ndClass;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
