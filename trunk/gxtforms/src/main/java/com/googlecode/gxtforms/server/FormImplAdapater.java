package com.googlecode.gxtforms.server;

import java.lang.reflect.Field;

import com.googlecode.gxtforms.client.FormConfigurationException;

public class FormImplAdapater extends FormImpl {

    private Object target;
    
    public FormImplAdapater(Class<?> target) {
        super();
        try {
            this.target = target.newInstance();
        } catch (Exception e) {
            throw new FormConfigurationException(e);
        }
    }

    @Override
    public Field[] getMemberFields() {
        return target.getClass().getDeclaredFields();
    }
    
}
