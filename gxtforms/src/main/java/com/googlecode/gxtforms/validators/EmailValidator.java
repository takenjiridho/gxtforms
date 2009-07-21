package com.googlecode.gxtforms.validators;

import java.util.regex.Pattern;

public class EmailValidator extends RegExValidator {

    private final static String regex = "^([\\w\\-\\.]+)@((\\[([0-9]{1,3}\\.){3}[0-9]{1,3}\\])|(([\\w\\-]+\\.)+)([A-Za-z]{2,4}))$";
    private final static Pattern pattern = Pattern.compile(regex);
    private final static String type = "email address";

    public EmailValidator() {
        super(pattern, regex, type);
    }

    public EmailValidator(String fieldLabel) {
        super(fieldLabel, pattern, regex, type);
    }

}
