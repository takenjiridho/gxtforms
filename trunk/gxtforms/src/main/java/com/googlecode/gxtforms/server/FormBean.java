package com.googlecode.gxtforms.server;

import java.util.List;

import com.googlecode.gxtforms.client.config.FieldConfiguration;
import com.googlecode.gxtforms.client.form.FormPanelConfiguration;

public interface FormBean {

    List<FieldConfiguration> getFields();
    
    FormPanelConfiguration getFormConfiguration();
    
}
