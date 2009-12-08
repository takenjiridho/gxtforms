package com.googlecode.gxtforms.demo.client.examples;

import java.io.Serializable;

import com.extjs.gxt.ui.client.data.BeanModelTag;
import com.googlecode.gxtforms.annotations.CharField;
import com.googlecode.gxtforms.annotations.ChooseOneField;
import com.googlecode.gxtforms.annotations.Form;
import com.googlecode.gxtforms.annotations.NestedBeanField;

@SuppressWarnings("serial")
@Form(heading = "Nested Beans Form", labelWidth = 100, fieldWidth = 150, width = 500)
public class NestedBeansForm implements BeanModelTag, Serializable {

    @CharField(index = 1, fieldLabel = "First Name", fieldSet = "Personal Info")
    String firstName;

    @CharField(index = 2, fieldLabel = "Last Name")
    String lastName;

    @NestedBeanField(index = 3, fieldSet = "Address")
    Address address = new Address();

    @CharField(index = 4, fieldSet = "Car Info (of course)", fieldLabel = "Car's Nickname")
    String carNick;

    @ChooseOneField(index = 5, fieldLabel = "Car's Manufacturer")
    CarManufacturer carType;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getCarNick() {
        return carNick;
    }

    public void setCarNick(String carNick) {
        this.carNick = carNick;
    }

    public CarManufacturer getCarType() {
        return carType;
    }

    public void setCarType(CarManufacturer carType) {
        this.carType = carType;
    }

}
