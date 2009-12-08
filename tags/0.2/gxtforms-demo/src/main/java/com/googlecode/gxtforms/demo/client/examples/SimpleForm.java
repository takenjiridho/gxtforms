package com.googlecode.gxtforms.demo.client.examples;

import java.io.Serializable;
import java.util.Date;

import com.extjs.gxt.ui.client.data.BeanModelTag;
import com.googlecode.gxtforms.annotations.CharField;
import com.googlecode.gxtforms.annotations.ChooseOneField;
import com.googlecode.gxtforms.annotations.DateField;
import com.googlecode.gxtforms.annotations.EmailField;
import com.googlecode.gxtforms.annotations.Form;
import com.googlecode.gxtforms.annotations.HiddenField;
import com.googlecode.gxtforms.annotations.PhoneField;
import com.googlecode.gxtforms.annotations.RadioField;
import com.googlecode.gxtforms.annotations.TextAreaField;
import com.googlecode.gxtforms.client.config.Orientation;

@SuppressWarnings("serial")
@Form(heading = "Simple Form", labelWidth = 100, fieldWidth = 150, width = 500)
public class SimpleForm implements BeanModelTag, Serializable {

    @HiddenField
    private Integer id;

    @CharField(index = 1, allowBlank = false, maxLength = 20, autoValidate = false, validateOnBlur = true)
    private String name;

    @ChooseOneField(index = 2)
    private CarManufacturer car;

    @RadioField(index = 3, allowBlank = false, orientation = Orientation.VERTICAL)
    private CarManufacturer car2;

    @TextAreaField(index = 4, maxLength = 255)
    private String description;

    @DateField(index = 5)
    private Date createdAt;

    @EmailField(index = 6)
    private String email;

    @PhoneField(index = 7)
    private String phone;

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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public CarManufacturer getCar2() {
        return car2;
    }

    public void setCar2(CarManufacturer car2) {
        this.car2 = car2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
