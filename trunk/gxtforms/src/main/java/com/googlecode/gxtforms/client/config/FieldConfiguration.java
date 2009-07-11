package com.googlecode.gxtforms.client.config;

import java.io.Serializable;
import java.util.List;

import com.googlecode.gxtforms.client.FieldOption;

@SuppressWarnings("serial")
public class FieldConfiguration implements Serializable {

    String name;
    String label;
    boolean required;
    int maxLength;
    int width;
    int order;
    FieldType type;
    String emptyText;
    List<? extends FieldOption<?>> options;

    public FieldConfiguration() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public FieldType getType() {
        return type;
    }

    public void setType(FieldType type) {
        this.type = type;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getEmptyText() {
        return emptyText;
    }

    public void setEmptyText(String emptyText) {
        this.emptyText = emptyText;
    }

    public List<? extends FieldOption<?>> getOptions() {
        return options;
    }

    public void setOptions(List<? extends FieldOption<?>> options) {
        this.options = options;
    }

}
