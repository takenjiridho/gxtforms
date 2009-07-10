package com.googlecode.gxtforms.demo.client;

import java.io.Serializable;

import com.googlecode.gxtforms.annotations.CharField;
import com.googlecode.gxtforms.annotations.ChooseOneField;

@SuppressWarnings("serial")
public class SimpleForm implements Serializable {

    @CharField(order = 1, required = true)
    private String name;

    @ChooseOneField(order = 2)
    private String car;

    public SimpleForm() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

}
