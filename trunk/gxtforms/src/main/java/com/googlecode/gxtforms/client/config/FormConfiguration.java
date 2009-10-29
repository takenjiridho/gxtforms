package com.googlecode.gxtforms.client.config;

import java.io.Serializable;
import java.util.List;

import com.googlecode.gxtforms.client.FormPanelConfiguration;

@SuppressWarnings("serial")
public class FormConfiguration implements Serializable {

    List<FieldConfiguration> fieldConfigurations;
    FormPanelConfiguration formPanelConfiguration;

    public FormConfiguration() {
    }

    public List<FieldConfiguration> getFieldConfigurations() {
        return fieldConfigurations;
    }

    public void setFieldConfigurations(List<FieldConfiguration> fieldConfigurations) {
        this.fieldConfigurations = fieldConfigurations;
    }
    
    public FieldConfiguration getFieldConfiguration(String name) {
        for (FieldConfiguration config : fieldConfigurations) {
            if (config.getName().equals(name)) {
                return config;
            }
        }
        return null;
    }

    public FormPanelConfiguration getFormPanelConfiguration() {
        return formPanelConfiguration;
    }

    public void setFormPanelConfiguration(FormPanelConfiguration formPanelConfiguration) {
        this.formPanelConfiguration = formPanelConfiguration;
    }

}
