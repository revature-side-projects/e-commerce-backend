package com.revature.models;

import javax.persistence.*;
import java.util.List;

@Entity // necessary annotation to mark this class as an entity bean
@Table(name = "`address`") // specifies database table name
//@Embeddable
public class Address {

    @Id // Identifies this column as a primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-incrementing
    @Column(name = "address_id", // defines column name in the database
            insertable = false,  // this column is not included in generated INSERT statements
            updatable = false,   // this column is not included in generated UPDATE statements
            nullable = false
            )
    private Integer addressId;

    @Column(nullable = false)
    private String street;

    @Column
    private String street2;

    @Column(length = 50)
    private String city;

    @Column(length = 2, nullable = false)
    private String state;

    @Column(length = 10, name = "postal_code")
    private String postalCode;

    @OneToMany(mappedBy = "address")
    private List<Order> orderList;

    // constructors
    public Address() { super(); } // required no-args constructor

    public Address(String street, String street2, String city, String state, String postalCode) {
        this.street = street;
        this.street2 = street2;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
    }

    // getters
    public Integer getAddressId() { return addressId; }
    public String getStreet() { return street; }
    public String getStreet2() { return street2; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getPostalCode() { return postalCode; }
//    public List<Order> getOrderList() { return orderList; }

    // setters
//    public void setAddressId(Integer addressId) { this.addressId = addressId; }
    public void setStreet(String street) { this.street = street; }
    public void setStreet2(String street2) { this.street2 = street2; }
    public void setCity(String city) { this.city = city; }
    public void setState(String state) { this.state = state; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
//    public void setOrderList(List<Order> orderList) { this.orderList = orderList; }

    @Override
    public String toString() {
        return "Address{" +
                "addressId=" + addressId +
                ", street='" + street + '\'' +
                ", street2='" + street2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", orderList.size()=" + ((orderList == null)?0:orderList.size()) +
                '}';
    }
}
