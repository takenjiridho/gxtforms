package com.googlecode.gxtforms.validators;

public interface Validator<T> {

    ValidationResult validate(T value);

    void setFieldLabel(String fieldLabel);

    String getFieldLabel();

    String getInvalidMessage();

}
