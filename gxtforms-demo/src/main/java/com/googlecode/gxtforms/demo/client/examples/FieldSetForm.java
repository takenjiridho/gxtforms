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
@Form(heading = "Field Sets Form", labelWidth = 100, fieldWidth = 150, width = 500)
public class FieldSetForm implements BeanModelTag, Serializable {

    @HiddenField(index = 0, fieldSet="Personal Info")
    private Integer id;

    @CharField(index = 1, allowBlank = false, maxLength = 20, autoValidate = false, validateOnBlur = true)
    private String name;

    @EmailField(index = 2)
    private String email;

    @PhoneField(index = 3)
    private String phone;

    @ChooseOneField(index = 4, fieldSet="Additional Info")
    private CarManufacturer car;

    @RadioField(index = 5, allowBlank = false, orientation = Orientation.VERTICAL)
    private CarManufacturer car2;

    @TextAreaField(index = 6, maxLength = 255)
    private String description;

    @DateField(index = 7)
    private Date createdAt;


    public FieldSetForm() {
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
