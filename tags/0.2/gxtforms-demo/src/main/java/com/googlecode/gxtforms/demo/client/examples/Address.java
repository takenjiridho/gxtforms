package com.googlecode.gxtforms.demo.client.examples;

import java.io.Serializable;

import com.extjs.gxt.ui.client.data.BeanModelTag;
import com.googlecode.gxtforms.annotations.CharField;
import com.googlecode.gxtforms.annotations.ChooseOneField;


@SuppressWarnings("serial")
public class Address implements BeanModelTag, Serializable {

    @CharField(index = 1, fieldLabel = "Address 1")
    String address1;

    @CharField(index = 2, fieldLabel = "Address 2")
    String address2;

    @ChooseOneField(index = 3, fieldLabel = "State")
    State state;

    @CharField(index = 4, fieldLabel = "ZIP Code", maxLength = 5)
    String zipCode;

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

}
