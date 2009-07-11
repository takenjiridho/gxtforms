package com.googlecode.gxtforms.client;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class FieldOption<T> implements Serializable {

    private T value;
    private String label;

    public FieldOption() {
        super();
    }

    public FieldOption(T value, String label) {
        super();
        this.value = value;
        this.label = label;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
