package app.user.entity;

import app.user.dto.UserRole;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;
    private String name;
    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;
    private String emailId;
    private String password;
    private String phoneNumber;
    private String address;
    private Integer picId;

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", userRole=" + userRole +
                ", emailId='" + emailId + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", picId=" + picId +
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
        result = prime * result + ((this.getUserId() == null) ? 0 : this.getUserId().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof User)) return false;
        User other = (User) obj;
        return Objects.equals(getUserId(), other.getUserId());
    }

/*
    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (this.getCustomerId() == null) {
			if (other.getCustomerId() != null)
				return false;
		}
		else if (!this.getCustomerId().equals(other.getCustomerId()))
			return false;
		return true;
	}
 */

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPicId() {
        return picId;
    }

    public void setPicId(Integer picId) {
        this.picId = picId;
    }
}
