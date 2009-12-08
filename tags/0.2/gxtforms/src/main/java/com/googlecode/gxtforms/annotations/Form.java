package com.googlecode.gxtforms.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.googlecode.gxtforms.client.config.LabelAlign;


@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Form {

    String action() default "";
    
    boolean hideLabels() default false;
    
    int labelWidth() default 0;
    
    int fieldWidth() default 0;
    
    String heading() default "";
    
    String method() default "";
    
    boolean collapsible() default false;
    
    boolean frame() default true; 
    
    boolean animCollapse() default true;
    
    LabelAlign labelAlign() default LabelAlign.LEFT;
    
    int width() default 0;
    
}
