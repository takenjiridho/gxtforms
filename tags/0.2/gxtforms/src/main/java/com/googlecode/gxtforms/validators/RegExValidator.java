package com.googlecode.gxtforms.validators;
import java.util.regex.Pattern;

public class RegExValidator extends ValidatorBase<String> {

    private final static String message = "This field is not a valid ";

    protected Pattern pattern;
    protected String regex;
    protected String typeLabel;

    /**
     * Constructor takes a pattern instance, making the server-side a bit more efficient. The regex is passed to the
     * client for client-side validation.
     *
     * @param pattern
     * @param regex
     * @param typeLabel
     */
    public RegExValidator(Pattern pattern, String regex, String typeLabel) {
        this.pattern = pattern;
        this.regex = regex;
        this.typeLabel = typeLabel;
    }

    public RegExValidator(String fieldLabel, Pattern pattern, String regex, String typeLabel) {
        super(fieldLabel);
        this.pattern = pattern;
        this.regex = regex;
        this.typeLabel = typeLabel;
    }

    public String getRegex() {
        return regex;
    }

    public ValidationResult validate(String value) {
        if (value != null && !pattern.matcher(value).matches()) {
            return new ValidationResult(getInvalidMessage());
        } else {
            return new ValidationResult();
        }
    }

    @Override
    public String getInvalidMessage() {
        return message + typeLabel + ".";
    }
    
    

}
