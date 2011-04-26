package com.googlecode.gxtforms.server;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

import com.googlecode.gxtforms.annotations.Form;

public class FormBeanImplAdapater extends FormBeanImpl {

    private Class<?> target;
    
    public FormBeanImplAdapater(Class<?> target) {
        this.target = target;
    }

    @Override
    public List<Field> getMemberFields() {
        return getMemberFields(target);
    }

    @Override
    public Form getFormAnnotation() {
        for (Annotation annotation : target.getAnnotations()) {
            if (annotation instanceof Form) {
                return (Form) annotation;
            }
        }
        throw new RuntimeException("no FormAnnotation defined for: " + target.getName());
    }
    
    
    
}
