package com.googlecode.gxtforms.client.form;

import java.io.Serializable;

import com.googlecode.gxtforms.client.config.LabelAlign;

@SuppressWarnings("serial")
public class FormPanelConfiguration implements Serializable {

    String action;
    boolean hideLabels;
    int labelWidth;
    int fieldWidth;
    String heading;
    String method;
    boolean collapsible;
    boolean frame;
    boolean animCollapse;
    LabelAlign labelAlign;
    int width;

    public FormPanelConfiguration() {
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean isHideLabels() {
        return hideLabels;
    }

    public void setHideLabels(boolean hideLabels) {
        this.hideLabels = hideLabels;
    }

    public int getLabelWidth() {
        return labelWidth;
    }

    public void setLabelWidth(int labelWidth) {
        this.labelWidth = labelWidth;
    }

    public int getFieldWidth() {
        return fieldWidth;
    }

    public void setFieldWidth(int fieldWidth) {
        this.fieldWidth = fieldWidth;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public boolean isCollapsible() {
        return collapsible;
    }

    public void setCollapsible(boolean collapsible) {
        this.collapsible = collapsible;
    }

    public boolean isFrame() {
        return frame;
    }

    public void setFrame(boolean frame) {
        this.frame = frame;
    }

    public boolean isAnimCollapse() {
        return animCollapse;
    }

    public void setAnimCollapse(boolean animCollapse) {
        this.animCollapse = animCollapse;
    }

    public LabelAlign getLabelAlign() {
        return labelAlign;
    }

    public void setLabelAlign(LabelAlign labelAlign) {
        this.labelAlign = labelAlign;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

}
