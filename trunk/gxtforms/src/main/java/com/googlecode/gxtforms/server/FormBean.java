package com.googlecode.gxtforms.server;

import java.lang.annotation.Annotation;
import java.util.List;

import com.googlecode.gxtforms.client.config.FieldConfiguration;
import com.googlecode.gxtforms.client.form.FormPanelConfiguration;

public interface FormBean {

    /**
     * Returns the configuration objects needed to render fields. The list's
     * order is determined by annotation index values.
     * 
     * @return
     */
    List<FieldConfiguration> getFields();
    
    /**
     * Returns the configuration objects needed to render fields. The list's
     * order is determined by annotation index values.  The "cascading" attributes of the parent have priority.
     * 
     * @return
     */
    List<FieldConfiguration> getFields(Annotation parent);
    

    /**
     * Returns the configuration needed to render a form panel.
     * 
     * @return
     */
    FormPanelConfiguration getFormConfiguration();

}
