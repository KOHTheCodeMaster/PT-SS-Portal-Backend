package app.entity;

import app.dto.SalesDTO;
import app.enums.Payment;
import app.enums.Status;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Sales {

    // Sales_Id, Buyer Name, Buyer Phone number, Buyer Address, Item Types, Colour, Item Size, Date, Amount of Item, Payment, Sales Name

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer salesId;
    private String buyerName;
    private String buyerPhoneNumber;
    private String buyerAddress;
    private String itemType;
    private String colour;
    private String itemSize;
    private LocalDate salesDate;
    private Integer amountOfItem;
    @Enumerated(value = EnumType.STRING)
    private Payment payment;
    @Enumerated(value = EnumType.STRING)
    private Status status;
    private String salesName;

    /**
     * Convert from given Sales entity instance to SalesDTO instance
     *
     * @return SalesDTO instance corresponding to the given Sales
     */
    public SalesDTO convertToDTO() {

        SalesDTO salesDTO = new SalesDTO();

        salesDTO.setSalesId(this.getSalesId());
        salesDTO.setBuyerName(this.getBuyerName());
        salesDTO.setBuyerPhoneNumber(this.getBuyerPhoneNumber());
        salesDTO.setBuyerAddress(this.getBuyerAddress());
        salesDTO.setItemType(this.getItemType());
        salesDTO.setColour(this.getColour());
        salesDTO.setItemSize(this.getItemSize());
        salesDTO.setSalesDate(this.getSalesDate());
        salesDTO.setAmountOfItem(this.getAmountOfItem());
        salesDTO.setPayment(this.getPayment());
        salesDTO.setStatus(this.getStatus());
        salesDTO.setSalesName(this.getSalesName());

        return salesDTO;
    }

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
                ", status='" + status + '\'' +
                ", salesName='" + salesName + '\'' +
                '}';
    }

    /**
     * Generated primary keys create a problem for the implementation of your equals() and hashCode() methods.
     * That’s because the primary key value gets set when the entity gets persisted.
     * So, your entity object can exist with and without a primary key value.
     * <p>
     * Entity instance which has not been persisted yet in the DB may or may not have initialized primary key
     * Therefore, to ensure the instance have same hashCode before & after it is managed by entity manager
     * An arbitrary Prime number 31 is used as hash code for all those instances whose primary key has not
     * been set. This negatively affects the performance of very huge Sets and Maps because they put
     * all objects into the same hash bucket.
     * <p>
     * Doubt:
     * result = 31 * result + ((this.getCustomerId() == null) ? 0 : this.getCustomerId().hashCode());
     * the above statement still leads to 2 different hash codes for the same entity instance
     * before & after its persisted.
     *
     * @return unique hash code for entity instance (unique row in db)
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.salesId == null) ? 0 : this.salesId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Sales)) return false;
        Sales other = (Sales) obj;
        return Objects.equals(this.salesId, other.salesId);
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getSalesName() {
        return salesName;
    }

    public void setSalesName(String salesName) {
        this.salesName = salesName;
    }
}
