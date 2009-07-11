package com.googlecode.gxtforms.server;

import static org.junit.Assert.assertEquals;

import java.util.EnumSet;
import java.util.List;

import org.junit.Test;

import com.googlecode.gxtforms.annotations.CharField;
import com.googlecode.gxtforms.annotations.ChooseOneField;
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
        assertEquals("Name", fields.get(0).getLabel());
        assertEquals(FieldType.Text, fields.get(0).getType());

        assertEquals("age", fields.get(1).getName());
        assertEquals("Age", fields.get(1).getLabel());
        assertEquals(FieldType.SelectOne, fields.get(1).getType());
    }

    @Test
    public void testCustomNameAndLabel() {
        List<FieldConfiguration> fields = new CustomFormDemo().getFields();
        assertEquals(2, fields.size());
        assertEquals("username", fields.get(0).getName());
        assertEquals("Login", fields.get(0).getLabel());
        assertEquals(FieldType.Text, fields.get(0).getType());
        assertEquals("yearsOfAge", fields.get(1).getName());
        assertEquals("Years of Age", fields.get(1).getLabel());
        assertEquals(FieldType.SelectOne, fields.get(1).getType());
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
        FieldConfiguration fieldConfig = new EnumForm().getFields().get(0);
        List<? extends FieldOption<?>> options = fieldConfig.getOptions();
        assertEquals(4, options.size());
        
        TestEnum[] enums = EnumSet.allOf(TestEnum.class).toArray(new TestEnum[0]);
        
        for (int i = 0; i < options.size(); i++) {
            assertEquals(enums[i], options.get(i).getValue());
            assertEquals(enums[i].name(), options.get(i).getLabel());
        }
        
    }

}

@SuppressWarnings("unused")
class SimpleFormDemo extends FormImpl {

    @CharField(order = 1)
    private String name;

    @ChooseOneField(order = 2)
    private int age;

}

@SuppressWarnings("unused")
class CustomFormDemo extends FormImpl {

    @CharField(label = "Login", name = "username", order = 1)
    private String name;

    @ChooseOneField(label = "Years of Age", name = "yearsOfAge", order = 2)
    private int age;

}

@SuppressWarnings("unused")
class NoOrderForm extends FormImpl {

    @CharField
    private String a;

    @CharField
    private String b;

    @CharField
    private String c;

}

@SuppressWarnings("unused")
class InvalidForm extends FormImpl {

    @CharField
    @ChooseOneField
    private String a;
}

@SuppressWarnings("unused")
class EnumForm extends FormImpl {

    @ChooseOneField
    private TestEnum test;

}
