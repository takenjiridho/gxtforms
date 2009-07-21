package com.googlecode.gxtforms.validators;

import java.util.regex.Pattern;

public class PhoneValidator extends RegExValidator {

    private final static String regex = "^(?:\\([2-9]\\d{2}\\)\\ ?|[2-9]\\d{2}(?:\\-?|\\ ?))[2-9]\\d{2}[- ]?\\d{4}$";
    private final static Pattern pattern = Pattern.compile(regex);
    private final static String type = "phone number";

    public PhoneValidator() {
        super(pattern, regex, type);
    }

    public PhoneValidator(String fieldLabel) {
        super(fieldLabel, pattern, regex, type);
    }

}