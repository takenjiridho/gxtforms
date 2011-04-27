package com.googlecode.gxtforms.annotations;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.googlecode.gxtforms.client.config.LabelAlign;
import com.googlecode.gxtforms.client.config.Orientation;

public enum AnnotationConstant {

    VALIDATOR("validator", null, false),
    STYLE_NAME("styleName", "", true),
    ENABLED("enabled", null, false),
    AUTO_VALIDATE("autoValidate", null, false),
    VALIDATION_DELAY("validationDelay", null, false),
    VALIDATE_ON_BLUR("validateOnBlur", null, false),
    MESSAGE_TARGET("messageTarget", null, false),
    MAX_LENGTH("maxLength", null, false),
    LABEL_STYLE("labelStyle", "", true),
    LABEL_SEPARATOR("labelSeparator", ":", true),
    LABEL_ALIGN("labelAlign", LabelAlign.LEFT, true),
    ALLOW_BLANK("allowBlank", null, false),
    READ_ONLY("readOnly", false, true),
    HIDE_LABEL("hideLabel", false, true),
    FIELD_LABEL("fieldLabel", null, false),
    ORIENTATION("orientation", Orientation.HORIZONTAL, true),
    EMPTY_TEXT("emptyText", null, false),
    FIELD_SET("fieldSet", "", true),
    NAME("name", null, false),
    INDEX("index", null, false),
    FIELD_TYPE("fieldType", null, false),
    IMPORTANT("important", false, true);

    public final static Set<AnnotationConstant> CASCADING_VALUES;

    static {
        Set<AnnotationConstant> cascadingValues = new HashSet<AnnotationConstant>();
        for (AnnotationConstant constant : values()) {
            if (constant.isCascades()) {
                cascadingValues.add(constant);
            }
        }
        CASCADING_VALUES = Collections.unmodifiableSet(cascadingValues);
    }

    final String label;
    final boolean cascades;
    final Object defaultValue;

    private AnnotationConstant(String label, Object defaultValue, boolean cascades) {
        this.label = label;
        this.cascades = cascades;
        this.defaultValue = defaultValue;
    }

    public String getLabel() {
        return label;
    }

    public boolean isCascades() {
        return cascades;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

}
