package com.googlecode.gxtforms.server;

import static com.googlecode.gxtforms.annotations.AnnotationConstant.*;

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

import com.googlecode.gxtforms.annotations.AnnotationConstant;
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
        return getFields(null);
    }

    public List<FieldConfiguration> getFields(Annotation parent) {
        List<FieldConfiguration> fields = new ArrayList<FieldConfiguration>();

        // process non-nested attributes
        Map<Field, Annotation> fieldAnnotations = getFieldAnnotations();
        for (Map.Entry<Field, Annotation> fieldEntry : fieldAnnotations.entrySet()) {
            Annotation annotation = fieldEntry.getValue();
            Field field = fieldEntry.getKey();
            if (!(annotation instanceof NestedBeanField)) {
                fields.add(buildFieldConfiguration(field, parent, annotation));
            }
        }

        // process nested beans
        int indexModifier = 0;
        List<FieldConfiguration> nestedFields = new ArrayList<FieldConfiguration>();
        for (Map.Entry<Field, Annotation> fieldEntry : fieldAnnotations.entrySet()) {
            Annotation parentAnot = fieldEntry.getValue();
            Field field = fieldEntry.getKey();

            if (parentAnot instanceof NestedBeanField) {
                FormBean bean = FormBeanUtils.initFormBean(field.getType());
                int thisIndex = (Integer) buildAttribute(INDEX, parentAnot);
                List<FieldConfiguration> children = bean.getFields(parentAnot);

                int currentIndex = indexModifier + thisIndex - 1;
                for (FieldConfiguration fieldConfiguration : children) {
                    fieldConfiguration.setIndex(fieldConfiguration.getIndex() + currentIndex);
                    fieldConfiguration.setName(field.getName() + "." + fieldConfiguration.getName());
                }
                nestedFields.addAll(children);
                List<FieldConfiguration> remainingFields = getFieldsAfterIndex(fields, currentIndex);
                for (FieldConfiguration fieldConfiguration : remainingFields) {
                    fieldConfiguration.setIndex(fieldConfiguration.getIndex() + children.size() - 1);
                }

                indexModifier += children.size() - 1;
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

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public FieldConfiguration buildFieldConfiguration(Field field, Annotation parentField, Annotation formField) {
        FieldConfiguration config = new FieldConfiguration();
        config.setFieldType((FieldType) buildAttribute(FIELD_TYPE, parentField, formField));
        config.setIndex((Integer) buildAttribute(INDEX, parentField, formField));
        config.setName((String) buildAttribute(NAME, parentField, formField));
        if (StringUtils.isEmpty(config.getName())) {
            config.setName(field.getName());
        }
        config.setFieldSet((String) buildAttribute(FIELD_SET, parentField, formField));

        if (!(formField instanceof HiddenField)) {

            if (!(formField instanceof CheckBoxField) && !(formField instanceof RadioField)) {
                config.setEmptyText((String) buildAttribute(EMPTY_TEXT, parentField, formField));
            }

            if (formField instanceof RadioField) {
                config.setOrientation((Orientation) buildAttribute(ORIENTATION, parentField, formField));
            }

            config.setFieldLabel((String) buildAttribute(FIELD_LABEL, parentField, formField));
            if (StringUtils.isEmpty(config.getFieldLabel())) {
                config.setFieldLabel(WordUtils.capitalize(config.getName()));
            }

            config.setHideLabel((Boolean) buildAttribute(HIDE_LABEL, parentField, formField));
            config.setReadOnly((Boolean) buildAttribute(READ_ONLY, parentField, formField));
            config.setAllowBlank((Boolean) buildAttribute(ALLOW_BLANK, parentField, formField));
            config.setLabelSeparator((String) buildAttribute(LABEL_SEPARATOR, parentField, formField));
            config.setLabelStyle((String) buildAttribute(LABEL_STYLE, parentField, formField));
            config.setMaxLength((Integer) buildAttribute(MAX_LENGTH, parentField, formField));
            config.setMessageTarget((String) buildAttribute(MESSAGE_TARGET, parentField, formField));
            config.setValidateOnBlur((Boolean) buildAttribute(VALIDATE_ON_BLUR, parentField, formField));
            config.setValidationDelay((Integer) buildAttribute(VALIDATION_DELAY, parentField, formField));
            config.setAutoValidate((Boolean) buildAttribute(AUTO_VALIDATE, parentField, formField));
            config.setEnabled((Boolean) buildAttribute(ENABLED, parentField, formField));
            config.setStyleName((String) buildAttribute(STYLE_NAME, parentField, formField));

            Class<? extends Validator<?>> validatorClass = (Class<? extends Validator<?>>) buildAttribute(VALIDATOR, parentField, formField);
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

    protected Object buildAttribute(AnnotationConstant attribute, Annotation annotation) {
        return buildAttribute(attribute, null, annotation);
    }

    protected Object buildAttribute(AnnotationConstant attribute, Annotation parentAnnotation, Annotation childAnnotation) {
            String label = attribute.getLabel();
            boolean parentImportant = (parentAnnotation != null)?(Boolean) invoke(IMPORTANT.getLabel(), parentAnnotation):false;
            boolean childImportant = (Boolean) invoke(IMPORTANT.getLabel(), childAnnotation);
            if (attribute.isCascades() && parentAnnotation != null && (parentImportant || !childImportant)) {
                Object o = invoke(label, parentAnnotation);
                if (o == null || o.equals(attribute.getDefaultValue())) {
                    // use child
                    return invoke(label, childAnnotation);
                }
                // use parent
                return o;
            } else {
                // use child
                return invoke(label, childAnnotation);
            }
    }

    private Object invoke(String name, Annotation o) {
        try {
            return o.getClass().getMethod(name, (Class<?>[]) null).invoke(o, (Object[]) null);
        } catch (Exception e) {
            return null;
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
            if (annot.annotationType().isAnnotationPresent(FormAnnotation.class)) {
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
