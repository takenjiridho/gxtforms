package com.googlecode.gxtforms.server;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.googlecode.gxtforms.annotations.Form;
import com.googlecode.gxtforms.client.config.LabelAlign;
import com.googlecode.gxtforms.client.form.FormPanelConfiguration;

public class FormConfigurationTest {

    @Test
    public void testBuildFormConfig() {
        FormPanelConfiguration config = new SampleFormConfig().getFormConfiguration();
        assertEquals("my title", config.getHeading());
        assertEquals("myAction", config.getAction());
        assertEquals(false, config.isAnimCollapse());
        assertEquals(true, config.isCollapsible());
        assertEquals(false, config.isFrame());
        assertEquals(true, config.isHideLabels());
        assertEquals("POST", config.getMethod());
        assertEquals(255, config.getFieldWidth());
        assertEquals(50, config.getLabelWidth());
        assertEquals(LabelAlign.TOP, config.getLabelAlign());
        assertEquals(500, config.getWidth());
    }
    
}


@Form(heading = "my title", action = "myAction", animCollapse= false, collapsible = true, fieldWidth = 255, labelWidth = 50, frame = false, 
        hideLabels = true, method = "POST", labelAlign= LabelAlign.TOP, width = 500)
class SampleFormConfig extends FormBeanImpl {
    
}
