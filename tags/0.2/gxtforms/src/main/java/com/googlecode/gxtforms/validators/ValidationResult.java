package com.googlecode.gxtforms.validators;

/**
 *
 */
public class ValidationResult {

    boolean valid = true;
    String message;

    public ValidationResult() {
    }

    public ValidationResult(String message) {
        this.message = message;
        valid = false;
    }

    public ValidationResult(boolean valid, String message) {
        this.valid = valid;
        this.message = message;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
