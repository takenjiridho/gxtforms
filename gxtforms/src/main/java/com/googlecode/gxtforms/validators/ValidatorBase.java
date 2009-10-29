package com.googlecode.gxtforms.validators;

public abstract class ValidatorBase<T> implements Validator<T> {

    /* currently fieldLabel is unused */
    protected String fieldLabel;

    public ValidatorBase() {
    }

    public ValidatorBase(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

    public String getFieldLabel() {
        return fieldLabel;
    }

    public String getInvalidMessage() {
        return "This field is invalid.";
    }

}
