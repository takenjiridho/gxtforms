package com.googlecode.gxtforms.server;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

import com.googlecode.gxtforms.annotations.CheckBoxField;
import com.googlecode.gxtforms.annotations.Form;
import com.googlecode.gxtforms.annotations.FormAnnotation;
import com.googlecode.gxtforms.annotations.HiddenField;
import com.googlecode.gxtforms.annotations.NestedBeanField;
import com.googlecode.gxtforms.annotations.RadioField;
import com.googlecode.gxtforms.client.config.FieldConfiguration;
import com.googlecode.gxtforms.client.config.FieldType;
import com.googlecode.gxtforms.client.config.Orientation;
import com.googlecode.gxtforms.client.config.RegexWithMessage;
import com.googlecode.gxtforms.client.exceptions.FieldConfigurationException;
import com.googlecode.gxtforms.client.field.EnumFieldOption;
import com.googlecode.gxtforms.client.form.FormPanelConfiguration;
import com.googlecode.gxtforms.validators.RegExValidator;
import com.googlecode.gxtforms.validators.Validator;

public class FormBeanImpl implements FormBean {

    public List<FieldConfiguration> getFields() {
        List<FieldConfiguration> fields = new ArrayList<FieldConfiguration>();

        // process non-nested attributes
        Map<Field, Annotation> fieldAnnotations = getFieldAnnotations();
        for (Map.Entry<Field, Annotation> fieldEntry : fieldAnnotations.entrySet()) {
            Annotation annotation = fieldEntry.getValue();
            Field field = fieldEntry.getKey();
            if (!(annotation instanceof NestedBeanField)) {
                fields.add(buildFieldConfiguration(field, annotation));
            }
        }

        // process nested beans
        int indexModifier = 0;
        List<FieldConfiguration> nestedFields = new ArrayList<FieldConfiguration>();
        for (Map.Entry<Field, Annotation> fieldEntry : fieldAnnotations.entrySet()) {
            Annotation annotation = fieldEntry.getValue();
            Field field = fieldEntry.getKey();

            if (annotation instanceof NestedBeanField) {
                FormBean bean = FormBeanUtils.initFormBean(field.getType());
                int thisIndex = (Integer) invoke("index", annotation);
                List<FieldConfiguration> currentNestedFields = bean.getFields();
                if (!currentNestedFields.isEmpty()) {
                    FieldConfiguration firstField = currentNestedFields.get(0);
                    if (StringUtils.isEmpty(firstField.getFieldSet())) {
                        firstField.setFieldSet((String) invoke("fieldSet", annotation));
                    }
                }

                int currentIndex = indexModifier + thisIndex - 1;
                for (FieldConfiguration fieldConfiguration : currentNestedFields) {
                    fieldConfiguration.setIndex(fieldConfiguration.getIndex() + currentIndex);                    
                    fieldConfiguration.setName(field.getName() + "." + fieldConfiguration.getName());
                }
                nestedFields.addAll(currentNestedFields);
                List<FieldConfiguration> remainingFields = getFieldsAfterIndex(fields, currentIndex);
                for (FieldConfiguration fieldConfiguration : remainingFields) {
                    fieldConfiguration.setIndex(fieldConfiguration.getIndex() + currentNestedFields.size() - 1);
                }
                
                indexModifier += currentNestedFields.size() - 1;
            }
        }
        
        fields.addAll(nestedFields);

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
    
    private List<FieldConfiguration> getFieldsAfterIndex(List<FieldConfiguration> fields, int index) {
        List<FieldConfiguration> fieldsAfter = new ArrayList<FieldConfiguration>();
        for (FieldConfiguration fieldConfiguration : fields) {
            if (fieldConfiguration.getIndex() > index) {
                fieldsAfter.add(fieldConfiguration);
            }
        }
        return fieldsAfter;
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
        throw new RuntimeException("No @Form annotation on: " + getClass().getName());
    }

    @SuppressWarnings("unchecked")
    public FieldConfiguration buildFieldConfiguration(Field field, Annotation formField) {
        FieldConfiguration config = new FieldConfiguration();
        config.setFieldType((FieldType) invoke("fieldType", formField));
        config.setIndex((Integer) invoke("index", formField));
        config.setName((String) invoke("name", formField));
        if (StringUtils.isEmpty(config.getName())) {
            config.setName(field.getName());
        }
        config.setFieldSet((String) invoke("fieldSet", formField));

        if (!(formField instanceof HiddenField)) {

            if (!(formField instanceof CheckBoxField) && !(formField instanceof RadioField)) {
                config.setEmptyText((String) invoke("emptyText", formField));
            }

            if (formField instanceof RadioField) {
                config.setOrientation((Orientation) invoke("orientation", formField));
            }

            config.setFieldLabel((String) invoke("fieldLabel", formField));
            if (StringUtils.isEmpty(config.getFieldLabel())) {
                config.setFieldLabel(WordUtils.capitalize(config.getName()));
            }

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
            config.setEnabled((Boolean) invoke("enabled", formField));
            config.setStyleName((String) invoke("styleName", formField));

            Class<? extends Validator<?>> validatorClass = (Class<? extends Validator<?>>) invoke("validator",
                    formField);
            if (RegExValidator.class.isAssignableFrom(validatorClass)) {
                try {
                    RegExValidator validator = (RegExValidator) validatorClass.newInstance();
                    validator.setFieldLabel(config.getFieldLabel());
                    config.setValidator(new RegexWithMessage(validator.getRegex(), validator.getInvalidMessage()));
                } catch (Exception e) {
                    throw new RuntimeException("failed creating validator for: " + config.getName());
                }
            }
        }
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
        Map<Field, Annotation> allFieldAnnotations = new LinkedHashMap<Field, Annotation>();
        List<Field> fields = getMemberFields();
        for (Field field : fields) {
            List<Annotation> aFieldsAnnotations = getFormFields(field, field.getAnnotations());
            if (aFieldsAnnotations.size() > 0) {
                allFieldAnnotations.put(field, aFieldsAnnotations.get(0));
            }
        }
        return allFieldAnnotations;
    }

    public List<Field> getMemberFields() {
        return getMemberFields(getClass());
    }
    
    public List<Field> getMemberFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<Field>();
        if (clazz.getSuperclass() != null) {
            fields.addAll(getMemberFields(clazz.getSuperclass()));
        }
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        return fields;
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
