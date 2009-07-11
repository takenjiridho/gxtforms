package com.googlecode.gxtforms.demo.client;

import java.io.Serializable;

import com.googlecode.gxtforms.annotations.CharField;
import com.googlecode.gxtforms.annotations.ChooseOneField;
import com.googlecode.gxtforms.annotations.DateField;
import com.googlecode.gxtforms.annotations.HiddenField;
import com.googlecode.gxtforms.annotations.TextAreaField;

@SuppressWarnings("serial")
public class SimpleForm implements Serializable {

    @HiddenField
    private Integer id;

    @CharField(order = 1, required = true)
    private String name;

    @ChooseOneField(order = 2)
    private CarManufacturer car;

    @TextAreaField(order = 3)
    private String description;

    @DateField(order = 4)
    private String createdAt;

    public SimpleForm() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CarManufacturer getCar() {
        return car;
    }

    public void setCar(CarManufacturer car) {
        this.car = car;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
