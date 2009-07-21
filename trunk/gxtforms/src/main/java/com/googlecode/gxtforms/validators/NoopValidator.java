package com.googlecode.gxtforms.validators;

public class NoopValidator extends ValidatorBase<Void> {

    public ValidationResult validate(Void value) {
        return null;
    }
    
}
