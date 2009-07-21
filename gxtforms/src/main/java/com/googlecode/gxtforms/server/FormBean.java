package com.googlecode.gxtforms.server;

import java.util.List;

import com.googlecode.gxtforms.client.FormPanelConfiguration;
import com.googlecode.gxtforms.client.config.FieldConfiguration;

public interface FormBean {

    List<FieldConfiguration> getFields();
    
    FormPanelConfiguration getFormConfiguration();
    
}
