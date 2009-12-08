package com.googlecode.gxtforms.client.config;

import java.io.Serializable;
import java.util.List;

import com.googlecode.gxtforms.client.field.FieldOption;

/**
 * This is a transfer object for all the different types of field
 * configurations.
 * 
 */
@SuppressWarnings("serial")
public class FieldConfiguration implements Serializable {

    String name;
    String title;
    String fieldLabel;
    boolean allowBlank;
    int maxLength;
    int width;
    int index;
    FieldType fieldType;
    String emptyText;
    List<? extends FieldOption<?>> options;
    String radioGroup;
    boolean autoValidate;
    String labelSeparator;
    String labelStyle;
    String messageTarget;
    boolean validateOnBlur;
    int validationDelay;
    boolean hideLabel;
    boolean readOnly;
    String styleName;
    boolean enabled;
    String fieldSet;
    Orientation orientation;
    RegexWithMessage validator;

    public FieldConfiguration() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFieldSet() {
        return fieldSet;
    }

    public void setFieldSet(String fieldSet) {
        this.fieldSet = fieldSet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFieldLabel() {
        return fieldLabel;
    }

    public void setFieldLabel(String label) {
        this.fieldLabel = label;
    }

    public boolean isAllowBlank() {
        return allowBlank;
    }

    public void setAllowBlank(boolean allowBlank) {
        this.allowBlank = allowBlank;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType type) {
        this.fieldType = type;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getEmptyText() {
        return emptyText;
    }

    public void setEmptyText(String emptyText) {
        this.emptyText = emptyText;
    }

    public List<? extends FieldOption<?>> getOptions() {
        return options;
    }

    public void setOptions(List<? extends FieldOption<?>> options) {
        this.options = options;
    }

    public String getRadioGroup() {
        return radioGroup;
    }

    public void setRadioGroup(String radioGroup) {
        this.radioGroup = radioGroup;
    }

    public boolean isAutoValidate() {
        return autoValidate;
    }

    public void setAutoValidate(boolean autoValidate) {
        this.autoValidate = autoValidate;
    }

    public String getLabelSeparator() {
        return labelSeparator;
    }

    public void setLabelSeparator(String labelSeparator) {
        this.labelSeparator = labelSeparator;
    }

    public String getLabelStyle() {
        return labelStyle;
    }

    public void setLabelStyle(String labelStyle) {
        this.labelStyle = labelStyle;
    }

    public String getMessageTarget() {
        return messageTarget;
    }

    public void setMessageTarget(String messageTarget) {
        this.messageTarget = messageTarget;
    }

    public boolean isValidateOnBlur() {
        return validateOnBlur;
    }

    public void setValidateOnBlur(boolean validateOnBlur) {
        this.validateOnBlur = validateOnBlur;
    }

    public int getValidationDelay() {
        return validationDelay;
    }

    public void setValidationDelay(int validationDelay) {
        this.validationDelay = validationDelay;
    }

    public boolean isHideLabel() {
        return hideLabel;
    }

    public void setHideLabel(boolean hideLabel) {
        this.hideLabel = hideLabel;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public RegexWithMessage getValidator() {
        return validator;
    }

    public void setValidator(RegexWithMessage validator) {
        this.validator = validator;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
