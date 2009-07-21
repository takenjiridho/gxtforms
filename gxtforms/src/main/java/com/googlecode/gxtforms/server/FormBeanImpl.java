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

import com.googlecode.gxtforms.annotations.CheckBoxField;
import com.googlecode.gxtforms.annotations.Form;
import com.googlecode.gxtforms.annotations.FormAnnotation;
import com.googlecode.gxtforms.annotations.HiddenField;
import com.googlecode.gxtforms.annotations.RadioField;
import com.googlecode.gxtforms.client.EnumFieldOption;
import com.googlecode.gxtforms.client.FieldConfigurationException;
import com.googlecode.gxtforms.client.FormPanelConfiguration;
import com.googlecode.gxtforms.client.config.FieldConfiguration;
import com.googlecode.gxtforms.client.config.FieldType;
import com.googlecode.gxtforms.client.config.Orientation;

public class FormBeanImpl implements FormBean {

    public List<FieldConfiguration> getFields() {
        List<FieldConfiguration> fields = new ArrayList<FieldConfiguration>();

        for (Map.Entry<Field, Annotation> fieldEntry : getFieldAnnotations().entrySet()) {
            FieldConfiguration fieldConfig = buildFieldConfiguration(fieldEntry.getKey(), fieldEntry.getValue());
            cleanDefaults(fieldConfig, fieldEntry.getKey());
            fields.add(fieldConfig);
        }

        Collections.sort(fields, new Comparator<FieldConfiguration>() {
            public int compare(FieldConfiguration field1, FieldConfiguration field2) {
                if (field1.getIndex() == field2.getIndex()) {
                    return -1;
                } else {
                    return field1.getIndex() - field2.getIndex();
                }
            }
        });

        return fields;
    }

    public FormPanelConfiguration getFormConfiguration() {
        FormPanelConfiguration config = new FormPanelConfiguration();
        Form annotation = getFormAnnotation();
        config.setAnimCollapse(annotation.animCollapse());
        config.setCollapsible(annotation.collapsible());
        config.setFrame(annotation.frame());
        config.setHideLabels(annotation.hideLabels());
        config.setLabelAlign(annotation.labelAlign());
        
        String action = annotation.action();
        if (StringUtils.isNotEmpty(action)) {
            config.setAction(action);    
        }
        
        config.setLabelWidth(annotation.labelWidth());    
        config.setFieldWidth(annotation.fieldWidth());
        config.setWidth(annotation.width());    
        
        String method = annotation.method();
        if (StringUtils.isNotEmpty(method)) {
            config.setMethod(method);    
        }
        
        String title = annotation.heading();
        if (StringUtils.isNotEmpty(title)) {
            config.setHeading(title);    
        }
        
        return config;
    }
    
    public Form getFormAnnotation() {
        for (Annotation annotation : getClass().getAnnotations()) {
            if (annotation instanceof Form) {
                return (Form) annotation;
            }
        }
        throw new RuntimeException("no FormAnnotation defined for: " + getClass().getName());
    }
    

    public void cleanDefaults(FieldConfiguration fieldConfiguration, Field field) {
        if (StringUtils.isEmpty(fieldConfiguration.getName())) {
            fieldConfiguration.setName(field.getName());
        }

        if (StringUtils.isEmpty(fieldConfiguration.getFieldLabel())) {
            fieldConfiguration.setFieldLabel(WordUtils.capitalize(fieldConfiguration.getName()));
        }
    }

    @SuppressWarnings("unchecked")
    public FieldConfiguration buildFieldConfiguration(Field field, Annotation formField) {
        FieldConfiguration config = new FieldConfiguration();
        config.setName((String) invoke("name", formField));
        config.setFieldType((FieldType) invoke("fieldType", formField));
        if (!(formField instanceof HiddenField)) {

            if (!(formField instanceof CheckBoxField) && !(formField instanceof RadioField)) {
                config.setEmptyText((String) invoke("emptyText", formField));
            }

            if (formField instanceof RadioField) {
                config.setOrientation((Orientation) invoke("orientation", formField));
            }

            config.setFieldLabel((String) invoke("fieldLabel", formField));
            config.setHideLabel((Boolean) invoke("hideLabel", formField));
            config.setReadOnly((Boolean) invoke("readOnly", formField));
            config.setAllowBlank((Boolean) invoke("allowBlank", formField));
            config.setLabelSeparator((String) invoke("labelSeparator", formField));
            config.setLabelStyle((String) invoke("labelStyle", formField));
            config.setMaxLength((Integer) invoke("maxLength", formField));
            config.setMessageTarget((String) invoke("messageTarget", formField));
            config.setValidateOnBlur((Boolean) invoke("validateOnBlur", formField));
            config.setValidationDelay((Integer) invoke("validationDelay", formField));
            config.setAutoValidate((Boolean) invoke("autoValidate", formField));
        }
        config.setIndex((Integer) invoke("index", formField));
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
            if (annot.annotationType().getAnnotation(FormAnnotation.class) != null) {
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
