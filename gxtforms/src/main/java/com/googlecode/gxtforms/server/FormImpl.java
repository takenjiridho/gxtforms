package com.googlecode.gxtforms.server;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

import com.googlecode.gxtforms.annotations.FormField;
import com.googlecode.gxtforms.client.config.FieldConfiguration;
import com.googlecode.gxtforms.client.config.FieldType;

public class FormImpl implements Form {

    public List<FieldConfiguration> getFields() {
        List<FieldConfiguration> fields = new ArrayList<FieldConfiguration>();

        for (Map.Entry<Field,Annotation> fieldEntry : getFieldAnnotations().entrySet()) {
            FieldConfiguration fieldConfig = buildFieldConfiguration(fieldEntry.getValue());
            cleanDefaults(fieldConfig, fieldEntry.getKey());
            fields.add(fieldConfig);
        }

        Collections.sort(fields, new Comparator<FieldConfiguration>() {
            public int compare(FieldConfiguration field1, FieldConfiguration field2) {
                if (field1.getOrder() == field2.getOrder()) {
                    return -1;
                } else {
                    return field1.getOrder() - field2.getOrder();
                }
            }
        });

        return fields;
    }
    
    public void cleanDefaults(FieldConfiguration fieldConfiguration, Field field) {
        if (StringUtils.isEmpty(fieldConfiguration.getName())) {
            fieldConfiguration.setName(field.getName());
        }
        
        if (StringUtils.isEmpty(fieldConfiguration.getLabel())) {
            fieldConfiguration.setLabel(WordUtils.capitalize(fieldConfiguration.getName()));
        }
    }
    

    public FieldConfiguration buildFieldConfiguration(Annotation formField) {
        FieldConfiguration config = new FieldConfiguration();
        config.setName((String) invoke("name", formField));
        config.setType((FieldType) invoke("fieldType", formField));
        config.setLabel((String) invoke("label", formField));
        config.setOrder((Integer) invoke("order", formField));
        
        
        return config;
    }

    public Object invoke(String methodName, Object o) {
        try {
            return o.getClass().getMethod(methodName, (Class<?>[]) null).invoke(o, (Object[]) null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Field, Annotation> getFieldAnnotations() {
        Map<Field, Annotation> allFieldAnnotations = new HashMap<Field, Annotation>();
        Field[] fields = getMemberFields();
        for (Field field : fields) {
            List<Annotation> aFieldsAnnotations = getFormFields(field.getAnnotations());
            if (aFieldsAnnotations.size() > 0) {
                allFieldAnnotations.put(field, aFieldsAnnotations.get(0));
            }
        }
        return allFieldAnnotations;
    }

    public Field[] getMemberFields() {
        return getClass().getDeclaredFields();
    }

    public List<Annotation> getFormFields(Annotation[] annots) {
        List<Annotation> annotations = new ArrayList<Annotation>();
        for (Annotation annot : annots) {
            if (annot.annotationType().getAnnotation(FormField.class) != null) {
                annotations.add(annot);
            }
        }
        assert annotations.size() < 2; // only zero or 1 FormField annotation is
                                       // allowed
        return annotations;
    }
}
