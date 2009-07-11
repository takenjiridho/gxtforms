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
import com.googlecode.gxtforms.annotations.HiddenField;
import com.googlecode.gxtforms.client.EnumFieldOption;
import com.googlecode.gxtforms.client.FieldConfigurationException;
import com.googlecode.gxtforms.client.config.FieldConfiguration;
import com.googlecode.gxtforms.client.config.FieldType;

public class FormImpl implements Form {

    public List<FieldConfiguration> getFields() {
        List<FieldConfiguration> fields = new ArrayList<FieldConfiguration>();

        for (Map.Entry<Field, Annotation> fieldEntry : getFieldAnnotations().entrySet()) {
            FieldConfiguration fieldConfig = buildFieldConfiguration(fieldEntry.getKey(), fieldEntry.getValue());
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

    @SuppressWarnings("unchecked")
    public FieldConfiguration buildFieldConfiguration(Field field, Annotation formField) {
        FieldConfiguration config = new FieldConfiguration();
        config.setName((String) invoke("name", formField));
        config.setType((FieldType) invoke("fieldType", formField));
        if (! (formField instanceof HiddenField)) {
            config.setLabel((String) invoke("label", formField));
        }
        config.setOrder((Integer) invoke("order", formField));
        if (Enum.class.isAssignableFrom(field.getType())) {
            List<EnumFieldOption> options = new ArrayList<EnumFieldOption>();
            for (Enum e : ((Class<Enum>) field.getType()).getEnumConstants()) {
                options.add(new EnumFieldOption(e, e.name()));
            }
            config.setOptions(options);
        }

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
            List<Annotation> aFieldsAnnotations = getFormFields(field, field.getAnnotations());
            if (aFieldsAnnotations.size() > 0) {
                allFieldAnnotations.put(field, aFieldsAnnotations.get(0));
            }
        }
        return allFieldAnnotations;
    }

    public Field[] getMemberFields() {
        return getClass().getDeclaredFields();
    }

    public List<Annotation> getFormFields(Field field, Annotation[] annots) {
        List<Annotation> annotations = new ArrayList<Annotation>();
        for (Annotation annot : annots) {
            if (annot.annotationType().getAnnotation(FormField.class) != null) {
                annotations.add(annot);
            }
        }
        if (annotations.size() > 1) {
            throw new FieldConfigurationException("Invalid field configuration for '" + field.getName()
                    + "'. Only 1 FormField annotation is allowed per field.");
        }
        return annotations;
    }
}
