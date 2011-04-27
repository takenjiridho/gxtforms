package com.googlecode.gxtforms.server;

import java.util.List;

import static junit.framework.Assert.*;

import org.junit.Test;

import com.googlecode.gxtforms.annotations.CharField;
import com.googlecode.gxtforms.annotations.NestedBeanField;
import com.googlecode.gxtforms.client.config.FieldConfiguration;

public class CascadingTests {

    @Test
    public void parentOverride() {
        List<FieldConfiguration> fields = new CBean().getFields();
        
        FieldConfiguration field1 = fields.get(0);
        assertEquals("Important",field1.getFieldSet());
        
        FieldConfiguration field2 = fields.get(1);
        assertEquals(true, field2.isHideLabel());
        
        FieldConfiguration field3 = fields.get(2);
        assertEquals("nested style", field3.getLabelStyle());

        FieldConfiguration field4 = fields.get(3);
        assertEquals("Nested Bean 2", field4.getFieldSet());
        
        //FieldConfiguration field5 = fields.get(4);
        //FieldConfiguration field6 = fields.get(5);
        
        
        FieldConfiguration field7 = fields.get(6);
        assertEquals("Not Important", field7.getFieldSet());
        
//        FieldConfiguration field8 = fields.get(1);
//        FieldConfiguration field9 = fields.get(2);
        FieldConfiguration field10 = fields.get(9);
        assertEquals("Nested Bean 4", field10.getFieldSet());
        
//        FieldConfiguration field11 = fields.get(4);
//        FieldConfiguration field12 = fields.get(5);

    }

}

class CBean extends FormBeanImpl {

    @NestedBeanField(fieldSet = "Nested Bean 1", index = 1)
    CNestedBean1 nestedBean1;

    @NestedBeanField(fieldSet = "Nested Bean 2", index = 2, important = true)
    CNestedBean1 nestedBean2;

    @NestedBeanField(index = 3, readOnly = true)  
    CNestedBean2 nestedBean3;

    @NestedBeanField(fieldSet = "Nested Bean 4", index = 4)
    CNestedBean2 nestedBean4;
    
}

class CNestedBean1 {
    
    @CharField(index = 1, fieldSet = "Important", important = true)
    String nested1a;

    @CharField(index = 2, hideLabel = true)
    String nested2a;

    @CharField(index = 3, labelStyle = "nested style")
    String nested3a;

}

class CNestedBean2 {
    
    @CharField(index = 1, fieldSet = "Not Important")
    String nested1b;

    @CharField(index = 2, readOnly = false)
    String nested2b;

    @CharField(index = 3)
    String nested3b;

}

