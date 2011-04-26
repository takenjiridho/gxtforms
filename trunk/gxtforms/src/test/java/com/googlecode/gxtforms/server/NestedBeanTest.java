package com.googlecode.gxtforms.server;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.googlecode.gxtforms.annotations.CharField;
import com.googlecode.gxtforms.annotations.NestedBeanField;
import com.googlecode.gxtforms.client.config.FieldConfiguration;

public class NestedBeanTest {

    @Test
    public void nestedBeanFieldSet() {
        List<FieldConfiguration> fields = new Bean2().getFields();
        debugFields(fields);
        
        FieldConfiguration char1 = fields.get(0);
        assertEquals("char1", char1.getName());
        assertEquals("char1", char1.getFieldSet());
        assertEquals(1, char1.getIndex());

        FieldConfiguration nested1 = fields.get(1);
        assertEquals("nestedBean.nested1", nested1.getName());
        assertEquals("Nested Bean", nested1.getFieldSet());
        assertEquals(2, nested1.getIndex());
        
        FieldConfiguration nested2 = fields.get(2);
        assertEquals("nestedBean.nested2", nested2.getName());
        assertEquals(3, nested2.getIndex());
               
        FieldConfiguration nested3 = fields.get(3);
        assertEquals("nestedBean.nested3", nested3.getName());
        assertEquals(4, nested3.getIndex());
        
        FieldConfiguration char2 = fields.get(4);
        assertEquals("char2", char2.getName());
        assertEquals("char2", char2.getFieldSet());
        assertEquals(5, char2.getIndex());
        
        FieldConfiguration nested4 = fields.get(5);
        assertEquals("nestedBean2.nested1", nested4.getName());
        assertEquals("Nested Bean 2", nested4.getFieldSet());
        assertEquals(6, nested4.getIndex());
        
        FieldConfiguration nested5 = fields.get(6);
        assertEquals("nestedBean2.nested2", nested5.getName());
        assertEquals(7, nested5.getIndex());

        FieldConfiguration nested6 = fields.get(7);
        assertEquals("nestedBean2.nested3", nested6.getName());
        assertEquals(8, nested6.getIndex());
        
        FieldConfiguration char3 = fields.get(8);
        assertEquals("char3", char3.getName());
        assertEquals("char3", char3.getFieldSet());
        assertEquals(9, char3.getIndex());
        
        FieldConfiguration char4 = fields.get(9);
        assertEquals("char4", char4.getName());
        assertEquals("char3", char4.getFieldSet());
        assertEquals(10, char4.getIndex());
        
        
    }
    
    private void debugFields(List<FieldConfiguration> fields) {
        for (FieldConfiguration fieldConfiguration : fields) {
            System.out.println(fieldConfiguration.getName() + ":" + fieldConfiguration.getIndex());
        }
    }
    
}

class Bean2 extends Bean {

    @CharField(index = 6, fieldSet = "char3")
    String char4;
    
}

class Bean extends FormBeanImpl {

    @CharField(index = 1, fieldSet = "char1")
    String char1;
    
    @NestedBeanField(fieldSet = "Nested Bean", index = 2)
    NestedBean nestedBean;
    
    @CharField(index = 3, fieldSet = "char2")
    String char2;

    @NestedBeanField(fieldSet = "Nested Bean 2", index = 4)
    NestedBean nestedBean2;
    
    @CharField(index = 5, fieldSet = "char3")
    String char3;
    
}

class NestedBean {
    @CharField(index = 1)
    String nested1;
    
    @CharField(index = 2)
    String nested2;
    
    @CharField(index = 3)
    String nested3;
    
}
