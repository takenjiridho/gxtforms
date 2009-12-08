package com.googlecode.gxtforms.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.googlecode.gxtforms.client.config.FieldType;


@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@FormAnnotation()
public @interface NestedBeanField {

    String fieldSet();
    
    int index() default 0;

    boolean readOnly() default false;
    
    String styleName() default "";
    
    boolean enabled() default true;
    
    FieldType fieldType() default FieldType.FieldSet;
    
}
