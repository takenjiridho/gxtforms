package com.googlecode.gxtforms.client.config;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class FormConfiguration implements Serializable {

    List<FieldConfiguration> fieldConfigurations;

    public FormConfiguration() {
    }

    public List<FieldConfiguration> getFieldConfigurations() {
        return fieldConfigurations;
    }

    public void setFieldConfigurations(List<FieldConfiguration> fieldConfigurations) {
        this.fieldConfigurations = fieldConfigurations;
    }

}
