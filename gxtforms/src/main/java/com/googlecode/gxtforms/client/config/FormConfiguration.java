package com.googlecode.gxtforms.client.config;

import java.util.List;

public class FormConfiguration {

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
