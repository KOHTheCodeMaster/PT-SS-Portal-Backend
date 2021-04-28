package app.user.entity;

import app.user.dto.ProductionStaffDTO;
import app.user.enums.Shift;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class ProductionStaff {

    //  productionId, supervisorName, nameOfReporter, Shift, Date, cardNumber, coilNumber, weight
    //  size, startTime, endTime, totalTime, prodAmount1stClass, prodAmount2ndClass, notes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productionId;
    private String supervisorName;
    private String nameOfReporter;
    @Enumerated(value = EnumType.STRING)
    private Shift shift;
    private LocalDate productionDate;
    private Integer cardNumber;
    private String coilNumber;
    private Integer weight;
    private String size;
    private BigDecimal startTime;
    private BigDecimal endTime;
    private BigDecimal totalTime;
    private Integer prodAmount1stClass;
    private Integer prodAmount2ndClass;
    private String notes;

    /**
     * Convert from given ProductionStaff entity instance to ProductionStaffDTO instance
     *
     * @return ProductionStaffDTO instance corresponding to the given ProductionStaff
     */
    public ProductionStaffDTO convertToDTO() {

        ProductionStaffDTO productionStaffDTO = new ProductionStaffDTO();

        productionStaffDTO.setProductionId(productionId);
        productionStaffDTO.setSupervisorName(supervisorName);
        productionStaffDTO.setNameOfReporter(nameOfReporter);
        productionStaffDTO.setShift(shift);
        productionStaffDTO.setProductionDate(productionDate);
        productionStaffDTO.setCardNumber(cardNumber);
        productionStaffDTO.setCoilNumber(coilNumber);
        productionStaffDTO.setWeight(weight);
        productionStaffDTO.setSize(size);
        productionStaffDTO.setStartTime(startTime);
        productionStaffDTO.setEndTime(endTime);
        productionStaffDTO.setTotalTime(totalTime);
        productionStaffDTO.setProdAmount1stClass(prodAmount1stClass);
        productionStaffDTO.setProdAmount2ndClass(prodAmount2ndClass);
        productionStaffDTO.setNotes(notes);

        return productionStaffDTO;
    }

    @Override
    public String toString() {
        return "ProductionStaffDTO{" +
                "productionId=" + productionId +
                ", supervisorName='" + supervisorName + '\'' +
                ", nameOfReporter='" + nameOfReporter + '\'' +
                ", shift=" + shift +
                ", productionDate='" + productionDate + '\'' +
                ", cardNumber=" + cardNumber +
                ", coilNumber='" + coilNumber + '\'' +
                ", weight=" + weight +
                ", size='" + size + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", totalTime=" + totalTime +
                ", prodAmount1stClass=" + prodAmount1stClass +
                ", prodAmount2ndClass=" + prodAmount2ndClass +
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

    public Integer getProdAmount1stClass() {
        return prodAmount1stClass;
    }

    public void setProdAmount1stClass(Integer prodAmount1stClass) {
        this.prodAmount1stClass = prodAmount1stClass;
    }

    public Integer getProdAmount2ndClass() {
        return prodAmount2ndClass;
    }

    public void setProdAmount2ndClass(Integer prodAmount2ndClass) {
        this.prodAmount2ndClass = prodAmount2ndClass;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
