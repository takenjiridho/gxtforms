package com.googlecode.gxtforms.validators;

import java.util.regex.Pattern;

public class WholeNumberValidator extends RegExValidator {

    private final static String regex = "^\\d+$";
    private final static Pattern pattern = Pattern.compile(regex);
    private final static String type = "digit";

    public WholeNumberValidator() {
        super(pattern, regex, type);
    }

    public WholeNumberValidator(String fieldLabel) {
        super(fieldLabel, pattern, regex, type);
    }

}

