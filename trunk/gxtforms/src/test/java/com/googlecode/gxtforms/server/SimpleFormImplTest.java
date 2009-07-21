package com.googlecode.gxtforms.server;

import static org.junit.Assert.assertEquals;

import java.util.EnumSet;
import java.util.List;

import org.junit.Test;

import com.googlecode.gxtforms.annotations.CharField;
import com.googlecode.gxtforms.annotations.ChooseOneField;
import com.googlecode.gxtforms.annotations.RadioField;
import com.googlecode.gxtforms.client.FieldConfigurationException;
import com.googlecode.gxtforms.client.FieldOption;
import com.googlecode.gxtforms.client.config.FieldConfiguration;
import com.googlecode.gxtforms.client.config.FieldType;

public class SimpleFormImplTest {

    @Test
    public void testGetFormFields() {
        List<FieldConfiguration> fields = new SimpleFormDemo().getFields();
        assertEquals(2, fields.size());
        assertEquals("name", fields.get(0).getName());
        assertEquals("Name", fields.get(0).getFieldLabel());
        assertEquals(FieldType.Text, fields.get(0).getFieldType());

        assertEquals("age", fields.get(1).getName());
        assertEquals("Age", fields.get(1).getFieldLabel());
        assertEquals(FieldType.SelectOne, fields.get(1).getFieldType());
    }

    @Test
    public void testCustomNameAndLabel() {
        List<FieldConfiguration> fields = new CustomFormDemo().getFields();
        assertEquals(2, fields.size());
        assertEquals("username", fields.get(0).getName());
        assertEquals("Login", fields.get(0).getFieldLabel());
        assertEquals(FieldType.Text, fields.get(0).getFieldType());
        assertEquals("yearsOfAge", fields.get(1).getName());
        assertEquals("Years of Age", fields.get(1).getFieldLabel());
        assertEquals(FieldType.SelectOne, fields.get(1).getFieldType());
    }

    @Test
    public void testNoOrderForm() {
        assertEquals(3, new NoOrderForm().getFields().size());
    }

    @Test(expected = FieldConfigurationException.class)
    public void test2FormFields() {
        new InvalidForm().getFields();
    }

    @Test()
    public void testEnumField() {
        
        for (FieldConfiguration fieldConfig : new EnumForm().getFields()) {
            List<? extends FieldOption<?>> options = fieldConfig.getOptions();
            assertEquals(4, options.size());
            
            EnumExample[] enums = EnumSet.allOf(EnumExample.class).toArray(new EnumExample[0]);
            
            for (int i = 0; i < options.size(); i++) {
                assertEquals(enums[i], options.get(i).getValue());
                assertEquals(enums[i].name(), options.get(i).getLabel());
            }
        }
        
    }

}

@SuppressWarnings("unused")
class SimpleFormDemo extends FormBeanImpl {

    @CharField(index = 1)
    private String name;

    @ChooseOneField(index = 2)
    private int age;

}

@SuppressWarnings("unused")
class CustomFormDemo extends FormBeanImpl {

    @CharField(fieldLabel = "Login", name = "username", index = 1)
    private String name;

    @ChooseOneField(fieldLabel = "Years of Age", name = "yearsOfAge", index = 2)
    private int age;

}

@SuppressWarnings("unused")
class NoOrderForm extends FormBeanImpl {

    @CharField
    private String a;

    @CharField
    private String b;

    @CharField
    private String c;

}

@SuppressWarnings("unused")
class InvalidForm extends FormBeanImpl {

    @CharField
    @ChooseOneField
    private String a;
}

@SuppressWarnings("unused")
class EnumForm extends FormBeanImpl {

    @ChooseOneField(index = 1)
    private EnumExample test;
    
    @RadioField(index = 2)
    private EnumExample test2;

}
