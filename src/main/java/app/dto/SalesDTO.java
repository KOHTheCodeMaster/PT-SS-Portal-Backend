package app.dto;

import app.enums.Payment;

import java.time.LocalDate;

public class SalesDTO {

    // Sales_Id, Buyer Name, Buyer Phone number, Buyer Address, Item Types, Colour, Item Size, Date, Amount of Item, Payment, Sales Name
    private Integer salesId;
    private String buyerName;
    private String buyerPhoneNumber;
    private String buyerAddress;
    private String itemType;
    private String colour;
    private String itemSize;
    private LocalDate salesDate;
    private Integer amountOfItem;
    private Payment payment;
    private String salesName;

    @Override
    public String toString() {
        return "SalesDTO{" +
                "salesId=" + salesId +
                ", buyerName='" + buyerName + '\'' +
                ", buyerPhoneNumber='" + buyerPhoneNumber + '\'' +
                ", buyerAddress='" + buyerAddress + '\'' +
                ", itemType='" + itemType + '\'' +
                ", colour='" + colour + '\'' +
                ", itemSize='" + itemSize + '\'' +
                ", salesDate=" + salesDate +
                ", amountOfItem=" + amountOfItem +
                ", payment=" + payment +
                ", salesName='" + salesName + '\'' +
                '}';
    }

    public Integer getSalesId() {
        return salesId;
    }

    public void setSalesId(Integer salesId) {
        this.salesId = salesId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerPhoneNumber() {
        return buyerPhoneNumber;
    }

    public void setBuyerPhoneNumber(String buyerPhoneNumber) {
        this.buyerPhoneNumber = buyerPhoneNumber;
    }

    public String getBuyerAddress() {
        return buyerAddress;
    }

    public void setBuyerAddress(String buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getItemSize() {
        return itemSize;
    }

    public void setItemSize(String itemSize) {
        this.itemSize = itemSize;
    }

    public LocalDate getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(LocalDate salesDate) {
        this.salesDate = salesDate;
    }

    public Integer getAmountOfItem() {
        return amountOfItem;
    }

    public void setAmountOfItem(Integer amountOfItem) {
        this.amountOfItem = amountOfItem;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public String getSalesName() {
        return salesName;
    }

    public void setSalesName(String salesName) {
        this.salesName = salesName;
    }
}
