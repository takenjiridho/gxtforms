package com.googlecode.gxtforms.demo.client.examples;

import java.io.Serializable;

import com.extjs.gxt.ui.client.data.BeanModelTag;
import com.googlecode.gxtforms.annotations.CharField;
import com.googlecode.gxtforms.annotations.Form;
import com.googlecode.gxtforms.annotations.TextAreaField;

@SuppressWarnings("serial")
@Form(heading = "Defaults Form", labelWidth = 100, fieldWidth = 150, width = 500, animCollapse = true)
public class DefaultsForm implements BeanModelTag, Serializable {

    @CharField(index = 1, allowBlank = false, maxLength = 20, autoValidate = false, validateOnBlur = true, styleName="custom-style")
    private String name;

    @TextAreaField(index = 4, maxLength = 255, enabled = false)
    private String description;

    public DefaultsForm() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
