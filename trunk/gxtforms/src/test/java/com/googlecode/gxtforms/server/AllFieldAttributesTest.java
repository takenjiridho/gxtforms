package com.googlecode.gxtforms.server;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.googlecode.gxtforms.annotations.CharField;
import com.googlecode.gxtforms.client.config.FieldConfiguration;
import com.googlecode.gxtforms.client.config.FieldType;

public class AllFieldAttributesTest {

    @Test
    public void testAllAttributes() {
        FieldConfiguration config = new FormA().getFields().get(0);
        assertEquals(true, config.isAutoValidate());
        assertEquals(false, config.isValidateOnBlur());
        assertEquals(true, config.isReadOnly());
        assertEquals(true, config.isHideLabel());
        assertEquals(false, config.isAllowBlank());
        assertEquals("nameXyz", config.getName());
        assertEquals(3, config.getIndex());
        assertEquals("empty", config.getEmptyText());
        assertEquals("label", config.getFieldLabel());
        assertEquals(FieldType.TextArea, config.getFieldType());
        assertEquals(2000, config.getValidationDelay());
        assertEquals("-", config.getLabelSeparator());
        assertEquals("target", config.getMessageTarget());
        assertEquals("style", config.getLabelStyle());
        assertEquals(50, config.getMaxLength());
    }

}

class FormA extends FormImpl {

    @SuppressWarnings("unused")
    @CharField(autoValidate = true, emptyText = "empty", fieldLabel = "label", index = 3, hideLabel = true, fieldType = FieldType.TextArea, 
            labelSeparator = "-", labelStyle = "style", messageTarget = "target", name = "nameXyz", readOnly = true, allowBlank = false, 
            validateOnBlur = false, validationDelay = 2000, maxLength = 50)
    private String charField;

}