package com.googlecode.gxtforms.client.config;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RegexWithMessage implements Serializable {

    String regex;
    String message;

    public RegexWithMessage() {
    }

    public RegexWithMessage(String regex, String message) {
        this.regex = regex;
        this.message = message;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
